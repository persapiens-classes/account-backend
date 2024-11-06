package org.persapiens.account.restclient;

import java.util.Optional;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.OwnerDTO;

@SuperBuilder
@Data
public class OwnerRestClientFactory {

	private String protocol;

	private String servername;

	private int port;

	public OwnerRestClient ownerRestClient() {
		return OwnerRestClient.builder()
			.restClientHelper(RestClientHelper.<OwnerDTO>builder()
				.endpoint("owner")
				.protocol(this.protocol)
				.servername(this.servername)
				.port(this.port)
				.build())
			.build();
	}

	public OwnerDTO owner(String name) {
		Optional<OwnerDTO> findByDescription = ownerRestClient().findByName(name);
		if (findByDescription.isEmpty()) {
			OwnerDTO result = OwnerDTO.builder().name(name).build();
			return ownerRestClient().save(result);
		}
		else {
			return findByDescription.get();
		}
	}

}
