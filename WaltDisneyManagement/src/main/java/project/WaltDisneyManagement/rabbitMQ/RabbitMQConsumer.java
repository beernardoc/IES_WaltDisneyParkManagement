package project.WaltDisneyManagement.rabbitMQ;

import org.json.JSONException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @RabbitListener(queues = Config.MagicKingdomQueue)
    public void consumeMessageFromQueue(String message){



        System.out.println("Message recieved from queue : " + message);
        messagingTemplate.convertAndSend("/topic/atualizacao", message);
    }

}
