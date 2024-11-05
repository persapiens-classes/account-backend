package org.persapiens.account.restclient;

import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BalanceRestClient {

    private RestClientHelper<BigDecimal> entityRestHelper;

    public BigDecimal balance(String owner, String equityAccount) {
        return this.entityRestHelper.getRestTemplate().getForObject(
        UriComponentsBuilder.fromHttpUrl(entityRestHelper.url() + "/balance")
            .queryParam("owner", owner)
            .queryParam("equityAccount", equityAccount)
            .build().encode().toUri()
            , BigDecimal.class);
    }
}