package org.persapiens.account.restclient;

import org.persapiens.account.dto.CategoryDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class CategoryRestClient {

    private RestClientHelper<CategoryDTO> entityRestHelper;

    public Iterable<CategoryDTO> findAll() {
        return this.entityRestHelper.findAll();
    }

    public CategoryDTO save(CategoryDTO entity) {
        return this.entityRestHelper.getRestTemplate().postForObject(
                entityRestHelper.saveUri(), entity, CategoryDTO.class);
    }

    public Optional<CategoryDTO> findByDescription(String description) {
        return Optional.ofNullable(this.entityRestHelper.getRestTemplate().getForObject(
                entityRestHelper.findByDescriptionUri(description), CategoryDTO.class));
    }

}