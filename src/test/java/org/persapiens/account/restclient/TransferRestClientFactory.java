package org.persapiens.account.restclient;

import org.persapiens.account.dto.TransferDTO;
import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestClient;

@SuperBuilder
@Data
public class TransferRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestClient restClient;

    public TransferRestClient transferRestClient() {
        return TransferRestClient.builder()
                .restClientHelper(RestClientHelper.<TransferDTO>builder()
                    .endpoint("")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .restClient(restClient)
                    .build())
                .build();
    }


}