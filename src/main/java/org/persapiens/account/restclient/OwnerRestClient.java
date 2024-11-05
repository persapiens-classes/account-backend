package org.persapiens.account.restclient;

import org.persapiens.account.dto.OwnerDTO;

import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class OwnerRestClient {

    private RestClientHelper<OwnerDTO> entityRestHelper;

    public Iterable<OwnerDTO> findAll() {
        return this.entityRestHelper.findAll();
    }

    public OwnerDTO save(OwnerDTO entity) {
        return this.entityRestHelper.getRestTemplate().postForObject(
                entityRestHelper.saveUri(), entity, OwnerDTO.class);
    }

    public void deleteByName(String name) {
        entityRestHelper.delete("/deleteByName", "name", name);
    }

    public Optional<OwnerDTO> findByName(String name) {
        return Optional.ofNullable(this.entityRestHelper.getRestTemplate().getForObject(
                entityRestHelper.uri("/findByName", "name", name), OwnerDTO.class));
    }

}