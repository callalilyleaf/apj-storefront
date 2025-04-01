package edu.byui.apj.storefront.jms.consumer;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.jms.annotation.JmsListener;
import edu.byui.apj.storefront.model.CardOrder;


@Component
public class OrderConfirmationConsumer {
    private static final Logger logger = LoggerFactory.getLogger(OrderConfirmationConsumer.class);
    private final WebClient webClient;

    public OrderConfirmationConsumer(WebClient.Builder webClientBuilder) { //Use WebClient.Builder to initialize the webClient
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
    }

    @JmsListener(destination = "orderQueue")
    public void receiveOrderCconfirmation(String orderId){ //Use JmsListener to receive the messages to the orderQueue
        try {
            CardOrder cardOrder = webClient.get()
                    .uri("/order/{orderId}", orderId)
                    .retrieve()
                    .bodyToMono(CardOrder.class) // convert HTTP response to a Mono<CardOrder> object!
                    .block(); // The subsequent.block() call converts the asynchronous Mono into a synchronous call, waiting for the response and returning the CardOrder instance!!
            logger.info("Order confirmation received: " + cardOrder);
        } catch (Exception e) {
            logger.error("Error retrieving order details for order id {} " + orderId, e); // e will also be printed out in the log
        }
    }
}
