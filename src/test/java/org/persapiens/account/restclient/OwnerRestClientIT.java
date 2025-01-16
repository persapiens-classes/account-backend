package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.OwnerDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnerRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String name = "Free income";

		OwnerDTO owner = OwnerDTO.builder().name(name).build();

		// verify insert operation
		assertThat(ownerRestClient().insert(owner)).isNotNull();

		// verify findByName operation
		assertThat(ownerRestClient().findByName(name).get().getName()).isEqualTo(owner.getName());

		// verify findAll operation
		assertThat(ownerRestClient().findAll()).isNotEmpty();
	}

	@Test
	void updateOne() {
		OwnerDTO owner = owner("Inserted owner");

		String originalName = owner.getName();
		owner.setName("Updated owner");

		ownerRestClient().update(originalName, owner);

		// verify update operation
		assertThat(ownerRestClient().findByName(originalName)).isEmpty();

		// verify update operation
		assertThat(ownerRestClient().findByName(owner.getName()).get().getName()).isEqualTo(owner.getName());
	}

	@Test
	void deleteOne() {
		// create test environment
		String name = "Fantastic owner";
		ownerRestClient().insert(OwnerDTO.builder().name(name).build());
		assertThat(ownerRestClient().findByName(name).get().getName()).isEqualTo(name);

		// execute deleteByName operation
		ownerRestClient().deleteByName(name);
		// verify the results
		assertThat(ownerRestClient().findByName(name)).isEmpty();
	}

}
