package dmihalishin.test.project.user.catalog.client.support;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Tests for {@link SimpleRoundRobinIterator}
 *
 * @author dmihalishin@gmail.com
 */
public class SimpleRoundRobinIteratorTest {

	@Test
	public void nextWithStatus() throws Exception {
		final List<String> data = Arrays.asList("a", "b");
		final SimpleRoundRobinIterator iterator = new SimpleRoundRobinIterator(data);
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals("a", iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals("b", iterator.next());
		iterator.updateHostStatus("a", false);
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals("b", iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals("b", iterator.next());
		iterator.updateHostStatus("a", true);
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals("a", iterator.next());
	}

	@Test
	public void next() throws Exception {
		final List<String> data = Arrays.asList("a", "b");
		final SimpleRoundRobinIterator iterator = new SimpleRoundRobinIterator(data);
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals("a", iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals("b", iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals("a", iterator.next());
		Assert.assertTrue(iterator.hasNext());
		Assert.assertEquals("b", iterator.next());
	}

}