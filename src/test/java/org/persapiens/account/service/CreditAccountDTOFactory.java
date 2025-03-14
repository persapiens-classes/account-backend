package org.persapiens.account.service;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.persistence.CreditAccountFactory;
import org.persapiens.account.persistence.CreditCategoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreditAccountDTOFactory {

	@Autowired
	private CreditAccountFactory creditAccountFactory;

	@Autowired
	private CreditCategoryFactory categoryFactory;

	public AccountDTO creditAccountDTO(CreditAccount creditAccount) {
		return new AccountDTO(creditAccount.getDescription(), creditAccount.getCategory().getDescription());
	}

	public AccountDTO creditAccountDTO(String description, String category) {
		return creditAccountDTO(
				this.creditAccountFactory.creditAccount(description, this.categoryFactory.category(category)));
	}

	public AccountDTO internship() {
		return creditAccountDTO(this.creditAccountFactory.internship());
	}

}
