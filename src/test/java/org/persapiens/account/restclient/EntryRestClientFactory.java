package org.persapiens.account.restclient;

import org.persapiens.account.dto.EntryDTO;
import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestTemplate;

@SuperBuilder
@Data
public class EntryRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestTemplate restTemplate;

    public EntryRestClient entryRestClient() {
        return EntryRestClient.builder()
                .entityRestHelper(RestClientHelper.<EntryDTO>builder()
                    .endpoint("entry")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .restTemplate(restTemplate)
                    .build())
                .build();
    }


}