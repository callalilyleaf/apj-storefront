// File: src/test/java/edu/byui/apj/storefront/db/controller/CartControllerTest.java
package edu.byui.apj.storefront.db.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @Test
    public void testGetCart() {
        Cart cart = new Cart();
        cart.setId("cart1");
        cart.setPersonId("person1");
        when(cartService.getCart("cart1")).thenReturn(cart);

        ResponseEntity<Cart> responseEntity = cartController.getCart("cart1");
        Cart response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("person1", response.getPersonId());
    }

    @Test
    public void testSaveCart() {
        Cart cart = new Cart();
        cart.setId("cart1");
        cart.setPersonId("person1");
        when(cartService.saveCart(cart)).thenReturn(cart);

        ResponseEntity<Cart> responseEntity = cartController.saveCart(cart);
        Cart response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("cart1", response.getId());
    }
}