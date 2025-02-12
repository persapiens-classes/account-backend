package org.persapiens.account.service;

import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.domain.Owner;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.OwnerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnerDTOFactory {

	@Autowired
	private OwnerFactory ownerFactory;

	public OwnerDTO ownerDTO(Owner owner) {
		return OwnerDTO.builder().name(owner.getName()).build();
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
