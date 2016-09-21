package dmihalishin.test.project.user.catalog.repository;

import dmihalishin.test.project.user.catalog.config.DBConfiguration;
import dmihalishin.test.project.user.catalog.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests for {@link UserRepository}
 *
 * @author dmihalishin@gmail.com
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = DBConfiguration.class)
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	@Rollback
	public void smokeTest() throws Exception {
		Assert.assertNull(this.userRepository.findOne(1L));
		final User user = new User(null, "test", "first", "last");
		this.userRepository.save(user);
		Assert.assertNotNull(user.getId());
		final User result = this.userRepository.findOne(user.getId());
		Assert.assertNotNull(result);
		Assert.assertEquals(user.getId(), result.getId());
		Assert.assertEquals(user.getUsername(), result.getUsername());
		Assert.assertEquals(user.getFirstName(), result.getFirstName());
		Assert.assertEquals(user.getLastName(), result.getLastName());
		Assert.assertEquals(1, this.userRepository.findByUsername(user.getUsername()).size());
	}

}