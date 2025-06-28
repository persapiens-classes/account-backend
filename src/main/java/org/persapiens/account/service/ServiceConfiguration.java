package org.persapiens.account.service;

import org.persapiens.account.domain.CreditAccount;
import org.persapiens.account.domain.CreditCategory;
import org.persapiens.account.domain.CreditEntry;
import org.persapiens.account.domain.DebitAccount;
import org.persapiens.account.domain.DebitCategory;
import org.persapiens.account.domain.DebitEntry;
import org.persapiens.account.domain.EquityAccount;
import org.persapiens.account.domain.EquityCategory;
import org.persapiens.account.domain.TransferEntry;
import org.persapiens.account.persistence.CreditAccountRepository;
import org.persapiens.account.persistence.CreditCategoryRepository;
import org.persapiens.account.persistence.CreditEntryRepository;
import org.persapiens.account.persistence.DebitAccountRepository;
import org.persapiens.account.persistence.DebitCategoryRepository;
import org.persapiens.account.persistence.DebitEntryRepository;
import org.persapiens.account.persistence.EquityAccountRepository;
import org.persapiens.account.persistence.EquityCategoryRepository;
import org.persapiens.account.persistence.TransferEntryRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

	@Bean
	public CategoryService<CreditCategory> creditCategoryService(CreditCategoryRepository creditCategoryRepository) {
		return new CategoryService<>(creditCategoryRepository, CreditCategory::new);
	}

	@Bean
	public CategoryService<DebitCategory> debitCategoryService(DebitCategoryRepository debitCategoryRepository) {
		return new CategoryService<>(debitCategoryRepository, DebitCategory::new);
	}

	@Bean
	public CategoryService<EquityCategory> equityCategoryService(EquityCategoryRepository equityCategoryRepository) {
		return new CategoryService<>(equityCategoryRepository, EquityCategory::new);
	}

	@Bean
	public AccountService<CreditAccount, CreditCategory> creditAccountService(CreditAccountRepository creditAccountRepository,
			CategoryService<CreditCategory> creditCategoryService) {
		return new AccountService<>(creditAccountRepository, creditCategoryService, CreditAccount::new);
	}

	@Bean
	public AccountService<DebitAccount, DebitCategory> debitAccountService(DebitAccountRepository debitAccountRepository,
			CategoryService<DebitCategory> debitCategoryService) {
		return new AccountService<>(debitAccountRepository, debitCategoryService, DebitAccount::new);
	}

	@Bean
	public AccountService<EquityAccount, EquityCategory> equityAccountService(EquityAccountRepository equityAccountRepository,
			CategoryService<EquityCategory> equityCategoryService) {
		return new AccountService<>(equityAccountRepository, equityCategoryService, EquityAccount::new);
	}

	@Bean
	public EntryService<CreditEntry, EquityAccount, EquityCategory, CreditAccount, CreditCategory> creditEntryService(CreditEntryRepository creditEntryRepository,
			AccountService<EquityAccount, EquityCategory> equityAccountService, AccountService<CreditAccount, CreditCategory> creditAccountService, OwnerService ownerService) {
		return new EntryService<>(creditEntryRepository, equityAccountService, creditAccountService, ownerService, CreditEntry::new);
	}

	@Bean
	public EntryService<DebitEntry, DebitAccount, DebitCategory, EquityAccount, EquityCategory> debitEntryService(DebitEntryRepository debitEntryRepository,
			AccountService<DebitAccount, DebitCategory> debitAccountService, AccountService<EquityAccount, EquityCategory> equityAccountService, OwnerService ownerService) {
		return new EntryService<>(debitEntryRepository, debitAccountService, equityAccountService, ownerService, DebitEntry::new);
	}

	@Bean
	public EntryService<TransferEntry, EquityAccount, EquityCategory, EquityAccount, EquityCategory> transferEntryService(TransferEntryRepository transferEntryRepository,
			AccountService<EquityAccount, EquityCategory> equityAccountService, OwnerService ownerService) {
		return new EntryService<>(transferEntryRepository, equityAccountService, equityAccountService, ownerService, TransferEntry::new);
	}

}
