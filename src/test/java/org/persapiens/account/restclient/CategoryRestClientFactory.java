package org.persapiens.account.restclient;

import org.persapiens.account.dto.CategoryDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestTemplate;

@SuperBuilder
@Data
public class CategoryRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestTemplate restTemplate;

    public CategoryRestClient categoryRestClient() {
        return CategoryRestClient.builder()
                .entityRestHelper(RestClientHelper.<CategoryDTO>builder()
                    .endpoint("category")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .restTemplate(restTemplate)
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