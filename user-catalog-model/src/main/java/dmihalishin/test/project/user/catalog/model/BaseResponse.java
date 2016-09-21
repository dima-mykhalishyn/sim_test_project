package dmihalishin.test.project.user.catalog.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Base Response
 *
 * @author dmihalishin@gmail.com
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> implements Serializable {

	private static final long serialVersionUID = -4030116489797950556L;

	public BaseResponse() {
		// default
	}

	public BaseResponse(final T response) {
		this.response = response;
	}

	private T response;

	private List<String> errors;

	public T getResponse() {
		return this.response;
	}

	public void setResponse(final T response) {
		this.response = response;
	}

	public List<String> getErrors() {
		return this.errors;
	}

	public void setErrors(final List<String> errors) {
		this.errors = errors;
	}
}
