package dmihalishin.test.project.user.catalog.service;

import dmihalishin.test.project.user.catalog.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * User servcie API
 *
 * @author dmihalishin@gmail.com
 */
public interface UserService {

	/**
	 * Retrieves the {@link User} as {@link Optional} by ID
	 *
	 * @param id the user Id
	 * @return user optional
	 */
	Optional<User> getById(long id);

	/**
	 * Retrieves the list of {@link User}by username
	 *
	 * @param username the user name. Cannot be {@code blank}
	 * @return users
	 */
	List<User> getByUsername(String username);

	/**
	 * Save or update the {@link User}
	 *
	 * @param user the {@code User} for processing. Cannot be {@code null}
	 * @return saved user
	 */
	User saveOrUpdate(User user);

}
