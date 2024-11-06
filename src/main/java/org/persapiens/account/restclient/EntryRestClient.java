package org.persapiens.account.restclient;

import java.math.BigDecimal;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.EntryDTO;

import org.springframework.web.util.UriComponentsBuilder;

@SuperBuilder
public class EntryRestClient {

	private RestClientHelper<EntryDTO> restClientHelper;

	public Iterable<EntryDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public EntryDTO save(EntryDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.saveUri())
			.body(entity)
			.retrieve()
			.body(EntryDTO.class);
	}

	public BigDecimal creditSum(String owner, String equityAccount) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(UriComponentsBuilder.fromHttpUrl(this.restClientHelper.url() + "/creditSum")
				.queryParam("owner", owner)
				.queryParam("equityAccount", equityAccount)
				.build()
				.encode()
				.toUri())
			.retrieve()
			.body(BigDecimal.class);
	}

	public BigDecimal debitSum(String owner, String equityAccount) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(UriComponentsBuilder.fromHttpUrl(this.restClientHelper.url() + "/debitSum")
				.queryParam("owner", owner)
				.queryParam("equityAccount", equityAccount)
				.build()
				.encode()
				.toUri())
			.retrieve()
			.body(BigDecimal.class);
	}

}
