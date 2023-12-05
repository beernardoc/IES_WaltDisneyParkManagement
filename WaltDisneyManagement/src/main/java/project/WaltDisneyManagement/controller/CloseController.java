package project.WaltDisneyManagement.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import project.WaltDisneyManagement.entity.Attraction;
import project.WaltDisneyManagement.repository.AttractionRepo;

@Controller
public class CloseController {

    @Autowired
    private AttractionRepo attractionRepo;

    @MessageMapping("/topic/closeAttraction")
    @SendTo("/topic/MagicKingdom")
    public String closeAttraction(String message) {
        Attraction attraction = attractionRepo.findByName(message);

        attraction.setStatus("Closed");

        attractionRepo.save(attraction);

        System.out.println(attraction.getName() + " is now closed.");
        System.out.println("PARK: " + attraction.getPark().getName());

        // Retorna a URL da atração fechada como resposta
        return "/parks/" + attraction.getPark().getName() + "/attractions/" + attraction.getName();
    }
}
