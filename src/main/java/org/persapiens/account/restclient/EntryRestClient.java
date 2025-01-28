package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;

import org.springframework.web.util.UriComponentsBuilder;

@SuperBuilder
public class EntryRestClient {

	private RestClientHelper<EntryDTO> restClientHelper;

	public Iterable<EntryDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public EntryDTO insert(EntryInsertUpdateDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.insertUri())
			.body(entity)
			.retrieve()
			.body(EntryDTO.class);
	}

	public Optional<EntryDTO> findById(Long id) {
		return Optional.ofNullable(this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.findByIdUri(id))
			.retrieve()
			.body(EntryDTO.class));
	}

	public void deleteById(Long id) {
		this.restClientHelper.deleteById(id);
	}

	public EntryDTO update(Long id, EntryInsertUpdateDTO entity) {
		return this.restClientHelper.getRestClient()
			.put()
			.uri(this.restClientHelper.updateUri(id.toString()))
			.body(entity)
			.retrieve()
			.body(EntryDTO.class);
	}

	public BigDecimal creditSum(String owner, String equityAccount) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(UriComponentsBuilder.fromUriString(this.restClientHelper.url() + "/creditSum")
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
			.uri(UriComponentsBuilder.fromUriString(this.restClientHelper.url() + "/debitSum")
				.queryParam("owner", owner)
				.queryParam("equityAccount", equityAccount)
				.build()
				.encode()
				.toUri())
			.retrieve()
			.body(BigDecimal.class);
	}

}
