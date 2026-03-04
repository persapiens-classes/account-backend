package org.persapiens.account.security;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private HandlerExceptionResolver handlerExceptionResolver;

	private JwtFactory jwtFactory;

	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		String jwt = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			jwt = Arrays.stream(cookies)
				.filter((cookie) -> AuthCookie.NAME.equals(cookie.getName()))
				.map(Cookie::getValue)
				.findFirst()
				.orElse(null);
		}

		if (jwt == null || jwt.isBlank()) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			// user not authenticated yet
			if (authentication == null) {
				// extract username from jwt token
				final String username = this.jwtFactory.extractUsername(jwt);

				// search user details from username
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

				// userDetails is enabled
				if (userDetails.isEnabled()) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}

			filterChain.doFilter(request, response);
		}
		catch (Exception exception) {
			this.handlerExceptionResolver.resolveException(request, response, null, exception);
		}
	}

}
