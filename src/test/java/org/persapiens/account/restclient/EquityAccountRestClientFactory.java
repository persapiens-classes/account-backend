package org.persapiens.account.restclient;

import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.EquityAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;
import lombok.Data;

@SuperBuilder
@Data
public class EquityAccountRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private CategoryRestClientFactory categoryRestClientFactory;

    public EquityAccountRestClient equityAccountRestClient() {
        return EquityAccountRestClient.builder()
            .restClientHelper(RestClientHelper.<EquityAccountDTO>builder()
                .endpoint("equityAccount")
                .protocol(protocol)
                .servername(servername)
                .port(port)
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