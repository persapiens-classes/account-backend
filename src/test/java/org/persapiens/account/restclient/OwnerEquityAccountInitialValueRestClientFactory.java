package org.persapiens.account.restclient;

import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestClient;

@SuperBuilder
@Data
public class OwnerEquityAccountInitialValueRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestClient restClient;

    public OwnerEquityAccountInitialValueRestClient ownerEquityAccountInitialValueRestClient() {
        return OwnerEquityAccountInitialValueRestClient.builder()
                .restClientHelper(RestClientHelper.<OwnerEquityAccountInitialValueDTO>builder()
                    .endpoint("ownerEquityAccountInitialValue")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .restClient(restClient)
                    .build())
                .build();
    }

}