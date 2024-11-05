package org.persapiens.account.restclient;

import org.persapiens.account.AccountApplication;
import org.persapiens.account.dto.OwnerDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerRestClientIT {

    private final String protocol = "http";
    private final String servername = "localhost";

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private OwnerRestClient ownerRestClient() {
        return OwnerRestClientFactory.builder()
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restTemplate(testRestTemplate.getRestTemplate())
                .build().ownerRestClient();
    }

    @Test
    public void saveOne() {        
        String name = "Free income";
        
        OwnerDTO owner = OwnerDTO.builder().name(name).build();

        // verify save operation
        assertThat(ownerRestClient().save(owner))
        	.isNotNull();

        // verify findByName operation
        assertThat(ownerRestClient().findByName(name).get().getName())
                .isEqualTo(owner.getName());
        
        // verify findAll operation
        assertThat(ownerRestClient().findAll())
                .isNotEmpty();        
    }

    @Test
    public void deleteOne() {
        // create test environment
        String name = "Fantastic owner";
        ownerRestClient().save(OwnerDTO.builder().name(name).build());
        assertThat(ownerRestClient().findByName(name).get().getName())
        	.isEqualTo(name);
        
        // execute deleteByName operation
        ownerRestClient().deleteByName(name);
        // verify the results
        assertThat(ownerRestClient().findByName(name))
        	.isNotEmpty();
    }    
}