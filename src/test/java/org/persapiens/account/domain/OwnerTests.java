package org.persapiens.account.domain;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.persapiens.account.common.OwnerConstants;

import static org.assertj.core.api.Assertions.assertThat;

class OwnerTests {

	@Test
	void equalNames() {
		assertThat(Owner.builder().name(OwnerConstants.FATHER).build())
			.isEqualTo(Owner.builder().name(OwnerConstants.FATHER).build());
	}

	@Test
	void equalDescription() {
		Owner father1 = Owner.builder().name(OwnerConstants.FATHER).build();

		Owner father2 = Owner.builder().name(OwnerConstants.FATHER).build();

		assertThat(father1).isEqualTo(father2);
	}

	@Test
	void differentNames() {
		assertThat(Owner.builder().name(OwnerConstants.FATHER).build())
			.isNotEqualTo(Owner.builder().name(OwnerConstants.MOTHER).build());
	}

	@Test
	void compareTo() {
		Set<Owner> owners = new TreeSet<>();

		Owner mother = Owner.builder().name(OwnerConstants.MOTHER).build();
		owners.add(mother);
		Owner father = Owner.builder().name(OwnerConstants.FATHER).build();
		owners.add(father);

		assertThat(owners.iterator().next()).isEqualTo(father);
	}

}
