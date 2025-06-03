package org.persapiens.account.restclient;

import java.math.BigDecimal;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.BalanceDTO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;

@SuperBuilder
public class BalanceRestClient {

	private RestClientHelper<BigDecimal> restClientHelper;

	public BalanceDTO balanceByOwnerAndEquityAccount(String owner, String equityAccount) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(UriComponentsBuilder.fromUriString(this.restClientHelper.url() + "/balances/filter")
				.queryParam("owner", owner)
				.queryParam("equityAccount", equityAccount)
				.build()
				.encode()
				.toUri())
			.retrieve()
			.body(BalanceDTO.class);
	}

	public Iterable<BalanceDTO> balanceAll() {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(UriComponentsBuilder.fromUriString(this.restClientHelper.url() + "/balances").build().encode().toUri())
			.retrieve()
			.body(new ParameterizedTypeReference<Iterable<BalanceDTO>>() {
			});
	}

}
