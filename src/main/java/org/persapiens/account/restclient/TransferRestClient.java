package org.persapiens.account.restclient;

import org.persapiens.account.dto.TransferDTO;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class TransferRestClient {

    private RestClientHelper<TransferDTO> restClientHelper;

    public void transfer(TransferDTO transferDTO) {
        this.restClientHelper.getRestClient()
            .post()
            .uri(UriComponentsBuilder.fromHttpUrl(restClientHelper.url() + "/transfer").build().encode().toUri())
            .body(transferDTO)
            .retrieve()
            .toBodilessEntity();
    }
}