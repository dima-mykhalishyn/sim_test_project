package dmihalishin.test.project.user.catalog.controller.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import dmihalishin.test.project.user.catalog.model.BaseResponse;
import dmihalishin.test.project.user.catalog.core.exception.UserAlreadyExistException;
import dmihalishin.test.project.user.catalog.core.exception.UserNotFoundException;
import dmihalishin.test.project.user.catalog.core.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

/**
 * Exception handler
 *
 * @author dmihalishin@gmail.com
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public BaseResponse handleException(final Exception e) throws JsonProcessingException {
		LOGGER.error("Handle Exception {}", e.getMessage(), e);
		return this.errorResponse(Collections.singletonList(e.getMessage()));
	}

	@ExceptionHandler(value = UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public BaseResponse handleNotFoundException(final Exception e) throws JsonProcessingException {
		LOGGER.warn("Handle {} {}", e.getClass().getName(), e.getMessage());
		return this.errorResponse(Collections.singletonList(e.getMessage()));
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BaseResponse handleValidationException(final ValidationException e) throws JsonProcessingException {
		LOGGER.error("Handle ValidationException {}", e.getMessage());
		return this.errorResponse(e.getMessages());
	}

	@ExceptionHandler(UserAlreadyExistException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BaseResponse handleUserAlreadyExistException(final UserAlreadyExistException e) throws JsonProcessingException {
		LOGGER.error("Handle UserAlreadyExistException {}", e.getMessage());
		return this.errorResponse(Collections.singletonList(e.getMessage()));
	}

	private BaseResponse errorResponse(final List<String> errorMessages) throws JsonProcessingException {
		final BaseResponse response = new BaseResponse();
		response.setErrors(errorMessages);
		return response;
	}

}
