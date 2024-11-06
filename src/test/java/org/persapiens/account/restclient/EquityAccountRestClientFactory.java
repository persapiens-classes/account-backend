package org.persapiens.account.restclient;

import java.util.Optional;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.EquityAccountDTO;

@SuperBuilder
@Data
public class EquityAccountRestClientFactory {

	private String protocol;

	private String servername;

	private int port;

	private CategoryRestClientFactory categoryRestClientFactory;

	public EquityAccountRestClient equityAccountRestClient() {
		return EquityAccountRestClient.builder()
			.restClientHelper(RestClientHelper.<EquityAccountDTO>builder()
				.endpoint("equityAccount")
				.protocol(this.protocol)
				.servername(this.servername)
				.port(this.port)
				.build())
			.build();
	}

	public EquityAccountDTO equityAccount(String description, String categoryDescription) {
		Optional<EquityAccountDTO> findByDescription = equityAccountRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			CategoryDTO category = this.categoryRestClientFactory.category(categoryDescription);
			EquityAccountDTO result = EquityAccountDTO.builder().description(description).category(category).build();
			return equityAccountRestClient().save(result);
		}
		else {
			return findByDescription.get();
		}
	}

}
