package edu.byui.apj.storefront.jms.controller;

import edu.byui.apj.storefront.jms.producer.OrderConfirmationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// 3.31 notes: start from OrderConfirmationConsumer!!! Let's gooo!!

@RestController
public class OrderConfirmationController {
    private final OrderConfirmationProducer orderConfirmationProducer;

    @Autowired
    public OrderConfirmationController(OrderConfirmationProducer orderConfirmationProducer){
        this.orderConfirmationProducer = orderConfirmationProducer;
    }

    @GetMapping("/confirm/{orderId}")
    public String getComfirmOrderId(@PathVariable String orderId, Model model){
        try{
            orderConfirmationProducer.sendOrderConfirmation(orderId);
            return "Order confirmation sent for order: " + orderId;
        }
        catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
            return "Error: " + e.getMessage();
        }

    }
}
