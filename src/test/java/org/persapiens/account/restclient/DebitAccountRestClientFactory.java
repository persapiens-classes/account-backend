package org.persapiens.account.restclient;

import org.persapiens.account.dto.CategoryDTO;
import org.persapiens.account.dto.DebitAccountDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;
import lombok.Data;

@SuperBuilder
@Data
public class DebitAccountRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private CategoryRestClientFactory categoryRestClientFactory;

    public DebitAccountRestClient debitAccountRestClient() {
        return DebitAccountRestClient.builder()
            .restClientHelper(RestClientHelper.<DebitAccountDTO>builder()
                .endpoint("debitAccount")
                .protocol(protocol)
                .servername(servername)
                .port(port)
                .build())
            .build();
    }

    public DebitAccountDTO debitAccount(String description, String categoryDescription) {
        Optional<DebitAccountDTO> findByDescription = debitAccountRestClient().findByDescription(description);
        if (findByDescription.isEmpty()) {
            CategoryDTO category = categoryRestClientFactory.category(categoryDescription);
            DebitAccountDTO result = DebitAccountDTO.builder().description(description)
                .category(category).build();
            return debitAccountRestClient().save(result);
        } else {
            return findByDescription.get();
        }
    }

}