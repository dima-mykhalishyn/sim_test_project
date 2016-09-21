package dmihalishin.test.project.user.catalog.core.exception;

import java.util.Collections;
import java.util.List;

/**
 * Validation Exception
 *
 * @author dmihalishin@gmail.com
 * @see RuntimeException
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 7906872243998427595L;

	private final List<String> messages;

	public ValidationException(final String message) {
		super(message);
		this.messages = Collections.singletonList(message);
	}

	public ValidationException(final List<String> messages) {
		super(messages.toString());
		this.messages = messages;
	}

	public List<String> getMessages() {
		return this.messages;
	}
}
