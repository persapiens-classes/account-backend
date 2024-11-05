package org.persapiens.account.restclient;

import org.persapiens.account.dto.CreditAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class CreditAccountRestClient {

    private RestClientHelper<CreditAccountDTO> entityRestHelper;

    public Iterable<CreditAccountDTO> findAll() {
        return this.entityRestHelper.findAll();
    }

    public CreditAccountDTO save(CreditAccountDTO entity) {
        return this.entityRestHelper.getRestTemplate().postForObject(
                entityRestHelper.saveUri(), entity, CreditAccountDTO.class);
    }

    public void deleteByDescription(String description) {
        entityRestHelper.deleteByDescription( description);
    }

    public Optional<CreditAccountDTO> findByDescription(String description) {
        return Optional.ofNullable(this.entityRestHelper.getRestTemplate().getForObject(
                entityRestHelper.findByDescriptionUri(description), CreditAccountDTO.class));
    }

}