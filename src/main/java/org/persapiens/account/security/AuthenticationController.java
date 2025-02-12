package org.persapiens.account.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnWebApplication
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/")
@RestController
public class AuthenticationController {

	private AuthenticationManager authenticationManager;

	private JwtFactory jwtFactory;

	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Autowired
	public void setJwtFactory(JwtFactory jwtFactory) {
		this.jwtFactory = jwtFactory;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequest) {
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		if (authentication.isAuthenticated()) {
			return ResponseEntity.ok(LoginResponseDTO.builder()
				.token(this.jwtFactory.generateToken(loginRequest.getUsername()))
				.expiresIn(this.jwtFactory.getExpirationTime())
				.build());
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}
