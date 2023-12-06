package project.WaltDisneyManagement.rabbitMQ;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.repository.ParkRepo;

import java.util.Objects;


@Component
public class RabbitMQConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ParkRepo parkRepo;

    @Autowired
    private AttractionRepo attractionRepo;



    @RabbitListener(queues = {Config.MagicKingdomQueue, Config.EpcotQueue, Config.HollywoodStudiosQueue, Config.AnimalKingdomQueue, Config.DisneySprings, Config.BlizzardBeach, Config.TyphoonLagoon})
    public void consumeMessageFromQueue(String message, @Header("amqp_receivedRoutingKey") String routingKey) {

        Gson gson = new Gson();


        if(Objects.equals(routingKey, "MagicKingdom")){
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();

            System.out.println(jsonObject);
            System.out.println(jsonObject.keySet());

            for(String key : jsonObject.keySet()){
                if (!Objects.equals(key, "Time")){
                    System.out.println("key" + key);
                    Attraction attraction = attractionRepo.findByName(key);

                    if(attraction == null || attraction.getType() == null){
                        System.out.println("Attraction not found");
                        continue;
                    }

                    if(Objects.equals(attraction.getStatus(), "Closed")){
                        System.out.println("Attraction is closed");
                        continue;
                    }


                    if(Objects.equals(attraction.getType(), "RollerCoaster")){
                        if (jsonObject.get(key) instanceof JsonObject) {
                            JsonObject attractionObject = jsonObject.getAsJsonObject(key);
                            System.out.println("rc " + attractionObject);

                            Double velocityKmh = attractionObject.getAsJsonPrimitive("velocity_kmh").getAsDouble();
                            Double height = attractionObject.getAsJsonPrimitive("height_m").getAsDouble();
                            Double temperature = attractionObject.getAsJsonPrimitive("temperature").getAsDouble();
                            Double vibration = attractionObject.getAsJsonPrimitive("vibration").getAsDouble();

                            if(attraction.testValuesRollerCoaster(velocityKmh, height, temperature, vibration)){
                                messagingTemplate.convertAndSend("/topic/MagicKingdom/" + attraction.getName(), attractionObject.toString());

                            }
                            else{
                                attraction.setStatus("Closed");
                                attractionRepo.save(attraction);
                                messagingTemplate.convertAndSend("/topic/MagicKingdom/" + attraction.getName() + "/Alert", attraction.getName());

                            }


                        }

                    }
                    else if (Objects.equals(attraction.getType(), "DarkRide")){
                        if (jsonObject.get(key) instanceof JsonObject) {
                            JsonObject attractionObject = jsonObject.getAsJsonObject(key);
                            System.out.println("dr " + attractionObject);

                            Double velocityKmh = attractionObject.getAsJsonPrimitive("velocity_kmh").getAsDouble();
                            Double temperature = attractionObject.getAsJsonPrimitive("temperature").getAsDouble();
                            Double vibration = attractionObject.getAsJsonPrimitive("vibration").getAsDouble();

                            if(attraction.testValuesDarkRide(velocityKmh, temperature, vibration)){
                                messagingTemplate.convertAndSend("/topic/MagicKingdom/" + attraction.getName(), attractionObject.toString());

                            }
                            else{
                                attraction.setStatus("Closed");
                                attractionRepo.save(attraction);
                                messagingTemplate.convertAndSend("/topic/MagicKingdom/" + attraction.getName() + "/Alert", attraction.getName());
                            }


                        }

                    }

                }



            }





        }
        if (Objects.equals(routingKey, "Epcot")){


            System.out.println("Epcot" + message);
        
        }
        if (Objects.equals(routingKey, "HollywoodStudios")){

            System.out.println("HollywoodStudios" + message);

        }
        if (Objects.equals(routingKey, "AnimalKingdom")){

            System.out.println("AnimalKingdom" + message);

        }
        if (Objects.equals(routingKey, "DisneySprings")){

            System.out.println("DisneySprings" + message);

        }
        if (Objects.equals(routingKey, "BlizzardBeach")){

            System.out.println("BlizzardBeach" + message);




        }
        if (Objects.equals(routingKey, "TyphoonLagoon")){

            System.out.println("TyphoonLagoon" + message);
        }


        messagingTemplate.convertAndSend("/topic/atualizacao", message);
    }

}
