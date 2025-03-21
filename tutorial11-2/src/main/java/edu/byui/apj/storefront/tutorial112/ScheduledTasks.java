package edu.byui.apj.storefront.tutorial112;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    int batchSize = 2; // change it to 4 if you want to have the reportName shows faster
    ExecutorService executor = Executors.newFixedThreadPool(batchSize);
    List<String> names = Arrays.asList(
            "Alice", "Bob", "Charlie", "David", "Emma",
            "Frank", "Grace", "Henry", "Ivy", "Jack",
            "Karen", "Liam", "Mia", "Noah", "Olivia",
            "Paul", "Quinn", "Ryan", "Sophia", "Thomas"
    );

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        Runnable displayTime = () -> {
            log.info("Then time is now {}", dateFormat.format(new Date()));;
        };
        executor.submit(displayTime);
    }

    @Scheduled(cron = "0 */1 * * * *")
        public void reportNames() {
        Runnable displayName = () -> {
            for (String name : names) {
                log.info("Then name is {}", name);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("All done here!");
        };
        executor.submit(displayName);
    }


}
