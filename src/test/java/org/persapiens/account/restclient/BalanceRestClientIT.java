package org.persapiens.account.restclient;

import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.CreditAccountConstants;
import org.persapiens.account.common.DebitAccountConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import org.persapiens.account.dto.CreditAccountDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import org.persapiens.account.dto.EntryDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BalanceRestClientIT {

    private final String protocol = "http";
    private final String servername = "localhost";

    @Value(value = "${local.server.port}")
    private int port;

    private BalanceRestClient balanceRestClient() {
        return BalanceRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .build().balanceRestClient();
    }

    private OwnerRestClientFactory ownerRestClientFactory() {
        return OwnerRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .build();
    }

    private CategoryRestClientFactory categoryRestClientFactory() {
        return CategoryRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .build();
    }

    private EquityAccountRestClientFactory equityAccountRestClientFactory() {
        return EquityAccountRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .categoryRestClientFactory(categoryRestClientFactory())
                .build();
    }    

    private CreditAccountRestClientFactory creditAccountRestClientFactory() {
        return CreditAccountRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .categoryRestClientFactory(categoryRestClientFactory())
                .build();
    }    

    private DebitAccountRestClientFactory debitAccountRestClientFactory() {
        return DebitAccountRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .categoryRestClientFactory(categoryRestClientFactory())
                .build();
    }    

    private OwnerEquityAccountInitialValueRestClient ownerEquityAccountInitialValueRestClient() {
        return OwnerEquityAccountInitialValueRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .build()
                .ownerEquityAccountInitialValueRestClient();
    }    

    private EntryRestClient entryRestClient() {
        return EntryRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .build()
                .entryRestClient();
    }    

    @Test
    public void balance500() {
        OwnerDTO uncle = ownerRestClientFactory().owner(OwnerConstants.UNCLE);
        EquityAccountDTO savings = equityAccountRestClientFactory().equityAccount(
            EquityAccountConstants.SAVINGS, CategoryConstants.BANK);

        // initial value 100
        OwnerEquityAccountInitialValueDTO initialValue = OwnerEquityAccountInitialValueDTO.builder()
                .equityAccount(savings)
                .owner(uncle)
                .value(new BigDecimal(100))
                .build();
        ownerEquityAccountInitialValueRestClient().save(initialValue);
        
        // credit 600
        CreditAccountDTO internship = creditAccountRestClientFactory().creditAccount(
            CreditAccountConstants.INTERNSHIP, CategoryConstants.SALARY);        
        EntryDTO entryCredito = EntryDTO.builder()
                .value(new BigDecimal(600))
                .date(LocalDateTime.now())
                .owner(uncle)
                .inAccount(savings)
                .outAccount(internship)
                .build();
        entryRestClient().save(entryCredito);

        // debit 200
        DebitAccountDTO gasoline = debitAccountRestClientFactory().debitAccount(
            DebitAccountConstants.GASOLINE, CategoryConstants.TRANSPORT);
        EntryDTO entryDebito = EntryDTO.builder()
                .value(new BigDecimal(200))
                .date(LocalDateTime.now())
                .owner(uncle)
                .inAccount(gasoline)
                .outAccount(savings)
                .build();
        entryRestClient().save(entryDebito);
        
        // executa a operacao a ser testada
        BigDecimal balance = balanceRestClient().balance(
            uncle.getName(), savings.getDescription());
        
        assertThat(balance).isEqualTo(new BigDecimal(500).setScale(2));
    }


}