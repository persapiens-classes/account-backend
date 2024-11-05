package org.persapiens.account.restclient;

import org.persapiens.account.dto.DebitAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class DebitAccountRestClient {

    private RestClientHelper<DebitAccountDTO> entityRestHelper;

    public Iterable<DebitAccountDTO> findAll() {
        return this.entityRestHelper.findAll();
    }

    public DebitAccountDTO save(DebitAccountDTO entity) {
        return this.entityRestHelper.getRestTemplate().postForObject(
                entityRestHelper.saveUri(), entity, DebitAccountDTO.class);
    }

    public void deleteByDescription(String description) {
        entityRestHelper.deleteByDescription( description);
    }

    public Optional<DebitAccountDTO> findByDescription(String description) {
        return Optional.ofNullable(this.entityRestHelper.getRestTemplate().getForObject(
                entityRestHelper.findByDescriptionUri(description), DebitAccountDTO.class));
    }

}