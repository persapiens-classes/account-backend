package org.persapiens.account.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OwnerServiceIT {

	@Autowired
	private OwnerService ownerService;

	@Autowired
	private OwnerFactory ownerFactory;

	@Test
	void repositoryNotNull() {
		assertThat(this.ownerService).isNotNull();
	}

	@Test
	void saveOne() {
		// create test environment
		Owner owner = this.ownerFactory.mother();

		// verify the results
		assertThat(this.ownerService.findById(owner.getId()).get()).isEqualTo(owner);
	}

	@Test
	void deleteOne() {
		// create test environment
		Owner owner = this.ownerFactory.owner("UNIQUE Owner");

		// execute the operation to be tested
		this.ownerService.delete(owner);

		// verify the results
		assertThat(this.ownerService.findById(owner.getId()).isPresent()).isFalse();
	}

}
