package org.persapiens.account.restclient;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.AccountDTO;

@SuperBuilder
public class AccountRestClient {

	private RestClientHelper<AccountDTO> restClientHelper;

	public Iterable<AccountDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public AccountDTO insert(AccountDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.insertUri())
			.body(entity)
			.retrieve()
			.body(AccountDTO.class);
	}

	public AccountDTO findByDescription(String description) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.findByDescriptionUri(description))
			.retrieve()
			.body(AccountDTO.class);
	}

	public void deleteByDescription(String description) {
		this.restClientHelper.deleteByDescription(description);
	}

	public AccountDTO update(String description, AccountDTO entity) {
		return this.restClientHelper.getRestClient()
			.put()
			.uri(this.restClientHelper.updateUri(description))
			.body(entity)
			.retrieve()
			.body(AccountDTO.class);
	}

}
