package org.persapiens.account.restclient;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

@SuperBuilder
public class OwnerEquityAccountInitialValueRestClient {

	private RestClientHelper<OwnerEquityAccountInitialValueDTO> restClientHelper;

	public Iterable<OwnerEquityAccountInitialValueDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public OwnerEquityAccountInitialValueDTO save(OwnerEquityAccountInitialValueDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.saveUri())
			.body(entity)
			.retrieve()
			.body(OwnerEquityAccountInitialValueDTO.class);
	}

}
