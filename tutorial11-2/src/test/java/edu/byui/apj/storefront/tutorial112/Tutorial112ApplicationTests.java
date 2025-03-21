package edu.byui.apj.storefront.tutorial112;

import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;



@SpringBootTest
class Tutorial112ApplicationTests {

	@Autowired ScheduledTasks tasks; // Autowire the actual bean

	@Test
	public void testReportCurrentTime() {
		ScheduledTasks tasksSpy = Mockito.spy(tasks);
		await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> verify(tasksSpy, atLeast(2)).reportCurrentTime());
	}

	@Test
	public void testReportNames() {
		ScheduledTasks tasksSpy = Mockito.spy(tasks);
		// reportName runs every 3 seconds, so it will run at least 3 times in 10 seconds
		await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> verify(tasksSpy, atLeast(3)).reportNames());
	}



}
