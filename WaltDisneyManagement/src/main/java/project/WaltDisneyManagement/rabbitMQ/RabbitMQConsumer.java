package project.WaltDisneyManagement.rabbitMQ;


import com.google.gson.Gson;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import project.WaltDisneyManagement.entity.Messages.*;
import project.WaltDisneyManagement.entity.Rollercoaster;

import java.util.Objects;


@Component
public class RabbitMQConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @RabbitListener(queues = {Config.MagicKingdomQueue, Config.EpcotQueue, Config.HollywoodStudiosQueue, Config.AnimalKingdomQueue, Config.DisneySprings, Config.BlizzardBeach, Config.TyphoonLagoon})
    public void consumeMessageFromQueue(String message, @Header("amqp_receivedRoutingKey") String routingKey) {

        Gson gson = new Gson();


        if(Objects.equals(routingKey, "MagicKingdom")){

            System.out.println(message);
            MagicKingdomMessage magicKingdomMessage = gson.fromJson(message, MagicKingdomMessage.class);

            String exceededParametersMessage = magicKingdomMessage.checkLimits();

            if (!Objects.equals(exceededParametersMessage, "Nenhum parâmetro ultrapassado")) {
                System.out.println(exceededParametersMessage);
                messagingTemplate.convertAndSend("/topic/MagicKingdom/Danger", exceededParametersMessage);
            }
            else {
                System.out.println(magicKingdomMessage);
                messagingTemplate.convertAndSend("/topic/MagicKingdom", magicKingdomMessage);
            }




        }
        if (Objects.equals(routingKey, "Epcot")){


            EpcotMessage epcotMessage = gson.fromJson(message, EpcotMessage.class);


            messagingTemplate.convertAndSend("/topic/Epcot", epcotMessage);
        
        }
        if (Objects.equals(routingKey, "HollywoodStudios")){


            HollywoodStudiosMessage HollywoodStudiosMessage = gson.fromJson(message, project.WaltDisneyManagement.entity.Messages.HollywoodStudiosMessage.class);

            messagingTemplate.convertAndSend("/topic/HollywoodStudios", HollywoodStudiosMessage);

        }
        if (Objects.equals(routingKey, "AnimalKingdom")){


            AnimalKingdomMessage animalKingdomMessage = gson.fromJson(message, AnimalKingdomMessage.class);

            messagingTemplate.convertAndSend("/topic/AnimalKingdom", animalKingdomMessage);

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


        messagingTemplate.convertAndSend("/topic/atualizacao", message);
    }

}
