package edu.byui.apj.storefront.tutorial121;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat; // Don't forget to add static!
import org.junit.jupiter.api.Test;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private HomeController homeController;

    @Test
    void contextLoads() throws Exception{
        assertThat(homeController).isNotNull();
    }
}
