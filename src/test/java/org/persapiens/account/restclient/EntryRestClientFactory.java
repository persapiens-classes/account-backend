package org.persapiens.account.restclient;

import org.persapiens.account.dto.EntryDTO;
import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestClient;

@SuperBuilder
@Data
public class EntryRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestClient restClient;

    public EntryRestClient entryRestClient() {
        return EntryRestClient.builder()
                .restClientHelper(RestClientHelper.<EntryDTO>builder()
                    .endpoint("entry")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .restClient(restClient)
                    .build())
                .build();
    }


}