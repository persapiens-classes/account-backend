package org.persapiens.account.restclient;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.security.LoginRequestDTO;
import org.persapiens.account.security.LoginResponseDTO;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@SuperBuilder
public class AuthenticationRestClient {

	private RestClientHelper<LoginResponseDTO> restClientHelper;

	public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequest) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(UriComponentsBuilder.fromUriString(this.restClientHelper.url() + "/auth/login")
				.build()
				.encode()
				.toUri())
			.contentType(MediaType.APPLICATION_JSON)
			.body(loginRequest)
			.retrieve()
			.toEntity(LoginResponseDTO.class);
	}

	public ResponseEntity<LoginResponseDTO> me() {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(UriComponentsBuilder.fromUriString(this.restClientHelper.url() + "/auth/me").build().encode().toUri())
			.retrieve()
			.toEntity(LoginResponseDTO.class);
	}

	public ResponseEntity<Void> logout() {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(UriComponentsBuilder.fromUriString(this.restClientHelper.url() + "/auth/logout")
				.build()
				.encode()
				.toUri())
			.retrieve()
			.toBodilessEntity();
	}

}
