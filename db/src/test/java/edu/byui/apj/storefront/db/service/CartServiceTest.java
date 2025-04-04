// File: src/test/java/edu/byui/apj/storefront/db/service/CartServiceTest.java
package edu.byui.apj.storefront.db.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import edu.byui.apj.storefront.db.model.Cart;
import edu.byui.apj.storefront.db.repository.CartRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Test
    public void testGetCart() {
        Cart cart = new Cart();
        cart.setId("cart1");
        cart.setPersonId("person1");

        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));

        Cart foundCart = cartService.getCart("cart1");
        assertNotNull(foundCart);
        assertEquals("person1", foundCart.getPersonId());
    }

    @Test
    public void testCreateCart() {
        Cart cart = new Cart();
        cart.setId("cart1");
        cart.setPersonId("person1");

        when(cartRepository.existsById("cart1")).thenReturn(false);
        when(cartRepository.save(cart)).thenReturn(cart);

        Cart createdCart = cartService.createCart(cart);
        assertNotNull(createdCart);
        assertEquals("cart1", createdCart.getId());
    }
}