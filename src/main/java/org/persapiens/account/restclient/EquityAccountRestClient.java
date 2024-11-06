package org.persapiens.account.restclient;

import org.persapiens.account.dto.EquityAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class EquityAccountRestClient {

	private RestClientHelper<EquityAccountDTO> restClientHelper;

	public Iterable<EquityAccountDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public EquityAccountDTO save(EquityAccountDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(restClientHelper.saveUri())
			.body(entity)
			.retrieve()
			.body(EquityAccountDTO.class);
	}

	public Optional<EquityAccountDTO> findByDescription(String description) {
		return Optional.ofNullable(this.restClientHelper.getRestClient()
			.get()
			.uri(restClientHelper.findByDescriptionUri(description))
			.retrieve()
			.body(EquityAccountDTO.class));
	}

	public void deleteByDescription(String description) {
		restClientHelper.deleteByDescription(description);
	}

}