package org.persapiens.account.restclient;

import java.util.Optional;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.EquityAccountDTO;

@SuperBuilder
public class EquityAccountRestClient {

	private RestClientHelper<EquityAccountDTO> restClientHelper;

	public Iterable<EquityAccountDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public EquityAccountDTO save(EquityAccountDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.saveUri())
			.body(entity)
			.retrieve()
			.body(EquityAccountDTO.class);
	}

	public Optional<EquityAccountDTO> findByDescription(String description) {
		return Optional.ofNullable(this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.findByDescriptionUri(description))
			.retrieve()
			.body(EquityAccountDTO.class));
	}

	public void deleteByDescription(String description) {
		this.restClientHelper.deleteByDescription(description);
	}

}
