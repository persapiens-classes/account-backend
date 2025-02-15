package org.persapiens.account.restclient;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.security.LoginRequestDTO;
import org.persapiens.account.security.LoginResponseDTO;

import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

@SuperBuilder
public class AuthenticationRestClient {

	private RestClientHelper<LoginResponseDTO> restClientHelper;

	public LoginResponseDTO login(LoginRequestDTO loginRequest) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(UriComponentsBuilder.fromUriString(this.restClientHelper.url() + "/login").build().encode().toUri())
			.contentType(MediaType.APPLICATION_JSON)
			.body(loginRequest)
			.retrieve()
			.body(LoginResponseDTO.class);
	}

}
