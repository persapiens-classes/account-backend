package org.persapiens.account.restclient;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EntryInsertUpdateDTO;

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

	public EntryDTO findById(Long id) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.findByIdUri(id))
			.retrieve()
			.body(EntryDTO.class);
	}

	public void deleteById(Long id) {
		this.restClientHelper.deleteById(id);
	}

	public EntryDTO update(Long id, EntryInsertUpdateDTO entity) {
		return this.restClientHelper.getRestClient()
			.put()
			.uri(this.restClientHelper.updateUri((id != null) ? id.toString() : ""))
			.body(entity)
			.retrieve()
			.body(EntryDTO.class);
	}

}
