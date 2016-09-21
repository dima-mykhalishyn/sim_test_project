package dmihalishin.test.project.user.catalog.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link UserDTO}
 *
 * @author dmihalishin@gmail.com
 */
public class UserDTOTest {

	@Test
	public void testNewInstance() {
		final Long id = 1L;
		final String username = "test";
		final String fName = "f";
		final String lName = "l";
		final UserDTO dto = UserDTO.newInstance(id, username, fName, lName);
		Assert.assertEquals(id, dto.getId());
		Assert.assertEquals(username, dto.getUsername());
		Assert.assertEquals(fName, dto.getFirstName());
		Assert.assertEquals(lName, dto.getLastName());
	}

}