package dmihalishin.test.project.user.catalog.client.exception;

/**
 * Available Host Not Found Exception
 *
 * @author dmihalishin@gmail.com
 * @see RuntimeException
 */
public class AvailableHostNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5478403654765205453L;

	public AvailableHostNotFoundException(final String message) {
		super(message);
	}
}
