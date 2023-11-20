package project.WaltDisneyManagement.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public RabbitMQConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "minha_fila")
    public void receberMensagem(String mensagem) {
        System.out.println("[x] Recebido: " + mensagem);

        // Enviar a mensagem para o cliente via WebSocket
        messagingTemplate.convertAndSend("/topic/atualizacao", mensagem);
    }
}
