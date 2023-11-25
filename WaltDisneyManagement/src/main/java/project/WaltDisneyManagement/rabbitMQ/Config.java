package project.WaltDisneyManagement.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class Config {

    public static final String MagicKingdomQueue = "MagicKingdom";

    public static final String EXCHANGE_NAME = "";

    @Bean
    Queue queue1() {
        return new Queue(MagicKingdomQueue, false);
    }


    @Bean
    DirectExchange exchange() { return new DirectExchange(EXCHANGE_NAME); }

    @Bean
    Binding binding1(@Qualifier("queue1") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MagicKingdomQueue);
    }

    @Bean
    public RabbitMQConsumer receiver() {
        return new RabbitMQConsumer();
    }


}
