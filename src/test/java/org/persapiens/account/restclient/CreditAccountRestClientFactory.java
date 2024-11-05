package org.persapiens.account.restclient;

import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.CreditAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestTemplate;

@SuperBuilder
@Data
public class CreditAccountRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestTemplate restTemplate;

    private CategoryRestClientFactory categoryRestClientFactory;

    public CreditAccountRestClient creditAccountRestClient() {
        return CreditAccountRestClient.builder()
            .entityRestHelper(RestClientHelper.<CreditAccountDTO>builder()
                .endpoint("creditAccount")
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restTemplate(restTemplate)
                .build())
            .build();
    }

    public CreditAccountDTO creditAccount(String description, String categoryDescription) {
        Optional<CreditAccountDTO> findByDescription = creditAccountRestClient().findByDescription(description);
        if (findByDescription.isEmpty()) {
            CategoryDTO category = categoryRestClientFactory.category(categoryDescription);
            CreditAccountDTO result = CreditAccountDTO.builder().description(description)
                .category(category).build();
            return creditAccountRestClient().save(result);
        } else {
            return findByDescription.get();
        }
    }

}