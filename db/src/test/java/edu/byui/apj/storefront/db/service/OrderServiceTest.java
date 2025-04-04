// File: src/test/java/edu/byui/apj/storefront/db/service/OrderServiceTest.java
package edu.byui.apj.storefront.db.service;

import edu.byui.apj.storefront.db.model.CardOrder;
import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartService cartService;

    @Test
    public void testGetOrder() {
        CardOrder order = new CardOrder();
        order.setId(1L);
        order.setOrderDate(new Date());
        order.setTotal(100.0);
        order.setConfirmationSent(false);
        order.setShipMethod("Express");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<CardOrder> result = orderService.getOrder(1L);
        assertTrue(result.isPresent());
        assertEquals("Express", result.get().getShipMethod());
    }

    @Test
    public void testSaveOrder() {
        Cart cart = new Cart();
        cart.setId("cart1");
        cart.setPersonId("person1");

        CardOrder order = new CardOrder();
        order.setOrderDate(new Date());
        order.setTotal(100.0);
        order.setConfirmationSent(false);
        order.setShipMethod("Express");
        order.setCart(cart);

        // When the cart service is asked for the cart, return the cart
        when(cartService.getCart("cart1")).thenReturn(cart);
        when(orderRepository.save(order)).thenReturn(order);

        CardOrder savedOrder = orderService.saveOrder(order);
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getCart());
        assertEquals("cart1", savedOrder.getCart().getId());
        assertEquals("Express", savedOrder.getShipMethod());
    }
}