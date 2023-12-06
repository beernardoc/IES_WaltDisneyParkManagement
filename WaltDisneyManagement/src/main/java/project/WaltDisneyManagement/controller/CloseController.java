package project.WaltDisneyManagement.controller;



import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.entity.Park;
import project.WaltDisneyManagement.repository.AttractionRepo;

import java.util.Objects;

@Controller
public class CloseController {

    @Autowired
    private AttractionRepo attractionRepo;


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/topic/CloseOrOPenAttraction")
    public void closeAttraction(String message) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(message).getAsJsonObject();

        String attractionName = String.valueOf(jsonObject.get("attraction")).replaceAll("\"", "");
        String status = String.valueOf(jsonObject.get("status")).replaceAll("\"", "");

        Attraction attraction = attractionRepo.findByName(attractionName);

        if(status.equals("Closed")){
            attraction.setStatus("Open");
            attractionRepo.save(attraction);
        }
        else if (status.equals("Open")){
            attraction.setStatus("Closed");
            attractionRepo.save(attraction);
        }
        else{
            System.out.println("Invalid status");
        }


        // Construa o destino dinâmico com base na atração
        String destination = "/topic/" + attraction.getPark().getName() + "/" + attractionName + "/Reload";

        // Envie uma mensagem para o destino dinâmico
        simpMessagingTemplate.convertAndSend(destination, "/parks/" + attraction.getPark().getName() + "/attractions/" + attraction.getName());



    }
}
