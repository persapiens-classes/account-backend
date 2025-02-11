package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.OwnerDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OwnerServiceIT {

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
		assertThat(this.ownerService.findByName(owner.getName()).get()).isEqualTo(owner);
	}

	@Test
	void deleteOne() {
		// create test environment
		OwnerDTO owner = this.ownerDTOFactory.ownerDTO("UNIQUE Owner");

		// execute the operation to be tested
		this.ownerService.deleteByName(owner.getName());

		// verify the results
		assertThat(this.ownerService.findByName(owner.getName()).isPresent()).isFalse();
	}

}
