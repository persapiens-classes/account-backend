package org.persapiens.account.service;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.persistence.CategoryFactory;
import org.persapiens.account.persistence.CreditAccountFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditAccountDTOFactory {

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Autowired
	private CategoryFactory categoryFactory;

	public CreditAccountDTO creditAccountDTO(CreditAccount creditAccount) {
		return new CreditAccountDTO(creditAccount.getDescription(), creditAccount.getCategory().getDescription());
	}

	public CreditAccountDTO creditAccountDTO(String description, String category) {
		return creditAccountDTO(
				this.creditAccountFactory.creditAccount(description, this.categoryFactory.category(category)));
	}

	public CreditAccountDTO internship() {
		return creditAccountDTO(this.creditAccountFactory.internship());
	}

}
