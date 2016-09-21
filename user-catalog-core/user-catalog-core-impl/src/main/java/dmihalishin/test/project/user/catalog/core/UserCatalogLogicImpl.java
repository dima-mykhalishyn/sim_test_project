package dmihalishin.test.project.user.catalog.core;

import dmihalishin.test.project.user.catalog.model.UserDTO;
import dmihalishin.test.project.user.catalog.core.exception.UserAlreadyExistException;
import dmihalishin.test.project.user.catalog.core.exception.UserNotFoundException;
import dmihalishin.test.project.user.catalog.core.exception.ValidationException;
import dmihalishin.test.project.user.catalog.core.utils.Utils;
import dmihalishin.test.project.user.catalog.domain.User;
import dmihalishin.test.project.user.catalog.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@link UserCatalogLogic} implementation
 *
 * @author dmihalishin@gmail.com
 * @see UserCatalogLogic
 */
@Service
public class UserCatalogLogicImpl implements UserCatalogLogic {

	private final UserService userService;

	private final Validator validator;

	public UserCatalogLogicImpl(final UserService userService, final Validator validator) {
		this.userService = userService;
		this.validator = validator;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see UserCatalogLogic#getById(Long)
	 */
	@Override
	public UserDTO getById(final Long id) {
		if (id == null) {
			throw new ValidationException("User ID cannot be null");
		}
		final Optional<User> result = this.userService.getById(id);
		final User user = result.orElseThrow(() -> new UserNotFoundException("User with ID[" + id + "] was not found"));
		return Utils.userToDTO(user);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see UserCatalogLogic#saveOrUpdate(UserDTO)
	 */
	@Override
	public UserDTO saveOrUpdate(final UserDTO dto) {
		validate(dto);
		final List<User> users = this.userService.getByUsername(dto.getUsername());
		if ((dto.getId() == null && !users.isEmpty()) || users.stream().anyMatch(u -> !u.getId().equals(dto.getId()))) {
			throw new UserAlreadyExistException("User with username[" + dto.getUsername() + "] already exists");
		}
		final User user = Utils.dtoToUser(dto);
		final User result = this.userService.saveOrUpdate(user);
		return Utils.userToDTO(result);
	}

	/**
	 * Validate the {@link UserDTO}
	 *
	 * @param dto the {@code UserDTO} for validation. Cannot be {@code null}
	 * @throws ValidationException in case of invalid user data
	 */
	private void validate(final UserDTO dto) throws ValidationException {
		if (dto == null) {
			throw new ValidationException("User cannot be null");
		}
		final Map<String, String> target = new HashMap<>();
		final MapBindingResult bindingResult = new MapBindingResult(target, "User");
		this.validator.validate(dto, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult.getFieldErrors()
					.stream().map(e -> e.getField() + " -> [" + e.getDefaultMessage() + "]").collect(Collectors.toList()));
		}
	}
}
