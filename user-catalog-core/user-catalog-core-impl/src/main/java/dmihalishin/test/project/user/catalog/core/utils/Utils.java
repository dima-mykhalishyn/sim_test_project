package dmihalishin.test.project.user.catalog.core.utils;

import dmihalishin.test.project.user.catalog.model.UserDTO;
import dmihalishin.test.project.user.catalog.domain.User;

/**
 * Utils class
 *
 * @author dmihalishin@gmail.com
 */
public final class Utils {

	/**
	 * Convert {@link User} to {@link UserDTO}
	 *
	 * @param user the user that will be converted. Cannot be {@code null}
	 * @return the user dto
	 */
	public static UserDTO userToDTO(final User user) {
		return UserDTO.newInstance(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
	}

	/**
	 * Convert {@link UserDTO} to {@link User}
	 *
	 * @param dto the user dto that will be converted. Cannot be {@code null}
	 * @return the user
	 */
	public static User dtoToUser(final UserDTO dto) {
		return new User(dto.getId(), dto.getUsername(), dto.getFirstName(), dto.getLastName());
	}

}
