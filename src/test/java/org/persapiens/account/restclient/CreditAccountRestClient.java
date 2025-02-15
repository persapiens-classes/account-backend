package org.persapiens.account.restclient;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.CreditAccountDTO;

@SuperBuilder
public class CreditAccountRestClient {

	private RestClientHelper<CreditAccountDTO> restClientHelper;

	public Iterable<CreditAccountDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public CreditAccountDTO insert(CreditAccountDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.insertUri())
			.body(entity)
			.retrieve()
			.body(CreditAccountDTO.class);
	}

	public CreditAccountDTO findByDescription(String description) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.findByDescriptionUri(description))
			.retrieve()
			.body(CreditAccountDTO.class);
	}

	public void deleteByDescription(String description) {
		this.restClientHelper.deleteByDescription(description);
	}

	public CreditAccountDTO update(String description, CreditAccountDTO entity) {
		return this.restClientHelper.getRestClient()
			.put()
			.uri(this.restClientHelper.updateUri(description))
			.body(entity)
			.retrieve()
			.body(CreditAccountDTO.class);
	}

}
