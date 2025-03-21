package org.persapiens.account.service;

import lombok.AllArgsConstructor;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OwnerDTOFactory {

	private OwnerFactory ownerFactory;

	public OwnerDTO ownerDTO(Owner owner) {
		return new OwnerDTO(owner.getName());
	}

	public OwnerDTO ownerDTO(String name) {
		return ownerDTO(this.ownerFactory.owner(name));
	}

	public OwnerDTO father() {
		return ownerDTO(OwnerConstants.FATHER);
	}

	public OwnerDTO mother() {
		return ownerDTO(OwnerConstants.MOTHER);
	}

	public OwnerDTO aunt() {
		return ownerDTO(OwnerConstants.AUNT);
	}

}
