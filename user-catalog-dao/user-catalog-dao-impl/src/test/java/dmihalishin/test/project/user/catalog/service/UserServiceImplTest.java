package dmihalishin.test.project.user.catalog.service;

import dmihalishin.test.project.user.catalog.domain.User;
import dmihalishin.test.project.user.catalog.repository.UserRepository;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Tests for {@link UserServiceImpl}
 *
 * @author dmihalishin@gmail.com
 */
public class UserServiceImplTest {

	private UserServiceImpl service;

	private UserRepository repository;

	@Before
	public void before() {
		this.repository = EasyMock.createMock(UserRepository.class);
		this.service = new UserServiceImpl(this.repository);
	}

	@Test
	public void getById() throws Exception {
		final User user = new User();
		EasyMock.expect(this.repository.findOne(15L)).andReturn(user);
		EasyMock.expect(this.repository.findOne(1L)).andReturn(null);
		EasyMock.replay(this.repository);
		final Optional<User> result = this.service.getById(15L);
		final Optional<User> none = this.service.getById(1L);
		EasyMock.verify(this.repository);
		Assert.assertTrue(!none.isPresent());
		Assert.assertTrue(result.isPresent());
		Assert.assertEquals(user, result.get());
	}

	@Test
	public void findByUsername() throws Exception {
		final User user = new User();
		user.setUsername("test");
		EasyMock.expect(this.repository.findByUsername(user.getUsername())).andReturn(Collections.singletonList(user));
		EasyMock.replay(this.repository);
		final List<User> result = this.service.getByUsername(user.getUsername());
		EasyMock.verify(this.repository);
		Assert.assertEquals(user, result.get(0));
	}

	@Test
	public void saveOrUpdate() throws Exception {
		final User user = new User();
		EasyMock.expect(this.repository.save(user)).andReturn(user);
		EasyMock.replay(this.repository);
		final User result = this.service.saveOrUpdate(user);
		EasyMock.verify(this.repository);
		Assert.assertEquals(user, result);
	}

}