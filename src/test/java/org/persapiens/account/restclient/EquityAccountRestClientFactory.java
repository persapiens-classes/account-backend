package org.persapiens.account.restclient;

import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestTemplate;

@SuperBuilder
@Data
public class EquityAccountRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestTemplate restTemplate;

    private CategoryRestClientFactory categoryRestClientFactory;

    public EquityAccountRestClient equityAccountRestClient() {
        return EquityAccountRestClient.builder()
            .entityRestHelper(RestClientHelper.<EquityAccountDTO>builder()
                .endpoint("equityAccount")
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .restTemplate(restTemplate)
                .build())
            .build();
    }

    public EquityAccountDTO equityAccount(String description, String categoryDescription) {
        Optional<EquityAccountDTO> findByDescription = equityAccountRestClient().findByDescription(description);
        if (findByDescription.isEmpty()) {
            CategoryDTO category = categoryRestClientFactory.category(categoryDescription);
            EquityAccountDTO result = EquityAccountDTO.builder().description(description)
                .category(category).build();
            return equityAccountRestClient().save(result);
        } else {
            return findByDescription.get();
        }
    }

}