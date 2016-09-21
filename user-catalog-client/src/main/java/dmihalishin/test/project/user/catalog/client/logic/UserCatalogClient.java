package dmihalishin.test.project.user.catalog.client.logic;

import dmihalishin.test.project.user.catalog.client.support.Utils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * User CatalogC lient
 *
 * @author dmihalishin@gmail.com
 */
@Component
public class UserCatalogClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserCatalogClient.class);

	public static final String HOST_KEY = "{host}";

	/**
	 * Make GET call
	 *
	 * @param url           the URL that should be called. Cannot be {@code blank}
	 * @param responseClass the response class. Cannot be {@code blank}
	 * @param consumer      the result consumer. Cannot be {@code null}
	 * @param <T>           the response type
	 * @throws IOException in case of problems
	 */
	public <T> void makeGet(final String url,
									final Class<T> responseClass,
									final Consumer<T> consumer) throws IOException {
		final HttpGet httpGet = new HttpGet(url);
		this.makeCall(url, httpGet, responseClass, consumer);
	}

	/**
	 * Make PUT call
	 *
	 * @param url           the URL that should be called. Cannot be {@code blank}
	 * @param request       the request that will be sended. Cannot be {@code null}
	 * @param responseClass the response class. Cannot be {@code blank}
	 * @param consumer      the result consumer. Cannot be {@code null}
	 * @param <T>           the request type
	 * @param <V>           the response type
	 * @throws IOException in case of problems
	 */
	public <T, V> void makePut(final String url,
										final T request,
										final Class<V> responseClass,
										final Consumer<V> consumer) throws IOException {
		final HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("Content-Type", "application/json");
		httpPut.setEntity(new StringEntity(Utils.writeJsonValue(request), "UTF-8"));
		this.makeCall(url, httpPut, responseClass, consumer);
	}

	/**
	 * Method for all kind of calls
	 *
	 * @param url           the URL that should be called. Cannot be {@code blank}
	 * @param request       the HttpUriRequest
	 * @param responseClass the response class. Cannot be {@code blank}
	 * @param consumer      the result consumer. Cannot be {@code null}
	 * @param <T>           the response type
	 * @throws IOException in case of problems
	 */
	private <T> void makeCall(final String url,
									  final HttpUriRequest request,
									  final Class<T> responseClass,
									  final Consumer<T> consumer) throws IOException {
		try (final CloseableHttpClient httpclient = getHttpClient()) {
			final T response = httpclient.execute(request, this.getResponseHandler(url, responseClass));
			consumer.accept(response);
		}
	}

	private CloseableHttpClient getHttpClient() {
		final RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(2000)
				.setConnectionRequestTimeout(5000)
				.setSocketTimeout(2000)
				.build();
		return HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.build();
	}

	/**
	 * Response handler initializer
	 *
	 * @param url           the URL that should be called. Cannot be {@code blank}
	 * @param responseClass the response class. Cannot be {@code blank}
	 * @param <T>           the response type
	 * @return response value
	 */
	private <T> ResponseHandler<? extends T> getResponseHandler(final String url, final Class<T> responseClass) {
		return response -> {
			final StatusLine statusLine = response.getStatusLine();
			final HttpEntity entity = response.getEntity();
			if (entity == null)
				throw new ClientProtocolException("Response contains no content");
			final String entityStr = EntityUtils.toString(entity);
			LOGGER.debug("Response code from url {} is {}, body: {}", url, response.getStatusLine().getStatusCode(), entityStr);
			if (statusLine.getStatusCode() >= 300)
				throw new HttpResponseException(statusLine.getStatusCode(), entityStr);
			return Utils.readJsonValue(entityStr, responseClass);
		};
	}

}
