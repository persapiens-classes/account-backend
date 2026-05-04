package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.IntegrationTest;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.OwnerRepository;

import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class OwnerServiceIT {

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private OwnerDTOFactory ownerDTOFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.ownerService).isNotNull();
	}

	@Test
	void saveOne() {
		// create test environment
		OwnerDTO owner = this.ownerDTOFactory.mother();

		// verify the results
		assertThat(this.ownerService.findByName(owner.name())).isEqualTo(owner);
	}

	@Test
	void deleteOne() {
		// create test environment
		OwnerDTO owner = this.ownerDTOFactory.ownerDTO("UNIQUE Owner");

		// execute the operation to be tested
		this.ownerService.deleteByName(owner.name());

		// verify the results
		assertThat(this.ownerRepository.findByName(owner.name())).isNotPresent();
	}

}
