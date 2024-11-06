package org.persapiens.account.restclient;

import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class OwnerEquityAccountInitialValueRestClient {

	private RestClientHelper<OwnerEquityAccountInitialValueDTO> restClientHelper;

	public Iterable<OwnerEquityAccountInitialValueDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public OwnerEquityAccountInitialValueDTO save(OwnerEquityAccountInitialValueDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(restClientHelper.saveUri())
			.body(entity)
			.retrieve()
			.body(OwnerEquityAccountInitialValueDTO.class);
	}

}