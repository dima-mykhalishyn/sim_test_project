package dmihalishin.test.project.user.catalog.repository;

import dmihalishin.test.project.user.catalog.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User Repository
 * @author dmihalishin@gmail.com
 */
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * Find users by username.
	 * @param username the username. Cannot be {@code blank}
	 * @return list of users with usch username
	 */
	List<User> findByUsername(String username);

}
