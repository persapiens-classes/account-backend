package org.persapiens.account.restclient;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.EntryDTO;

@SuperBuilder
@Data
public class EntryRestClientFactory {

	private String protocol;

	private String servername;

	private int port;

	public EntryRestClient entryRestClient() {
		return EntryRestClient.builder()
			.restClientHelper(RestClientHelper.<EntryDTO>builder()
				.endpoint("entry")
				.protocol(this.protocol)
				.servername(this.servername)
				.port(this.port)
				.build())
			.build();
	}

}
