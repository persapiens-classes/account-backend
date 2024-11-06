package org.persapiens.account.restclient;

import org.persapiens.account.dto.DebitAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class DebitAccountRestClient {

    private RestClientHelper<DebitAccountDTO> restClientHelper;

    public Iterable<DebitAccountDTO> findAll() {
        return this.restClientHelper.findAll();
    }

    public DebitAccountDTO save(DebitAccountDTO entity) {
        return this.restClientHelper.getRestClient()
            .post()
            .uri(restClientHelper.saveUri())
            .body(entity)
            .retrieve()
            .body(DebitAccountDTO.class);
    }

    public Optional<DebitAccountDTO> findByDescription(String description) {
        return Optional.ofNullable(this.restClientHelper.getRestClient()
            .get()
            .uri(restClientHelper.findByDescriptionUri(description))
            .retrieve()
            .body(DebitAccountDTO.class));
    }

    public void deleteByDescription(String description) {
        restClientHelper.deleteByDescription(description);
    }

}