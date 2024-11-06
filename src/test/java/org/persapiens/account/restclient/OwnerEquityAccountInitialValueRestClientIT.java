package org.persapiens.account.restclient;

import org.persapiens.account.AccountApplication;
import org.persapiens.account.common.CategoryConstants;
import org.persapiens.account.common.EquityAccountConstants;
import org.persapiens.account.common.OwnerConstants;
import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerEquityAccountInitialValueRestClientIT {

    private final String protocol = "http";
    private final String servername = "localhost";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private OwnerEquityAccountInitialValueRestClient ownerEquityAccountInitialValueRestClient() {
        return OwnerEquityAccountInitialValueRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restClient(RestClient.create(testRestTemplate.getRestTemplate()))
                .build().ownerEquityAccountInitialValueRestClient();
    }

    private OwnerRestClientFactory ownerRestClientFactory() {
        return OwnerRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restClient(RestClient.create(testRestTemplate.getRestTemplate()))
                .build();
    }

    private CategoryRestClientFactory categoryRestClientFactory() {
        return CategoryRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restClient(RestClient.create(testRestTemplate.getRestTemplate()))
                .build();
    }

    private EquityAccountRestClientFactory equityAccountRestClientFactory() {
        return EquityAccountRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restClient(RestClient.create(testRestTemplate.getRestTemplate()))
                .categoryRestClientFactory(categoryRestClientFactory())
                .build();
    }    

    @Test
    public void saveOne() {        
        String mother = OwnerConstants.MOTHER;
        String savings = EquityAccountConstants.SAVINGS;
        String bank = CategoryConstants.BANK;
        
        OwnerEquityAccountInitialValueDTO ownerEquityAccountInitialValue = OwnerEquityAccountInitialValueDTO.builder()
            .value(new BigDecimal(1000))
            .owner(ownerRestClientFactory().owner(mother))
            .equityAccount(equityAccountRestClientFactory().equityAccount(savings, bank))
            .build();

        // verify save operation
        assertThat(ownerEquityAccountInitialValueRestClient().save(ownerEquityAccountInitialValue))
        	.isNotNull();
        
        // verify findAll operation
        assertThat(ownerEquityAccountInitialValueRestClient().findAll())
                .isNotEmpty();        
    }

}