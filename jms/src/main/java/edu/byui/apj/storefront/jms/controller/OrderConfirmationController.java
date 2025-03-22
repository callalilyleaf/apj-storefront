package edu.byui.apj.storefront.jms.controller;

import edu.byui.apj.storefront.jms.producer.OrderConfirmationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 3.21 notes: start from making the OrderConfirmationController class. Go to Gemini for reference and assistance: https://gemini.google.com/u/4/gem/coding-partner/5d860cb977dfd76c

@RestController
public class OrderConfirmationController {
    private final OrderConfirmationProducer orderConfirmationProducer;

    @Autowired
    public OrderConfirmationController(OrderConfirmationProducer orderConfirmationController){
        this.orderConfirmationController = orderConfirmationController;
    }

    @GetMapping("/getComfirmOrderId")
    public String getComfirmOrderId(Model model){

    }
}
