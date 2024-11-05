package org.persapiens.account.restclient;

import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.EquityAccountDTO;
import org.persapiens.account.dto.OwnerDTO;
import org.persapiens.account.dto.TransferDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferRestClientIT {

    private final String protocol = "http";
    private final String servername = "localhost";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private TransferRestClient transferRestClient() {
        return TransferRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restTemplate(testRestTemplate.getRestTemplate())
                .build().transferRestClient();
    }

    private OwnerRestClientFactory ownerRestClientFactory() {
        return OwnerRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restTemplate(testRestTemplate.getRestTemplate())
                .build();
    }

    private CategoryRestClientFactory categoryRestClientFactory() {
        return CategoryRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restTemplate(testRestTemplate.getRestTemplate())
                .build();
    }

    private EquityAccountRestClientFactory equityAccountRestClientFactory() {
        return EquityAccountRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restTemplate(testRestTemplate.getRestTemplate())
                .categoryRestClientFactory(categoryRestClientFactory())
                .build();
    }    

    private EntryRestClient entryRestClient() {
        return EntryRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restTemplate(testRestTemplate.getRestTemplate())
                .build()
                .entryRestClient();
    }    

    @Test
    public void transfer50FromCheckingsAuntToInvestimentUncle() {
        OwnerDTO aunt = ownerRestClientFactory().owner(OwnerConstants.AUNT);
        OwnerDTO uncle = ownerRestClientFactory().owner(OwnerConstants.UNCLE);

        EquityAccountDTO checkings = equityAccountRestClientFactory().equityAccount(
            EquityAccountConstants.CHECKING, CategoryConstants.BANK);
        EquityAccountDTO investiment = equityAccountRestClientFactory().equityAccount(
            EquityAccountConstants.INVESTIMENT, CategoryConstants.BANK);
        
        // execute transfer operation
        transferRestClient().transfer(TransferDTO.builder()
            .debitOwner(aunt.getName())
            .creditOwner(uncle.getName())
            .debitAccount(checkings.getDescription())
            .creditAccount(investiment.getDescription())
            .value(new BigDecimal(50))
            .build());
        
        assertThat(entryRestClient().debitSum(aunt.getName(), checkings.getDescription()))
            .isEqualTo(new BigDecimal(50).setScale(2));
        
        assertThat(entryRestClient().creditSum(uncle.getName(), investiment.getDescription()))
            .isEqualTo(new BigDecimal(50).setScale(2));
    }

}