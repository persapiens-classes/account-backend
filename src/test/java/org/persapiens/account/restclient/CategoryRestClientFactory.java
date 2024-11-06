package org.persapiens.account.restclient;

import java.util.Optional;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.CategoryDTO;

@SuperBuilder
@Data
public class CategoryRestClientFactory {

	private String protocol;

	private String servername;

	private int port;

	public CategoryRestClient categoryRestClient() {
		return CategoryRestClient.builder()
			.restClientHelper(RestClientHelper.<CategoryDTO>builder()
				.endpoint("category")
				.protocol(this.protocol)
				.servername(this.servername)
				.port(this.port)
				.build())
			.build();
	}

	public CategoryDTO category(String description) {
		Optional<CategoryDTO> findByDescription = categoryRestClient().findByDescription(description);
		if (findByDescription.isEmpty()) {
			CategoryDTO result = CategoryDTO.builder().description(description).build();
			return categoryRestClient().save(result);
		}
		else {
			return findByDescription.get();
		}
	}

}
