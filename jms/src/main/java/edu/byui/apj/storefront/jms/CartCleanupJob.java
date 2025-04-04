package edu.byui.apj.storefront.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import edu.byui.apj.storefront.model.Cart;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 3/31 notes: finished everything from the instruction. But received an error message: cannot find symbol method set_id(java.lang.String):59
// I think it's something wrong with the mongoDB

@Component // Any other classes except for the controller, should have the @Component annotation
public class CartCleanupJob {
    private static final Logger logger = LoggerFactory.getLogger(CartCleanupJob.class);
    private final WebClient webClient;

    public CartCleanupJob(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
    }

    public List<Cart> getCartsWithoutOrders(){
        try {
            List<Cart> carts = webClient.get()
                    .uri("/cart/noorder")
                    .retrieve()
                    .bodyToFlux(Cart.class)
                    .collectList()
                    .block();
            if (carts != null) {
                logger.info("Carts without orders retrieved: " + carts);
            }else{
                throw new RuntimeException("No carts received.");
            }
            return carts;
        } catch (Exception ex) {
            logger.error("Error retrieving carts without orders", ex);
            throw new RuntimeException("Failed to retrieve carts without orders", ex);
        }
    }

    public void cleanupCart(String cartId){
        try {
            ClientResponse response = webClient.delete()
                    .uri("/cart/{cartId}", cartId)
                    .retrieve()
                    .toBodilessEntity()
                    .map(entity -> ClientResponse.create(entity.getStatusCode()).build())
                    .block();
            if (response != null && response.statusCode().is2xxSuccessful()) {
                logger.info("Cart {} deleted successfully", cartId);
            } else {
                String statusCode = (response != null) ? response.statusCode().toString() : "Unknown";
                logger.error("Failed to delete cart {}. HTTP Status: {}", cartId, statusCode);
            }
        }catch (Exception ex){
            logger.error("Error while deleting cart {}", cartId, ex);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupCarts(){
        try{
            List<Cart> carts = getCartsWithoutOrders();
            if (carts.isEmpty()){
                logger.info("No carts to cleanup");
                return;
            }
            ExecutorService executor = Executors.newFixedThreadPool(2);
            for (Cart cart : carts){
                executor.submit(() -> cleanupCart(cart.getId()));
            }
            executor.shutdown();
            boolean terminated = executor.awaitTermination(5, TimeUnit.MINUTES); // for awaitTermination, it needs an exception catch to handle the InterruptedException
            if (terminated){
                logger.info("Cart cleanup completed");
            }else{
                logger.error("Cart clean up did not complete for 5 minutes");
            } // comprehsion
        }catch (Exception ex){
            logger.error("Error during cart cleanup", ex);
        }
    }
}
