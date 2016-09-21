package dmihalishin.test.project.user.catalog.core.exception;

/**
 * User Already Exist Exception
 *
 * @author dmihalishin@gmail.com
 */
public class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -704145319661010469L;

	public UserAlreadyExistException(final String message) {
		super(message);
	}
}
