package org.persapiens.account.restclient;

import java.util.Optional;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.OwnerDTO;

@SuperBuilder
public class OwnerRestClient {

	private RestClientHelper<OwnerDTO> restClientHelper;

	public Iterable<OwnerDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public OwnerDTO insert(OwnerDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.insertUri())
			.body(entity)
			.retrieve()
			.body(OwnerDTO.class);
	}

	public Optional<OwnerDTO> findByName(String name) {
		return Optional.ofNullable(this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.uri("/{name}", "name", name))
			.retrieve()
			.body(OwnerDTO.class));
	}

	public void deleteByName(String name) {
		this.restClientHelper.delete("/{name}", "name", name);
	}

	public OwnerDTO update(String name, OwnerDTO entity) {
		return this.restClientHelper.getRestClient()
			.put()
			.uri(this.restClientHelper.updateUri(name))
			.body(entity)
			.retrieve()
			.body(OwnerDTO.class);
	}

}
