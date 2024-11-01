package org.persapiens.account;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AccountApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
			.sources(AccountApplication.class)
			.run(args);
	}

}
