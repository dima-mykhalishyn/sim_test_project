package dmihalishin.test.project.user.catalog.client.support;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

import java.io.IOException;

/**
 * Utils
 *
 * @author dmihalishin@gmail.com
 */
public class Utils {

	private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	/**
	 * Convert JSON to a Value
	 *
	 * @param json     the json value. Cannot be {@code blank}
	 * @param response the response class
	 * @param <T>      the response type
	 * @return converted value
	 */
	public static <T> T readJsonValue(final String json, final Class<T> response) {
		try {
			return mapper.readValue(json, response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Convert object to JSON
	 *
	 * @param object the object that will be converted. Cannot be {@code null}
	 * @param <T>    the object type
	 * @return the JSON representation
	 */
	public static <T> String writeJsonValue(T object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sleep(final long millis, final Logger logger) {
		try {
			Thread.currentThread().sleep(millis);
		} catch (InterruptedException e) {
			logger.trace(e.getMessage(), e);
			throw new RuntimeException("Sleep interrupted"); // in case of InterruptedException -> stop execution
		}
	}

}
