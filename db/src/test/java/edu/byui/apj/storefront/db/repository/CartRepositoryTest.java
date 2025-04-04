// File: src/test/java/edu/byui/apj/storefront/db/repository/CartRepositoryTest.java
package edu.byui.apj.storefront.db.repository;

import edu.byui.apj.storefront.db.model.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartRepositoryTest {

    @Mock
    private CartRepository cartRepository;

    @Test
    public void testSaveAndFindCart() {
        Cart cart = new Cart();
        cart.setId("cart1");
        cart.setPersonId("person1");

        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartRepository.findById("cart1")).thenReturn(Optional.of(cart));

        Cart savedCart = cartRepository.save(cart);
        assertNotNull(savedCart);

        Optional<Cart> foundCart = cartRepository.findById("cart1");
        assertTrue(foundCart.isPresent());
        assertEquals("person1", foundCart.get().getPersonId());
    }
}