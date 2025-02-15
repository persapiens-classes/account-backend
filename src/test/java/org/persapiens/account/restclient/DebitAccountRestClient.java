package org.persapiens.account.restclient;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.DebitAccountDTO;

@SuperBuilder
public class DebitAccountRestClient {

	private RestClientHelper<DebitAccountDTO> restClientHelper;

	public Iterable<DebitAccountDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public DebitAccountDTO insert(DebitAccountDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.insertUri())
			.body(entity)
			.retrieve()
			.body(DebitAccountDTO.class);
	}

	public DebitAccountDTO findByDescription(String description) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.findByDescriptionUri(description))
			.retrieve()
			.body(DebitAccountDTO.class);
	}

	public void deleteByDescription(String description) {
		this.restClientHelper.deleteByDescription(description);
	}

	public DebitAccountDTO update(String description, DebitAccountDTO entity) {
		return this.restClientHelper.getRestClient()
			.put()
			.uri(this.restClientHelper.updateUri(description))
			.body(entity)
			.retrieve()
			.body(DebitAccountDTO.class);
	}

}
