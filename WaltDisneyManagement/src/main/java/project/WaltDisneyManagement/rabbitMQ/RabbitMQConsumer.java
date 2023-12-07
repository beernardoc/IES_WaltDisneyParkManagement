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
import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.repository.AttractionRepo;
import project.WaltDisneyManagement.repository.ParkRepo;

import project.WaltDisneyManagement.entity.ParkCars;
import project.WaltDisneyManagement.repository.ParkCarsRepo;

import java.util.Objects;


@Component
public class RabbitMQConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AttractionRepo attractionRepo;

    @Autowired
    private ParkCarsRepo parkingRepo;

    int total = 0;



    @RabbitListener(queues = {Config.MagicKingdomQueue, Config.EpcotQueue, Config.HollywoodStudiosQueue, Config.AnimalKingdomQueue, Config.DisneySprings, Config.BlizzardBeach, Config.TyphoonLagoon, Config.ParkingLot})
    public void consumeMessageFromQueue(String message, @Header("amqp_receivedRoutingKey") String routingKey) {


        System.out.println("Received message: " + message + " from queue: " + routingKey);

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();

        System.out.println(jsonObject);
        System.out.println(jsonObject.keySet());



        for(String key : jsonObject.keySet()){
            if (!Objects.equals(key, "Time") && !Objects.equals(key, "ParkingLot1") && !Objects.equals(key, "ParkingLot2")){
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

                System.out.println(attraction.getName());


                if(Objects.equals(attraction.getType(), "RollerCoaster")){
                    if (jsonObject.get(key) instanceof JsonObject) {
                        JsonObject attractionObject = jsonObject.getAsJsonObject(key);
                        System.out.println("rc " + attractionObject);

                        Double velocityKmh = attractionObject.getAsJsonPrimitive("velocity_kmh").getAsDouble();
                        Double height = attractionObject.getAsJsonPrimitive("height_m").getAsDouble();
                        Double temperature = attractionObject.getAsJsonPrimitive("temperature").getAsDouble();
                        Double vibration = attractionObject.getAsJsonPrimitive("vibration").getAsDouble();

                        if(attraction.testValuesRollerCoaster(velocityKmh, height, temperature, vibration)){
                            messagingTemplate.convertAndSend("/topic/" +  attraction.getPark().getName() +"/" + attraction.getName(), attractionObject.toString());

                        }
                        else{
                            attraction.setStatus("Closed");
                            attractionRepo.save(attraction);
                            messagingTemplate.convertAndSend("/topic/" + attraction.getPark().getName() +"/" + attraction.getName() + "/Alert", attraction.getName());

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
                            messagingTemplate.convertAndSend("/topic/" + attraction.getPark().getName() +"/" + attraction.getName(), attractionObject.toString());

                        }
                        else{
                            attraction.setStatus("Closed");
                            attractionRepo.save(attraction);
                            messagingTemplate.convertAndSend("/topic/" + attraction.getPark().getName() +"/" + attraction.getName() + "/Alert", attraction.getName());
                        }


                    }

                }
                

            }
            else if (Objects.equals(key, "ParkingLot1") || (Objects.equals(key, "ParkingLot2"))) {
                if (jsonObject.get(key) instanceof JsonObject) {
                        ParkCars parkingLot = parkingRepo.findByName(key);
                        System.out.println("Parque de estacionamento criado");

                        JsonObject parkingObject = jsonObject.getAsJsonObject(key);
                        System.out.println("dr " + parkingObject);

                        int cars_in = parkingObject.getAsJsonPrimitive("cars_in").getAsInt();
                        System.out.println(cars_in);
                        int cars_out = parkingObject.getAsJsonPrimitive("cars_out").getAsInt();
                        System.out.println(cars_out);


                        int update = cars_in - cars_out;

                        int total = parkingLot.getAtual();

                        if (update < 0){
                            total = total - update;
                        }else{total = total + update;}

                        if (total < 0){total = 0;}
                        if (total > parkingLot.getMaxcap()){total = parkingLot.getMaxcap();}

                        System.out.println("hey");
                        parkingLot.setAtual(total);
                        parkingRepo.save(parkingLot);



                    }
            }
            



        }
        messagingTemplate.convertAndSend("/topic/atualizacao", message);
    }

}
