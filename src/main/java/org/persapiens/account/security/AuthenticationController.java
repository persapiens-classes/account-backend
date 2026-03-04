package org.persapiens.account.security;

import java.time.Duration;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CookieValue;
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
			ResponseCookie cookie = ResponseCookie.from(AuthCookie.NAME, token)
				.httpOnly(true)
				.secure(true)
				.sameSite("Strict")
				.path("/")
				.maxAge(Duration.ofMillis(expiresIn))
				.build();
			return ResponseEntity.ok()
				.header("Set-Cookie", cookie.toString())
				.body(new LoginResponseDTO(loginRequest.username(), expiresIn));
		}
		catch (BadCredentialsException _) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@GetMapping("/me")
	public ResponseEntity<LoginResponseDTO> me(
			@CookieValue(value = AuthCookie.NAME, required = false) String token) {
		if (token == null || token.isBlank()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		String username = this.jwtFactory.extractUsername(token);
		return ResponseEntity.ok(new LoginResponseDTO(username, this.jwtFactory.getExpirationTime()));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		ResponseCookie cookie = ResponseCookie.from(AuthCookie.NAME, "")
			.httpOnly(true)
			.secure(true)
			.sameSite("Strict")
			.path("/")
			.maxAge(Duration.ZERO)
			.build();
		return ResponseEntity.noContent().header("Set-Cookie", cookie.toString()).build();
	}

}
