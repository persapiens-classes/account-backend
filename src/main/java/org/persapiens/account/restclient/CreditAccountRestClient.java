package org.persapiens.account.restclient;

import org.persapiens.account.dto.CreditAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class CreditAccountRestClient {

    private RestClientHelper<CreditAccountDTO> restClientHelper;

    public Iterable<CreditAccountDTO> findAll() {
        return this.restClientHelper.findAll();
    }

    public CreditAccountDTO save(CreditAccountDTO entity) {
        return this.restClientHelper.getRestClient()
            .post()
            .uri(restClientHelper.saveUri())
            .body(entity)
            .retrieve()
            .body(CreditAccountDTO.class);
    }

    public Optional<CreditAccountDTO> findByDescription(String description) {
        return Optional.ofNullable(this.restClientHelper.getRestClient()
            .get()
            .uri(restClientHelper.findByDescriptionUri(description))
            .retrieve()
            .body(CreditAccountDTO.class));
    }

    public void deleteByDescription(String description) {
        restClientHelper.deleteByDescription(description);
    }

}