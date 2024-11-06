package org.persapiens.account.restclient;

import org.persapiens.account.dto.OwnerDTO;

import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class OwnerRestClient {

    private RestClientHelper<OwnerDTO> restClientHelper;

    public Iterable<OwnerDTO> findAll() {
        return this.restClientHelper.findAll();
    }

    public OwnerDTO save(OwnerDTO entity) {
        return this.restClientHelper.getRestClient()
            .post()
            .uri(restClientHelper.saveUri())
            .body(entity)
            .retrieve()
            .body(OwnerDTO.class);
    }

    public Optional<OwnerDTO> findByName(String name) {
        return Optional.ofNullable(this.restClientHelper.getRestClient()
            .get()
            .uri(restClientHelper.uri("/findByName", "name", name))
            .retrieve()
            .body(OwnerDTO.class));
    }

    public void deleteByName(String name) {
        restClientHelper.delete("/deleteByName", "name", name);
    }

}