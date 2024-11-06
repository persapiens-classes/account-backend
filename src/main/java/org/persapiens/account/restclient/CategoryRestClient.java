package org.persapiens.account.restclient;

import java.util.Optional;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.CategoryDTO;

@SuperBuilder
public class CategoryRestClient {

	private RestClientHelper<CategoryDTO> restClientHelper;

	public Iterable<CategoryDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public CategoryDTO save(CategoryDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.saveUri())
			.body(entity)
			.retrieve()
			.body(CategoryDTO.class);
	}

	public Optional<CategoryDTO> findByDescription(String description) {
		return Optional.ofNullable(this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.findByDescriptionUri(description))
			.retrieve()
			.body(CategoryDTO.class));
	}

}
