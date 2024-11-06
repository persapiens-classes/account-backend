package org.persapiens.account.restclient;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.TransferDTO;

@SuperBuilder
@Data
public class TransferRestClientFactory {

	private String protocol;

	private String servername;

	private int port;

	public TransferRestClient transferRestClient() {
		return TransferRestClient.builder()
			.restClientHelper(RestClientHelper.<TransferDTO>builder()
				.endpoint("")
				.protocol(this.protocol)
				.servername(this.servername)
				.port(this.port)
				.build())
			.build();
	}

}
