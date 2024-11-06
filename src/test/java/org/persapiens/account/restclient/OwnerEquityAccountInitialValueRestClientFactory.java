package org.persapiens.account.restclient;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

@SuperBuilder
@Data
public class OwnerEquityAccountInitialValueRestClientFactory {

	private String protocol;

	private String servername;

	private int port;

	public OwnerEquityAccountInitialValueRestClient ownerEquityAccountInitialValueRestClient() {
		return OwnerEquityAccountInitialValueRestClient.builder()
			.restClientHelper(RestClientHelper.<OwnerEquityAccountInitialValueDTO>builder()
				.endpoint("ownerEquityAccountInitialValue")
				.protocol(this.protocol)
				.servername(this.servername)
				.port(this.port)
				.build())
			.build();
	}

}
