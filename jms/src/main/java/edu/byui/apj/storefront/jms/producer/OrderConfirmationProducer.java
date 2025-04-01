package edu.byui.apj.storefront.jms.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class OrderConfirmationProducer {
    private final JmsTemplate jmsTemplate; // Before you set up the @Autowired, it will tell you the jmsTemplate has not been initialized.
    private static final String DESTINATION = "orderQueue";
    private static final Logger logger = LoggerFactory.getLogger(OrderConfirmationProducer.class);

    @Autowired
    public OrderConfirmationProducer(JmsTemplate jmsTemplate){
        this.jmsTemplate = jmsTemplate;
    }

    public void sendOrderConfirmation(String orderId){ jmsTemplate.convertAndSend(DESTINATION, orderId);
        logger.info("Order confirmation message sent: " + orderId);
    }
}
