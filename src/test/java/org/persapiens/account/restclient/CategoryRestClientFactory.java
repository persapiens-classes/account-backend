package org.persapiens.account.restclient;

import org.persapiens.account.dto.CategoryDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;
import lombok.Data;

@SuperBuilder
@Data
public class CategoryRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    public CategoryRestClient categoryRestClient() {
        return CategoryRestClient.builder()
                .restClientHelper(RestClientHelper.<CategoryDTO>builder()
                    .endpoint("category")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .build())
                .build();
    }

    public CategoryDTO category(String description) {
        Optional<CategoryDTO> findByDescription = categoryRestClient().findByDescription(description);
        if (findByDescription.isEmpty()) {
            CategoryDTO result = CategoryDTO.builder().description(description).build();
            return categoryRestClient().save(result);
        } else {
            return findByDescription.get();
        }
    }

}