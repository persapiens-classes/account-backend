package org.persapiens.account.restclient;

import org.persapiens.account.dto.CategoryDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class CategoryRestClient {

    private RestClientHelper<CategoryDTO> restClientHelper;

    public Iterable<CategoryDTO> findAll() {
        return this.restClientHelper.findAll();
    }

    public CategoryDTO save(CategoryDTO entity) {
        return this.restClientHelper.getRestClient()
            .post()
            .uri(restClientHelper.saveUri())
            .body(entity)
            .retrieve()
            .body(CategoryDTO.class);
    }

    public Optional<CategoryDTO> findByDescription(String description) {
        return Optional.ofNullable(this.restClientHelper.getRestClient()
            .get()
            .uri(restClientHelper.findByDescriptionUri(description))
            .retrieve()
            .body(CategoryDTO.class));
    }

}