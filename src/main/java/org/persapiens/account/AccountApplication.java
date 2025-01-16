package org.persapiens.account;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AccountApplication implements WebMvcConfigurer {

	@SuppressFBWarnings("PERMISSIVE_CORS")
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(AccountApplication.class).run(args);
	}

}
