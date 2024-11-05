package org.persapiens.account.restclient;

import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class OwnerEquityAccountInitialValueRestClient {

    private RestClientHelper<OwnerEquityAccountInitialValueDTO> entityRestHelper;

    public Iterable<OwnerEquityAccountInitialValueDTO> findAll() {
        return this.entityRestHelper.findAll();
    }

    public OwnerEquityAccountInitialValueDTO save(OwnerEquityAccountInitialValueDTO entity) {
        return this.entityRestHelper.getRestTemplate().postForObject(
                entityRestHelper.saveUri(), entity, OwnerEquityAccountInitialValueDTO.class);
    }

}