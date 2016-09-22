package dmihalishin.test.project.user.catalog.client.logic;

import dmihalishin.test.project.user.catalog.client.support.UserCatalogResponse;
import dmihalishin.test.project.user.catalog.client.support.Utils;
import dmihalishin.test.project.user.catalog.model.UserDTO;
import org.apache.commons.lang3.Validate;
import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * @author dmihalishin@gmail.com
 */
@Component
public class TestLogic {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestLogic.class);

	private static final String ID_KEY = "{id}";

	private final UserCatalogClient client;

	private final ThreadLocalRandom random = ThreadLocalRandom.current();

	private final String getByIdPath;

	private final String savePath;

	@Autowired
	public TestLogic(final UserCatalogClient client,
						  @Value("${user.catalog.service.getbyid}") final String getByIdPath,
						  @Value("${user.catalog.service.save}") final String savePath) {
		this.client = client;
		this.getByIdPath = getByIdPath;
		this.savePath = savePath;
	}

	/**
	 * Run test logic
	 *
	 * @param hostIterator     the host iterator. Cannot be {@code null}
	 * @param calls            the amount of GET calls that should be done in a loop.
	 * @param timeoutInSeconds timeout in seconds per one iteration
	 */
	public void runLogic(final Iterator<String> hostIterator,
								final int calls, final int timeoutInSeconds) {
		Validate.notNull(hostIterator);
		IntStream.range(0, calls).boxed().forEach(i -> {
			if (hostIterator.hasNext()) {
				try {
					final String nextHost = hostIterator.next();
					final int id = this.random.nextInt(calls + 1); // exclusive this value
					final String url = this.getByIdPath.replace(UserCatalogClient.HOST_KEY, nextHost).replace(ID_KEY, String.valueOf(id));
					try {
						this.client.makeGet(url, UserCatalogResponse.class,
								response -> {
									LOGGER.info("Get User from {} with ID {} : {}", url, id, response.getResponse().getUsername());
								});
					} catch (HttpResponseException e) {
						LOGGER.info("Call {} and get error code {} : {}", nextHost, e.getStatusCode(), e.getMessage());
						if (e.getStatusCode() == 404) { // try to create one :)
							final UserDTO dto = UserDTO.newInstance(null, "username" + id, "user", String.valueOf(i));
							this.createUser(nextHost, dto);
						}
					} catch (SocketTimeoutException e) {
						// Here could be Failover logic, something like retry, but for test purpose it's not necessary
						LOGGER.info("SocketTimeoutException for {}: {}", nextHost, e.getMessage());
					}
				} catch (Exception e) {
					LOGGER.error("Exception: {}", e.getMessage());
				}
				Utils.sleep(timeoutInSeconds * 1000, LOGGER);  // sleep N seconds
			}
		});
	}

	/**
	 * Create User on specific host
	 *
	 * @param host where to create user. Cannot be {@code blank}
	 * @param dto  the user dto that will be created. Cannot be {@code null}
	 */
	private void createUser(final String host, final UserDTO dto) {
		final String url = this.savePath.replace(UserCatalogClient.HOST_KEY, host);
		try {
			this.client.makePut(url, dto, UserCatalogResponse.class, response -> {
				LOGGER.info("Save User in {} with username {} and got ID {}", url, dto.getUsername(), response.getResponse().getId());
			});
		} catch (IOException e) {
			// Here could be Failover logic, something like retry, but for test purpose it's not necessary
			LOGGER.info("Create User {} : {}", url, e.getMessage());
		}
	}
}
