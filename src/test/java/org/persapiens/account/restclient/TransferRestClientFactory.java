package org.persapiens.account.restclient;

import org.persapiens.account.dto.TransferDTO;
import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestTemplate;

@SuperBuilder
@Data
public class TransferRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestTemplate restTemplate;

    public TransferRestClient transferRestClient() {
        return TransferRestClient.builder()
                .entityRestHelper(RestClientHelper.<TransferDTO>builder()
                    .endpoint("")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .restTemplate(restTemplate)
                    .build())
                .build();
    }


}