// File: src/test/java/edu/byui/apj/storefront/db/controller/OrderControllerTest.java
package edu.byui.apj.storefront.db.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import edu.byui.apj.storefront.db.model.CardOrder;
import edu.byui.apj.storefront.db.service.OrderService;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Test
    public void testGetOrderFound() {
        CardOrder order = new CardOrder();
        order.setId(1L);
        order.setOrderDate(new Date());
        order.setTotal(100.0);
        order.setConfirmationSent(false);
        order.setShipMethod("Express");

        when(orderService.getOrder(1L)).thenReturn(Optional.of(order));

        ResponseEntity<CardOrder> responseEntity = orderController.getOrder(1L);
        assertNotNull(responseEntity);
        CardOrder response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Express", response.getShipMethod());
    }

    @Test
    public void testGetOrderNotFound() {
        when(orderService.getOrder(1L)).thenReturn(Optional.empty());

        ResponseEntity<CardOrder> responseEntity = orderController.getOrder(1L);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testSaveOrder() {
        CardOrder order = new CardOrder();
        order.setOrderDate(new Date());
        order.setTotal(100.0);
        order.setConfirmationSent(false);
        order.setShipMethod("Express");

        when(orderService.saveOrder(order)).thenReturn(order);

        ResponseEntity<CardOrder> responseEntity = orderController.saveOrder(order);
        assertNotNull(responseEntity);
        CardOrder response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Express", response.getShipMethod());
    }
}