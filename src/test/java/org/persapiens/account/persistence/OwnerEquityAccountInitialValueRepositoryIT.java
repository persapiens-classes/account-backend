package org.persapiens.account.persistence;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationTest;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.domain.OwnerEquityAccountInitialValue;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class OwnerEquityAccountInitialValueRepositoryIT {

	@Autowired
	private OwnerFactory ownerFactory;

	@Autowired
	private EquityAccountFactory equityAccountFactory;

	@Autowired
	private OwnerEquityAccountInitialValueRepository ownerEquityAccountInitialValueRepository;

	@Autowired
	private OwnerEquityAccountInitialValueFactory ownerEquityAccountInitialValueFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.ownerEquityAccountInitialValueRepository).isNotNull();
	}

	@Test
	void findByOwnerAndEquityAccount() {
		// create test environment
		Owner father = this.ownerFactory.father();

		EquityAccount savings = this.equityAccountFactory.savings();

		OwnerEquityAccountInitialValue ownerEquityAccountInitialValue = this.ownerEquityAccountInitialValueFactory
			.ownerEquityAccountInitialValue(father, savings, new BigDecimal(100));

		// execute the operation to be tested
		// verify the results
		assertThat(this.ownerEquityAccountInitialValueRepository.findByOwnerAndEquityAccount(father, savings))
			.contains(ownerEquityAccountInitialValue);
	}

}
