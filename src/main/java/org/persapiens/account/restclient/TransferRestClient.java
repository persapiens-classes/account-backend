package org.persapiens.account.restclient;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.TransferDTO;

import org.springframework.web.util.UriComponentsBuilder;

@SuperBuilder
public class TransferRestClient {

	private RestClientHelper<TransferDTO> restClientHelper;

	public void transfer(TransferDTO transferDTO) {
		this.restClientHelper.getRestClient()
			.post()
			.uri(UriComponentsBuilder.fromUriString(this.restClientHelper.url() + "/transfer").build().encode().toUri())
			.body(transferDTO)
			.retrieve()
			.toBodilessEntity();
	}

}
