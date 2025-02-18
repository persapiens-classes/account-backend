package org.persapiens.account.service;

import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.persistence.CategoryFactory;
import org.persapiens.account.persistence.DebitAccountFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DebitAccountDTOFactory {

	@Autowired
	private DebitAccountFactory debitAccountFactory;

	@Autowired
	private CategoryFactory categoryFactory;

	public DebitAccountDTO debitAccountDTO(DebitAccount debitAccount) {
		return new DebitAccountDTO(debitAccount.getDescription(), debitAccount.getCategory().getDescription());
	}

	public DebitAccountDTO debitAccountDTO(String description, String category) {
		return debitAccountDTO(
				this.debitAccountFactory.debitAccount(description, this.categoryFactory.category(category)));
	}

	public DebitAccountDTO gasoline() {
		return debitAccountDTO(this.debitAccountFactory.gasoline());
	}

}
