package org.persapiens.account.persistence;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.domain.Owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OwnerRepositoryIT {

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private OwnerFactory ownerFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.ownerRepository).isNotNull();
	}

	@Test
	void findByName() {
		// create test environment
		Owner father = this.ownerFactory.father();
		Owner mother = this.ownerFactory.mother();

		// execute the operation to be tested
		// verify results
		assertThat(this.ownerRepository.findByName(OwnerConstants.FATHER)).contains(father);
		assertThat(this.ownerRepository.findByName(OwnerConstants.MOTHER)).contains(mother);
	}

	@Test
	void deleteByName() {
		String uniqueName = "Unique Owner";

		// create test environment
		this.ownerFactory.owner(uniqueName);

		// execute the operation to be tested
		this.ownerRepository.deleteByName(uniqueName);

		// verify results
		assertThat(this.ownerRepository.findByName(uniqueName)).isNotPresent();
	}

}
