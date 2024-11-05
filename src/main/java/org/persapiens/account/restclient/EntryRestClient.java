package org.persapiens.account.restclient;

import org.persapiens.account.dto.EntryDTO;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class EntryRestClient {

    private RestClientHelper<EntryDTO> entityRestHelper;

    public Iterable<EntryDTO> findAll() {
        return this.entityRestHelper.findAll();
    }

    public EntryDTO save(EntryDTO entity) {
        return this.entityRestHelper.getRestTemplate().postForObject(
                entityRestHelper.saveUri(), entity, EntryDTO.class);
    }

    public BigDecimal creditSum(String owner, String equityAccount) {
        return this.entityRestHelper.getRestTemplate().getForObject(
        UriComponentsBuilder.fromHttpUrl(entityRestHelper.url() + "/creditSum")
            .queryParam("owner", owner)
            .queryParam("equityAccount", equityAccount)
            .build().encode().toUri()
            , BigDecimal.class);
    }

    public BigDecimal debitSum(String owner, String equityAccount) {
        return this.entityRestHelper.getRestTemplate().getForObject(
        UriComponentsBuilder.fromHttpUrl(entityRestHelper.url() + "/debitSum")
            .queryParam("owner", owner)
            .queryParam("equityAccount", equityAccount)
            .build().encode().toUri()
            , BigDecimal.class);
    }    
}