package org.persapiens.account.service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.TransferEntry;
import org.persapiens.account.dto.AccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.persistence.EquityAccountFactory;
import org.persapiens.account.persistence.EquityCategoryFactory;
import org.persapiens.account.persistence.OwnerFactory;
import org.persapiens.account.persistence.TransferEntryFactory;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TransferEntryDTOFactory {

	private EntryService<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> transferEntryService;

	private TransferEntryFactory transferEntryFactory;

	private OwnerFactory ownerFactory;

	private EquityCategoryFactory equityCategoryFactory;

	private EquityAccountFactory equityAccountFactory;

	public EntryDTO transferEntryDTO(TransferEntry entry) {
		return this.transferEntryService.toDTO(entry);
	}

	public EntryDTO transferEntryDTO(OwnerDTO ownerIn, OwnerDTO ownerOut, AccountDTO inAccount, AccountDTO outAccount, BigDecimal value) {
		return transferEntryDTO(this.transferEntryFactory.entry(this.ownerFactory.owner(ownerIn.name()),
			this.ownerFactory.owner(ownerOut.name()),
			this.equityAccountFactory.equityAccount(inAccount.description(),
					this.equityCategoryFactory.category(inAccount.category())),
			this.equityAccountFactory.equityAccount(outAccount.description(),
					this.equityCategoryFactory.category(outAccount.category())),
			value));
	}

}
