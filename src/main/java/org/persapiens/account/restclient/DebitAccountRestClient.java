package org.persapiens.account.restclient;

import java.util.Optional;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.DebitAccountDTO;

@SuperBuilder
public class DebitAccountRestClient {

	private RestClientHelper<DebitAccountDTO> restClientHelper;

	public Iterable<DebitAccountDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public DebitAccountDTO save(DebitAccountDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.saveUri())
			.body(entity)
			.retrieve()
			.body(DebitAccountDTO.class);
	}

	public Optional<DebitAccountDTO> findByDescription(String description) {
		return Optional.ofNullable(this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.findByDescriptionUri(description))
			.retrieve()
			.body(DebitAccountDTO.class));
	}

	public void deleteByDescription(String description) {
		this.restClientHelper.deleteByDescription(description);
	}

}
