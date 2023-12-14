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
import project.WaltDisneyManagement.service.AttractionService;
import project.WaltDisneyManagement.service.ParkCarsService;
import project.WaltDisneyManagement.service.ParkService;

import java.util.Objects;


@Component
public class RabbitMQConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AttractionRepo attractionRepo;

    @Autowired
    private AttractionService attractionService;

    @Autowired
    private ParkCarsRepo parkingRepo;

    @Autowired
    private ParkCarsService parkCarsService;

    @Autowired
    private ParkRepo parkRepo;

    @Autowired
    private ParkService parkService;





    @RabbitListener(queues = {Config.MagicKingdomQueue, Config.EpcotQueue, Config.HollywoodStudiosQueue, Config.AnimalKingdomQueue, Config.DisneySprings, Config.BlizzardBeach, Config.TyphoonLagoon, Config.ParkingLot})
    public void consumeMessageFromQueue(String message, @Header("amqp_receivedRoutingKey") String routingKey) {


        System.out.println("Received message: " + message + " from queue: " + routingKey);

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();



        for(String key : jsonObject.keySet()){


            if (!Objects.equals(key, "Time") && !Objects.equals(key, "ParkingLot1") && !Objects.equals(key, "ParkingLot2") && !Objects.equals(key, "Visitors")) {
                Attraction attraction = attractionService.findByName(key);

                if(attraction == null){
                    continue;
                }

                if(Objects.equals(attraction.getStatus(), "Closed")){
                    continue;
                }


                if(Objects.equals(attraction.getType(), "RollerCoaster")){
                    if (jsonObject.get(key) instanceof JsonObject) {
                        JsonObject attractionObject = jsonObject.getAsJsonObject(key);
                        // System.out.println("rc " + attractionObject);

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
                else if(Objects.equals(attraction.getType(), "Carousel")) {
                    if (jsonObject.get(key) instanceof JsonObject) {
                        JsonObject attractionObject = jsonObject.getAsJsonObject(key);


                        Double velocityKmh = attractionObject.getAsJsonPrimitive("velocity_kmh").getAsDouble();
                        Double rpm = attractionObject.getAsJsonPrimitive("rpm").getAsDouble();
                        Double temperature = attractionObject.getAsJsonPrimitive("temperature").getAsDouble();
                        Double vibration = attractionObject.getAsJsonPrimitive("vibration").getAsDouble();

                        if (attraction.testValuesCarousel(velocityKmh, rpm, temperature, vibration)) {
                            messagingTemplate.convertAndSend("/topic/" + attraction.getPark().getName() + "/" + attraction.getName(), attractionObject.toString());

                        } else {
                            attraction.setStatus("Closed");
                            attractionRepo.save(attraction);
                            messagingTemplate.convertAndSend("/topic/" + attraction.getPark().getName() + "/" + attraction.getName() + "/Alert", attraction.getName());
                        }


                    }
                }
                else if(Objects.equals(attraction.getType(), "WaterRide")) {
                    if (jsonObject.get(key) instanceof JsonObject) {
                        JsonObject attractionObject = jsonObject.getAsJsonObject(key);


                        Double velocityKmh = attractionObject.getAsJsonPrimitive("velocity_kmh").getAsDouble();
                        Double height = attractionObject.getAsJsonPrimitive("height_m").getAsDouble();
                        Double temperature = attractionObject.getAsJsonPrimitive("temperature").getAsDouble();
                        Double vibration = attractionObject.getAsJsonPrimitive("vibration").getAsDouble();

                        if (attraction.testValuesWaterRide(velocityKmh, height, temperature, vibration)) {
                            messagingTemplate.convertAndSend("/topic/" + attraction.getPark().getName() + "/" + attraction.getName(), attractionObject.toString());

                        } else {
                            attraction.setStatus("Closed");
                            attractionRepo.save(attraction);
                            messagingTemplate.convertAndSend("/topic/" + attraction.getPark().getName() + "/" + attraction.getName() + "/Alert", attraction.getName());
                        }


                    }
                }


            else if (Objects.equals(key, "Visitors")) {

                var value = jsonObject.get(key).getAsJsonObject().get("entry_exit").getAsInt();

                Park park = parkService.findByName(routingKey);

                if(park == null){
                    // System.out.println("Attraction not found");
                    continue;
                }

                park.addVisitor(value);
                parkRepo.save(park);
                messagingTemplate.convertAndSend("/topic/Visitors", parkService.getTotalVisitors());
                messagingTemplate.convertAndSend("/topic/" + park.getName() + "/Visitors", park.getVisitors());


            }



            else if (Objects.equals(key, "ParkingLot1") || (Objects.equals(key, "ParkingLot2"))) {
                if (jsonObject.get(key) instanceof JsonObject) {

                        ParkCars parkingLot = parkCarsService.findByName(key);
                        System.out.println("Parque de estacionamento criado");


                        if(parkingLot == null){
                            System.out.println("Parque de estacionamento não encontrado");
                            continue;
                        }

                        JsonObject parkingObject = jsonObject.getAsJsonObject(key);
                        // System.out.println("dr " + parkingObject);

                        int cars_in = parkingObject.getAsJsonPrimitive("cars_in").getAsInt();
                        // System.out.println(cars_in);
                        int cars_out = parkingObject.getAsJsonPrimitive("cars_out").getAsInt();
                        // System.out.println(cars_out);


                        int update = cars_in - cars_out;

                        int total = parkingLot.getAtual();

                        if (update < 0){
                            total = total - update;
                        }else{total = total + update;}

                        if (total < 0){total = 0;}
                        if (total > parkingLot.getMaxcap()){total = parkingLot.getMaxcap();}

                        
                        parkingLot.setAtual(total);
                        parkingRepo.save(parkingLot);

                        messagingTemplate.convertAndSend("/topic/" + parkingLot.getName(), parkingObject.toString());



                }

            }

        }

    }

}
}
