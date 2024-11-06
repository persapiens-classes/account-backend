package org.persapiens.account.restclient;

import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BalanceRestClient {

	private RestClientHelper<BigDecimal> restClientHelper;

	public BigDecimal balance(String owner, String equityAccount) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(UriComponentsBuilder.fromHttpUrl(restClientHelper.url() + "/balance")
				.queryParam("owner", owner)
				.queryParam("equityAccount", equityAccount)
				.build()
				.encode()
				.toUri())
			.retrieve()
			.body(BigDecimal.class);
	}

}