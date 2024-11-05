package org.persapiens.account.restclient;

import org.persapiens.account.dto.TransferDTO;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class TransferRestClient {

    private RestClientHelper<TransferDTO> entityRestHelper;

    public void transfer(TransferDTO transferDTO) {
        this.entityRestHelper.getRestTemplate().postForObject(
            UriComponentsBuilder.fromHttpUrl(entityRestHelper.url() + "/transfer").build().encode().toUri(),
            transferDTO, Void.class);
    }
}