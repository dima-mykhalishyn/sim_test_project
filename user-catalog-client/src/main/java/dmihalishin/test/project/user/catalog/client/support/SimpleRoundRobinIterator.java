package dmihalishin.test.project.user.catalog.client.support;

import dmihalishin.test.project.user.catalog.client.exception.AvailableHostNotFoundException;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Simple Round Robin Iterator, that return host depends on status flag.
 * In case of false -> value will be ignored.
 * If all values have false status -> throw an Exception
 *
 * @author dmihalishin@gmail.com
 * @see Iterator
 */
public class SimpleRoundRobinIterator implements Iterator<String> {

	private final List<String> hosts;

	private final Map<String, Boolean> values;

	private final AtomicInteger position = new AtomicInteger(0);

	public SimpleRoundRobinIterator(final List<String> values) {
		Validate.notNull(values);
		Validate.isTrue(!values.isEmpty());
		this.hosts = values;
		this.values = new ConcurrentHashMap<>(values.stream().collect(Collectors.toMap(
				s -> s, s -> true
		)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return true; // always true, because it's will go round -> infinity :)
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see Iterator#next()
	 */
	@Override
	public String next() {
		final int position = IntStream.range(0, this.hosts.size()).map(i -> this.getNextPosition()
		).filter(pos -> this.values.get(this.hosts.get(pos))).findFirst().orElseThrow(() -> new AvailableHostNotFoundException("No Available hosts found"));
		return this.hosts.get(position);
	}

	private int getNextPosition() {
		this.position.compareAndSet(this.values.size(), 0);
		return this.position.getAndIncrement();
	}

	public List<String> getHosts() {
		return Collections.unmodifiableList(this.hosts);
	}

	public void updateHostStatus(final String host, final boolean available) {
		this.values.put(host, available);
	}
}
