package org.persapiens.account.persistence;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.domain.Owner;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OwnerFactory {

	private OwnerRepository ownerRepository;

	public Owner owner(String name) {
		Optional<Owner> findByName = this.ownerRepository.findByName(name);
		if (findByName.isEmpty()) {
			Owner category = Owner.builder().name(name).build();
			return this.ownerRepository.save(category);
		}
		else {
			return findByName.get();
		}
	}

	public Owner father() {
		return owner(OwnerConstants.FATHER);
	}

	public Owner mother() {
		return owner(OwnerConstants.MOTHER);
	}

	public Owner aunt() {
		return owner(OwnerConstants.AUNT);
	}

}
