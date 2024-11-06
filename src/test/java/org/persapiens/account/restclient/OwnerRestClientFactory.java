package org.persapiens.account.restclient;

import org.persapiens.account.dto.OwnerDTO;
import java.util.Optional;
import lombok.experimental.SuperBuilder;
import lombok.Data;

@SuperBuilder
@Data
public class OwnerRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    public OwnerRestClient ownerRestClient() {
        return OwnerRestClient.builder()
                .restClientHelper(RestClientHelper.<OwnerDTO>builder()
                    .endpoint("owner")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .build())
                .build();
    }

    public OwnerDTO owner(String name) {
        Optional<OwnerDTO> findByDescription = ownerRestClient().findByName(name);
        if (findByDescription.isEmpty()) {
            OwnerDTO result = OwnerDTO.builder().name(name).build();
            return ownerRestClient().save(result);
        } else {
            return findByDescription.get();
        }
    }

}