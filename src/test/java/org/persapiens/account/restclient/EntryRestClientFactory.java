package org.persapiens.account.restclient;

import org.persapiens.account.dto.EntryDTO;
import lombok.experimental.SuperBuilder;
import lombok.Data;

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
				.protocol(protocol)
				.servername(servername)
				.port(port)
				.build())
			.build();
	}

}