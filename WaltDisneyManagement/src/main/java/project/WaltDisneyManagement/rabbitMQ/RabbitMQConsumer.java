package project.WaltDisneyManagement.rabbitMQ;


import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import project.WaltDisneyManagement.entity.HollywoodStudios;
import project.WaltDisneyManagement.entity.BlizzardBeachMessage;
import project.WaltDisneyManagement.entity.DisneySpringsMessage;
import project.WaltDisneyManagement.entity.EpcotMessage;
import project.WaltDisneyManagement.entity.MagicKingdomMessage;
import project.WaltDisneyManagement.entity.TyphoonLagoonMessage;

import java.util.Objects;


@Component
public class RabbitMQConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @RabbitListener(queues = Config.MagicKingdomQueue)
    public void consumeMessageFromQueue(String message, @Header("amqp_receivedRoutingKey") String routingKey) {

        Gson gson = new Gson();


        if(Objects.equals(routingKey, "MagicKingdom")){

            MagicKingdomMessage magicKingdomMessage = gson.fromJson(message, MagicKingdomMessage.class);


            messagingTemplate.convertAndSend("/topic/MagicKingdom", magicKingdomMessage);

        }
        if (Objects.equals(routingKey, "Epcot")){

            EpcotMessage epcotMessage = gson.fromJson(message, EpcotMessage.class);


            messagingTemplate.convertAndSend("/topic/Epcot", epcotMessage);
        
        }
        if (Objects.equals(routingKey, "HollywoodStudios")){

            HollywoodStudios HollywoodStudiosMessage = gson.fromJson(message, HollywoodStudios.class);

            messagingTemplate.convertAndSend("/topic/HollywoodStudios", HollywoodStudiosMessage);

        }
        if (Objects.equals(routingKey, "AnimalKingdom")){

            MagicKingdomMessage magicKingdomMessage = gson.fromJson(message, MagicKingdomMessage.class);

            messagingTemplate.convertAndSend("/topic/AnimalKingdom", magicKingdomMessage);

        }
        if (Objects.equals(routingKey, "DisneySprings")){

            DisneySpringsMessage disneySpringsMessage = gson.fromJson(message, DisneySpringsMessage.class);

            messagingTemplate.convertAndSend("/topic/DisneySprings", disneySpringsMessage);

        }
        if (Objects.equals(routingKey, "BlizzardBeach")){

            BlizzardBeachMessage blizzardBeachMessage = gson.fromJson(message, BlizzardBeachMessage.class);

            messagingTemplate.convertAndSend("/topic/BlizzardBeach", blizzardBeachMessage);

        }
        if (Objects.equals(routingKey, "TyphoonLagoon")){

            TyphoonLagoonMessage typhoonLagoonMessage = gson.fromJson(message, TyphoonLagoonMessage.class);

            messagingTemplate.convertAndSend("/topic/TyphoonLagoon", typhoonLagoonMessage);

        }






        //System.out.println(routingKey); 
        //System.out.println("Message recieved from queue : " + message);
        messagingTemplate.convertAndSend("/topic/atualizacao", message);
    }

}
