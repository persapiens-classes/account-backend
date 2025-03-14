package org.persapiens.account.service;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.persistence.DebitAccountFactory;
import org.persapiens.account.persistence.DebitCategoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebitAccountDTOFactory {

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Autowired
	private DebitCategoryFactory categoryFactory;

	public AccountDTO debitAccountDTO(DebitAccount debitAccount) {
		return new AccountDTO(debitAccount.getDescription(), debitAccount.getCategory().getDescription());
	}

	public AccountDTO debitAccountDTO(String description, String category) {
		return debitAccountDTO(
				this.debitAccountFactory.debitAccount(description, this.categoryFactory.category(category)));
	}

	public AccountDTO gasoline() {
		return debitAccountDTO(this.debitAccountFactory.gasoline());
	}

}
