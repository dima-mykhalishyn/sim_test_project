package dmihalishin.test.project.user.catalog.core.exception;

/**
 * User Not Found Exception
 *
 * @author dmihalishin@gmail.com
 * @see RuntimeException
 */
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(final String message) {
		super(message);
	}
}
