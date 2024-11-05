package org.persapiens.account.restclient;

import org.persapiens.account.dto.EquityAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class EquityAccountRestClient {

    private RestClientHelper<EquityAccountDTO> entityRestHelper;

    public Iterable<EquityAccountDTO> findAll() {
        return this.entityRestHelper.findAll();
    }

    public EquityAccountDTO save(EquityAccountDTO entity) {
        return this.entityRestHelper.getRestTemplate().postForObject(
                entityRestHelper.saveUri(), entity, EquityAccountDTO.class);
    }

    public void deleteByDescription(String description) {
        entityRestHelper.deleteByDescription( description);
    }

    public Optional<EquityAccountDTO> findByDescription(String description) {
        return Optional.ofNullable(this.entityRestHelper.getRestTemplate().getForObject(
                entityRestHelper.findByDescriptionUri(description), EquityAccountDTO.class));
    }

}