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

    public static final String MagicKingdomQueue = "Magic Kingdom";
    public static final String EpcotQueue = "Epcot";
    public static final String HollywoodStudiosQueue = "Hollywood Studios";
    public static final String AnimalKingdomQueue = "Animal Kingdom";
    public static final String DisneySprings = "Disney Springs";
    public static final String BlizzardBeach = "Blizzard Beach";
    public static final String TyphoonLagoon = "Typhoon Lagoon";

    public static final String EXCHANGE_NAME = "";


    @Bean
    Queue queue1() {
        return new Queue(MagicKingdomQueue, false);
    }

    @Bean
    Queue queue2() {
        return new Queue(EpcotQueue, false);
    }

    @Bean
    Queue queue3() {
        return new Queue(HollywoodStudiosQueue, false);
    }

    @Bean
    Queue queue4() {
        return new Queue(AnimalKingdomQueue, false);
    }

    @Bean
    Queue queue5() {
        return new Queue(DisneySprings, false);
    }

    @Bean
    Queue queue6() {
        return new Queue(BlizzardBeach, false);
    }

    @Bean 
    Queue queue7() {
        return new Queue(TyphoonLagoon, false);
    }


    @Bean
    DirectExchange exchange() { return new DirectExchange(EXCHANGE_NAME); }

    @Bean
    Binding binding1(@Qualifier("queue1") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(MagicKingdomQueue);
    }

    @Bean
    Binding binding2(@Qualifier("queue2") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(EpcotQueue);
    }

    @Bean
    Binding binding3(@Qualifier("queue3") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(HollywoodStudiosQueue);
    }

    @Bean
    Binding binding4(@Qualifier("queue4") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(AnimalKingdomQueue);
    }

    @Bean
    Binding binding5(@Qualifier("queue5") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DisneySprings);
    }

    @Bean  
    Binding binding6(@Qualifier("queue6") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(BlizzardBeach);
    }

    @Bean
    Binding binding7(@Qualifier("queue7") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TyphoonLagoon);
    }

    @Bean
    public RabbitMQConsumer receiver() {
        return new RabbitMQConsumer();
    }


}
