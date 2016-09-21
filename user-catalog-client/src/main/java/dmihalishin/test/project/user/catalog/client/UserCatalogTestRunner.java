package dmihalishin.test.project.user.catalog.client;

import dmihalishin.test.project.user.catalog.client.logic.NodeHealthCheckRunner;
import dmihalishin.test.project.user.catalog.client.logic.TestLogic;
import dmihalishin.test.project.user.catalog.client.logic.UserCatalogClient;
import dmihalishin.test.project.user.catalog.client.support.SimpleRoundRobinIterator;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * User Catalog Test runner.
 * That run test operations for User Catalog Service
 *
 * @author dmihalishin@gmail.com
 */
@SpringBootApplication
@PropertySource("classpath:default.properties")
public class UserCatalogTestRunner {

	private final ExecutorService executorService = Executors.newFixedThreadPool(3);

	public static void main(String[] args) {
		SpringApplication.run(UserCatalogTestRunner.class, args);
	}

	@Bean
	public CommandLineRunner demo(final TestLogic testLogic,
											final UserCatalogClient client,
											@Value("${user.catalog.service.health}") final String healthEndpoint,
											@Value("${calls.amount}") final Integer callsAmount,
											@Value("${call.timeout.seconds}") final Integer timeout
	) {
		return (args) -> {
			Validate.isTrue(args.length > 0);
			final SimpleRoundRobinIterator hosts = new SimpleRoundRobinIterator(
					Arrays.asList(args));
			try {
				final NodeHealthCheckRunner healthCheckRunner = new NodeHealthCheckRunner(hosts, client, healthEndpoint);
				this.executorService.execute(healthCheckRunner);
				final Future result = this.executorService.submit(() -> {
					testLogic.runLogic(hosts, callsAmount, timeout);
					return "Success!";
				});
				result.get(); // wait till end of the logic
			} finally {
				executorService.shutdownNow();
				executorService.awaitTermination(30, TimeUnit.SECONDS);
			}
			//testLogic.runLogic(hosts, 10, 10);
		};
	}


}
