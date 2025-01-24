package org.persapiens.account;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@SuppressFBWarnings("SPRING_CSRF_PROTECTION_DISABLED")
@EnableConfigurationProperties(UserCredentials.class)
@EnableWebSecurity
@Configuration
@ConditionalOnWebApplication
public class SecurityConfig {

	@SuppressWarnings("PMD.SignatureDeclareThrowsException")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests((auth) -> auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**")
				.permitAll()
				.anyRequest()
				.authenticated())
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService(UserCredentials userCredentials) {
		PasswordEncoder encoder = passwordEncoder();
		InMemoryUserDetailsManager result = new InMemoryUserDetailsManager();
		result.createUser(User.builder()
			.username(userCredentials.getName())
			.password(encoder.encode(userCredentials.getPassword()))
			.authorities(userCredentials.getAuthorities().toArray(new String[userCredentials.getAuthorities().size()]))
			.build());
		return result;
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.components(new Components().addSecuritySchemes("basicAuth",
					new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
			.addSecurityItem(new SecurityRequirement().addList("basicAuth"));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
