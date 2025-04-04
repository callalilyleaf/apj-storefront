// File: src/test/java/edu/byui/apj/storefront/db/repository/OrderRepositoryTest.java
package edu.byui.apj.storefront.db.repository;

import edu.byui.apj.storefront.db.model.CardOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void testSaveAndFindOrder() {
        CardOrder order = new CardOrder();
        order.setId(1L);
        order.setOrderDate(new Date());
        order.setTotal(100.0);
        order.setConfirmationSent(false);
        order.setShipMethod("Express");

        when(orderRepository.save(order)).thenReturn(order);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        CardOrder savedOrder = orderRepository.save(order);
        assertNotNull(savedOrder);

        Optional<CardOrder> foundOrder = orderRepository.findById(1L);
        assertTrue(foundOrder.isPresent());
        assertEquals("Express", foundOrder.get().getShipMethod());
    }
}