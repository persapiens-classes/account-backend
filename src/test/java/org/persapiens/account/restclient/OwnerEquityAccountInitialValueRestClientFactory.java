package org.persapiens.account.restclient;

import org.persapiens.account.dto.OwnerEquityAccountInitialValueDTO;
import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestTemplate;

@SuperBuilder
@Data
public class OwnerEquityAccountInitialValueRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestTemplate restTemplate;

    public OwnerEquityAccountInitialValueRestClient ownerEquityAccountInitialValueRestClient() {
        return OwnerEquityAccountInitialValueRestClient.builder()
                .entityRestHelper(RestClientHelper.<OwnerEquityAccountInitialValueDTO>builder()
                    .endpoint("ownerEquityAccountInitialValue")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .restTemplate(restTemplate)
                    .build())
                .build();
    }

}