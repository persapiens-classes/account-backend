package org.persapiens.account.security;

import java.io.IOException;
import java.util.List;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class JwtAuthenticationFilterTests {

	@Mock
	private JwtFactory jwtFactory;

	@Mock
	private UserDetailsService userDetailsService;

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private FilterChain filterChain;

	@Mock
	private UserDetails userDetails;

	@Mock
	private HandlerExceptionResolver handlerExceptionResolver;

	@InjectMocks
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		SecurityContextHolder.clearContext();
	}

	private void runFilterAndCheckFilterChainAndJwtFactory() throws ServletException, IOException {
		this.jwtAuthenticationFilter.doFilterInternal(this.request, this.response, this.filterChain);

		then(this.filterChain).should().doFilter(this.request, this.response);
		then(this.jwtFactory).shouldHaveNoMoreInteractions();
	}

	@Test
	void testAuthorizationHeaderNotBearer() throws ServletException, IOException {
		given(this.request.getHeader("Authorization")).willReturn("BASIC 12345678");

		runFilterAndCheckFilterChainAndJwtFactory();
	}

	@Test
	void testAlreadyAuthenticated() throws ServletException, IOException {
		String token = "valid-jwt-token";
		String username = "testUser";

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
				username.trim(), List.of(new SimpleGrantedAuthority("ROLE_USER")));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		given(this.request.getHeader("Authorization")).willReturn("Bearer " + token);

		runFilterAndCheckFilterChainAndJwtFactory();
	}

	@Test
	void testUserDetailsIsNotEnabled() throws ServletException, IOException {
		String token = "valid-jwt-token";
		String username = "testUser";

		given(this.request.getHeader("Authorization")).willReturn("Bearer " + token);
		given(this.jwtFactory.extractUsername(token)).willReturn(username);
		given(this.userDetailsService.loadUserByUsername(username)).willReturn(this.userDetails);
		given(this.userDetails.isEnabled()).willReturn(false);

		this.jwtAuthenticationFilter.doFilterInternal(this.request, this.response, this.filterChain);

		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
	}

	@Test
	void testInvalidJwtToken() throws ServletException, IOException {
		String token = "invalid-jwt-token";
		Exception exception = new JwtException("token error");

		given(this.request.getHeader("Authorization")).willReturn("Bearer " + token);
		given(this.jwtFactory.extractUsername(token)).willThrow(exception);

		this.jwtAuthenticationFilter.doFilterInternal(this.request, this.response, this.filterChain);

		then(this.handlerExceptionResolver).should().resolveException(this.request, this.response, null, exception);
	}

}
