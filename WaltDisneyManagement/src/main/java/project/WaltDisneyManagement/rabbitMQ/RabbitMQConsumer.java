package project.WaltDisneyManagement.rabbitMQ;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Header;
import com.google.gson.Gson;


import java.util.Objects;
import project.WaltDisneyManagement.entity.MagicKingdomMessage;


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







        //System.out.println(routingKey); 
        //System.out.println("Message recieved from queue : " + message);
        messagingTemplate.convertAndSend("/topic/atualizacao", message);
    }

}
