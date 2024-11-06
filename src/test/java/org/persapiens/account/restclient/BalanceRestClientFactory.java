package org.persapiens.account.restclient;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class BalanceRestClientFactory {

	private String protocol;

	private String servername;

	private int port;

	public BalanceRestClient balanceRestClient() {
		return BalanceRestClient.builder()
			.restClientHelper(RestClientHelper.<BigDecimal>builder()
				.endpoint("")
				.protocol(this.protocol)
				.servername(this.servername)
				.port(this.port)
				.build())
			.build();
	}

}
