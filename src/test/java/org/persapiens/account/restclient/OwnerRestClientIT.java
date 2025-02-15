package org.persapiens.account.restclient;

import org.junit.jupiter.api.Test;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.OwnerDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnerRestClientIT extends RestClientIT {

	@Test
	void insertOne() {
		String name = "Free income";

		OwnerDTO owner = OwnerDTO.builder().name(name).build();

		// verify insert operation
		assertThat(ownerRestClient().insert(owner)).isNotNull();

		// verify findByName operation
		assertThat(ownerRestClient().findByName(name).getName()).isEqualTo(owner.getName());

		// verify findAll operation
		assertThat(ownerRestClient().findAll()).isNotEmpty();
	}

	private void insertInvalid(String name, HttpStatus httpStatus) {
		OwnerDTO ownerDto = OwnerDTO.builder().name(name).build();

		// verify insert operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> ownerRestClient().insert(ownerDto))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void insertEmpty() {
		// verify insert operation
		// verify status code error
		insertInvalid("", HttpStatus.BAD_REQUEST);
	}

	@Test
	void insertSameOwnerTwice() {
		String name = "repeated owner";

		OwnerDTO ownerDto = OwnerDTO.builder().name(name).build();

		ownerRestClient().insert(ownerDto);

		// verify insert operation
		// verify status code error
		insertInvalid(name, HttpStatus.CONFLICT);
	}

	@Test
	void updateOne() {
		OwnerDTO owner = owner("Inserted owner");

		String originalName = owner.getName();
		owner.setName("Updated owner");

		ownerRestClient().update(originalName, owner);

		// verify update operation
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> ownerRestClient().findByName(originalName))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));

		// verify update operation
		assertThat(ownerRestClient().findByName(owner.getName()).getName()).isEqualTo(owner.getName());
	}

	private void updateInvalid(String oldName, String newName, HttpStatus httpStatus) {
		OwnerDTO category = OwnerDTO.builder().name(newName).build();

		// verify update operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class)
			.isThrownBy(() -> ownerRestClient().update(oldName, category))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void updateInvalid() {
		OwnerDTO ownerToUpdate = owner("owner to update");

		// empty id
		updateInvalid("", "", HttpStatus.FORBIDDEN);
		updateInvalid("", ownerToUpdate.getName(), HttpStatus.FORBIDDEN);

		// empty fields
		updateInvalid(ownerToUpdate.getName(), "", HttpStatus.BAD_REQUEST);

		// invalid id
		updateInvalid("invalid owner", "valid owner name", HttpStatus.NOT_FOUND);

		// invalid field
		updateInvalid(ownerToUpdate.getName(), ownerToUpdate.getName(), HttpStatus.CONFLICT);
	}

	@Test
	void deleteOne() {
		// create test environment
		String name = "Fantastic owner";
		ownerRestClient().insert(OwnerDTO.builder().name(name).build());
		assertThat(ownerRestClient().findByName(name).getName()).isEqualTo(name);

		// execute deleteByName operation
		ownerRestClient().deleteByName(name);
		// verify the results
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> ownerRestClient().findByName(name))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
	}

	private void deleteInvalid(String name, HttpStatus httpStatus) {
		// verify delete operation
		// verify status code error
		assertThatExceptionOfType(HttpClientErrorException.class).isThrownBy(() -> ownerRestClient().deleteByName(name))
			.satisfies((ex) -> assertThat(ex.getStatusCode()).isEqualTo(httpStatus));
	}

	@Test
	void deleteInvalid() {
		String name = "Invalid name";
		deleteInvalid("", HttpStatus.FORBIDDEN);
		deleteInvalid(name, HttpStatus.NOT_FOUND);
	}

}
