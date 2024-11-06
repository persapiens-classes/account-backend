package org.persapiens.account.persistence;

import java.util.Optional;

import static org.persapiens.account.common.OwnerConstants.FATHER;
import static org.persapiens.account.common.OwnerConstants.MOTHER;
import org.persapiens.account.domain.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerFactory {

	@Autowired
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
		return owner(FATHER);
	}

	public Owner mother() {
		return owner(MOTHER);
	}

}
