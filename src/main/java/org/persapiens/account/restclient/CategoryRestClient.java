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

	public CategoryDTO insert(CategoryDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.insertUri())
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

	public void deleteByDescription(String description) {
		this.restClientHelper.deleteByDescription(description);
	}

	public CategoryDTO update(String description, CategoryDTO entity) {
		return this.restClientHelper.getRestClient()
			.put()
			.uri(this.restClientHelper.updateUri(description))
			.body(entity)
			.retrieve()
			.body(CategoryDTO.class);
	}

}
