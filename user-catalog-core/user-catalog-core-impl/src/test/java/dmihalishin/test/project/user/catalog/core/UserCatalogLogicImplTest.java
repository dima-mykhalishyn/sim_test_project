package dmihalishin.test.project.user.catalog.core;

import dmihalishin.test.project.user.catalog.model.UserDTO;
import dmihalishin.test.project.user.catalog.core.exception.UserAlreadyExistException;
import dmihalishin.test.project.user.catalog.core.exception.UserNotFoundException;
import dmihalishin.test.project.user.catalog.core.exception.ValidationException;
import dmihalishin.test.project.user.catalog.core.utils.Utils;
import dmihalishin.test.project.user.catalog.domain.User;
import dmihalishin.test.project.user.catalog.service.UserService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import java.util.Collections;
import java.util.Optional;

/**
 * Tests for {@link UserCatalogLogicImpl}
 *
 * @author dmihalishin@gmail.com
 */
public class UserCatalogLogicImplTest {

	private UserCatalogLogic catalogLogic;

	private UserService userService;

	private Validator validator;

	@Before
	public void before() {
		this.validator = EasyMock.createMock(Validator.class);
		this.userService = EasyMock.createMock(UserService.class);
		this.catalogLogic = new UserCatalogLogicImpl(this.userService, this.validator);
	}

	@Test
	public void getById() throws Exception {
		final User user = new User(1L, "test", "a", "b");
		EasyMock.expect(this.userService.getById(1L)).andReturn(Optional.of(user));
		replay();
		final UserDTO dto = this.catalogLogic.getById(1L);
		verify();
		Assert.assertEquals(user.getId(), dto.getId());
		Assert.assertEquals(user.getUsername(), dto.getUsername());
		Assert.assertEquals(user.getFirstName(), dto.getFirstName());
		Assert.assertEquals(user.getLastName(), dto.getLastName());
	}

	@Test(expected = ValidationException.class)
	public void getByIdValidationException() throws Exception {
		this.catalogLogic.getById(null);
	}

	@Test(expected = UserNotFoundException.class)
	public void getByIdUserNotFoundException() throws Exception {
		EasyMock.expect(this.userService.getById(1L)).andReturn(Optional.empty());
		replay();
		try {
			this.catalogLogic.getById(1L);
		} finally {
			verify();
		}
	}

	@Test
	public void saveOrUpdate() throws Exception {
		final User user = new User(1L, "test", "a", "b");
		this.validator.validate(EasyMock.anyObject(UserDTO.class), EasyMock.anyObject());
		EasyMock.expectLastCall();
		EasyMock.expect(this.userService.getByUsername(user.getUsername())).andReturn(Collections.emptyList());
		EasyMock.expect(this.userService.saveOrUpdate(EasyMock.anyObject(User.class))).andReturn(user);
		replay();
		final UserDTO dto = this.catalogLogic.saveOrUpdate(Utils.userToDTO(user));
		verify();
		Assert.assertEquals(user.getId(), dto.getId());
		Assert.assertEquals(user.getUsername(), dto.getUsername());
		Assert.assertEquals(user.getFirstName(), dto.getFirstName());
		Assert.assertEquals(user.getLastName(), dto.getLastName());
	}

	@Test(expected = UserAlreadyExistException.class)
	public void saveOrUpdateDuplicate() throws Exception {
		final User user = new User(1L, "test", "a", "b");
		this.validator.validate(EasyMock.anyObject(UserDTO.class), EasyMock.anyObject());
		EasyMock.expectLastCall();
		EasyMock.expect(this.userService.getByUsername(user.getUsername())).andReturn(Collections.singletonList(new User(2L, "test", "a", "b")));
		replay();
		try {
			this.catalogLogic.saveOrUpdate(Utils.userToDTO(user));
		} finally {
			verify();
		}
	}

	@Test(expected = ValidationException.class)
	public void saveOrUpdateValidation() throws Exception {
		final User user = new User(1L, "test", "a", "b");
		this.validator.validate(EasyMock.anyObject(UserDTO.class), EasyMock.anyObject());
		EasyMock.expectLastCall().andAnswer(() -> {
			final MapBindingResult result = (MapBindingResult) EasyMock.getCurrentArguments()[1];
			result.rejectValue("code", "Invalid");
			return null;
		});
		replay();
		try {
			this.catalogLogic.saveOrUpdate(Utils.userToDTO(user));
		} finally {
			verify();
		}
	}

	@Test(expected = ValidationException.class)
	public void saveOrUpdateNull() throws Exception {
		replay();
		try {
			this.catalogLogic.saveOrUpdate(null);
		} finally {
			verify();
		}
	}

	private void replay() {
		EasyMock.replay(this.userService, this.validator);
	}

	private void verify() {
		EasyMock.verify(this.userService, this.validator);
	}
}