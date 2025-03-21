package org.persapiens.account.restclient;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@SuperBuilder
public class RestClientHelper<T> {

	private String endpoint;

	private String protocol;

	private String servername;

	private int port;

	private String jwtToken;

	public RestClient getRestClient() {
		return RestClient.builder().requestInterceptor(new ClientHttpRequestInterceptor() {
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				if (RestClientHelper.this.jwtToken != null && !RestClientHelper.this.jwtToken.isEmpty()) {
					request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + RestClientHelper.this.jwtToken);
				}
				return execution.execute(request, body);
			}
		}).defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, responseException) -> {
			throw new HttpClientErrorException(responseException.getStatusCode());
		}).build();
	}

	public String url() {
		return this.protocol + "://" + this.servername + ":" + this.port + "/" + this.endpoint;
	}

	private URI uri() {
		return UriComponentsBuilder.fromUriString(url()).build().encode().toUri();
	}

	public URI uri(String suffix, String param, String value) {
		Map<String, Object> uriVariables = new HashMap<>();
		uriVariables.put(param, value);
		return uri(suffix, uriVariables);
	}

	public URI uri(String suffix, Map<String, Object> uriVariables) {
		return UriComponentsBuilder.fromUriString(url() + suffix).uriVariables(uriVariables).build().encode().toUri();
	}

	public void delete(String suffix, String param, String value) {
		getRestClient().delete().uri(uri(suffix, param, value)).retrieve().toBodilessEntity();
	}

	public void delete(String suffix, Map<String, Object> uriVariables) {
		getRestClient().delete().uri(uri(suffix, uriVariables)).retrieve().toBodilessEntity();
	}

	public void deleteById(Long id) {
		delete("/{id}", "id", id.toString());
	}

	public void deleteByDescription(String description) {
		delete("/{description}", "description", description);
	}

	public Iterable<T> findAll() {
		return getRestClient().get().uri(findAllUri()).retrieve().body(new ParameterizedTypeReference<Iterable<T>>() {
		});
	}

	public URI findByUri(String suffix, Map<String, Object> uriVariables) {
		return uri(suffix, uriVariables);
	}

	public URI findByDescriptionUri(String description) {
		return uri("/{description}", "description", description);
	}

	public URI findByIdUri(Long id) {
		return uri("/{id}", "id", id.toString());
	}

	public URI findAllUri() {
		return uri();
	}

	public URI insertUri() {
		return uri();
	}

	public URI updateUri(String value) {
		return uri("/{id}", "id", value);
	}

	public URI updateUri(String suffix, Map<String, Object> uriVariables) {
		return uri(suffix, uriVariables);
	}

}
