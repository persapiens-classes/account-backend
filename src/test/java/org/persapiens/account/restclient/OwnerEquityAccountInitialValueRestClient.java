package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.experimental.SuperBuilder;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueInsertDTO;

@SuperBuilder
public class OwnerEquityAccountInitialValueRestClient {

	private RestClientHelper<OwnerEquityAccountInitialValueDTO> restClientHelper;

	public Iterable<OwnerEquityAccountInitialValueDTO> findAll() {
		return this.restClientHelper.findAll();
	}

	public OwnerEquityAccountInitialValueDTO insert(OwnerEquityAccountInitialValueInsertDTO entity) {
		return this.restClientHelper.getRestClient()
			.post()
			.uri(this.restClientHelper.insertUri())
			.body(entity)
			.retrieve()
			.body(OwnerEquityAccountInitialValueDTO.class);
	}

	private Map<String, Object> uriVariables(String owner, String equityAccount) {
		Map<String, Object> result = new HashMap<>();
		result.put("owner", owner);
		result.put("equityAccount", equityAccount);
		return result;
	}

	public OwnerEquityAccountInitialValueDTO findByOwnerAndEquityAccount(String owner, String equityAccount) {
		return this.restClientHelper.getRestClient()
			.get()
			.uri(this.restClientHelper.findByUri("/filter?owner={owner}&equityAccount={equityAccount}",
					uriVariables(owner, equityAccount)))
			.retrieve()
			.body(OwnerEquityAccountInitialValueDTO.class);
	}

	public void deleteByOwnerAndEquityAccount(String owner, String equityAccount) {
		this.restClientHelper.delete("?owner={owner}&equityAccount={equityAccount}",
				uriVariables(owner, equityAccount));
	}

	public OwnerEquityAccountInitialValueDTO update(String owner, String equityAccount, BigDecimal value) {
		return this.restClientHelper.getRestClient()
			.put()
			.uri(this.restClientHelper.updateUri("?owner={owner}&equityAccount={equityAccount}",
					uriVariables(owner, equityAccount)))
			.body(value)
			.retrieve()
			.body(OwnerEquityAccountInitialValueDTO.class);
	}

}
