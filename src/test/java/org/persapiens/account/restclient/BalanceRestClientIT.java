package org.persapiens.account.restclient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.common.DebitAccountConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BalanceRestClientIT extends RestClientIT {

	private BalanceRestClient balanceRestClient() {
		return BalanceRestClient.builder().restClientHelper(this.<BigDecimal>restClientHelper("")).build();
	}

	@Test
	public void balance500() {
		OwnerDTO uncle = owner(OwnerConstants.UNCLE);
		EquityAccountDTO savings = equityAccount(EquityAccountConstants.SAVINGS,
				CategoryConstants.BANK);

		// initial value 100
		OwnerEquityAccountInitialValueDTO initialValue = OwnerEquityAccountInitialValueDTO.builder()
			.equityAccount(savings)
			.owner(uncle)
			.value(new BigDecimal(100))
			.build();
		ownerEquityAccountInitialValueRestClient().save(initialValue);

		// credit 600
		CreditAccountDTO internship = creditAccount(CreditAccountConstants.INTERNSHIP,
				CategoryConstants.SALARY);
		EntryDTO entryCredito = EntryDTO.builder()
			.value(new BigDecimal(600))
			.date(LocalDateTime.now())
			.owner(uncle)
			.inAccount(savings)
			.outAccount(internship)
			.build();
		entryRestClient().save(entryCredito);

		// debit 200
		DebitAccountDTO gasoline = debitAccount(DebitAccountConstants.GASOLINE,
				CategoryConstants.TRANSPORT);
		EntryDTO entryDebito = EntryDTO.builder()
			.value(new BigDecimal(200))
			.date(LocalDateTime.now())
			.owner(uncle)
			.inAccount(gasoline)
			.outAccount(savings)
			.build();
		entryRestClient().save(entryDebito);

		// executa a operacao a ser testada
		BigDecimal balance = balanceRestClient().balance(uncle.getName(), savings.getDescription());

		assertThat(balance).isEqualTo(new BigDecimal(500).setScale(2));
	}

}
