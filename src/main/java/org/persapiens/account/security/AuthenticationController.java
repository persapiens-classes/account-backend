package org.persapiens.account.security;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnWebApplication
@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

	private AuthenticationManager authenticationManager;

	private JwtFactory jwtFactory;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> authenticate(@Valid @RequestBody LoginRequestDTO loginRequest) {
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
			String token = this.jwtFactory.generateToken(loginRequest.username());
			long expiresIn = this.jwtFactory.getExpirationTime();
			return ResponseEntity.ok(new LoginResponseDTO(loginRequest.username(), token, expiresIn));
		}
		catch (BadCredentialsException _) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/me")
	public ResponseEntity<LoginResponseDTO> me(Authentication authentication) {
		if (authentication == null || authentication.getName() == null || authentication.getName().isBlank()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(new LoginResponseDTO(authentication.getName(), null, this.jwtFactory.getExpirationTime()));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		return ResponseEntity.noContent().build();
	}

}
