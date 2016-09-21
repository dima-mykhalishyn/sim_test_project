package dmihalishin.test.project.user.catalog.client.logic;

import dmihalishin.test.project.user.catalog.client.support.SimpleRoundRobinIterator;
import dmihalishin.test.project.user.catalog.client.support.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Logic that update hosts statuses
 *
 * @author dmihalishin@gmail.com
 * @see Runnable
 */
public class NodeHealthCheckRunner implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(NodeHealthCheckRunner.class);

	private final SimpleRoundRobinIterator hostsIterator;

	private final UserCatalogClient client;

	private final String healthPath;

	public NodeHealthCheckRunner(final SimpleRoundRobinIterator hostsIterator,
										  final UserCatalogClient client,
										  final String healthPath) {
		this.hostsIterator = hostsIterator;
		this.client = client;
		this.healthPath = healthPath;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Runnable#run()
	 */
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			this.hostsIterator.getHosts().stream().forEach(host -> {
				final AtomicBoolean available = new AtomicBoolean(true);
				final String url = this.healthPath.replace(UserCatalogClient.HOST_KEY, host);
				try {
					this.client.makeGet(url, Status.class, status -> {
						available.set("UP".equals(status.getStatus()));
					});
				} catch (Exception e) {
					LOGGER.debug("Exception on call {} : {}", url, e.getMessage());
					available.set(false);
				}
				this.hostsIterator.updateHostStatus(host, available.get());
			});
			Utils.sleep(5000, LOGGER);  // sleep 5 seconds
		}
	}
}

// this is internal class, nobody need it except this class
class Status {

	private String status;

	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
}
