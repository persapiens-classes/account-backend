package org.persapiens.account.restclient;

import org.persapiens.account.dto.TransferDTO;
import lombok.experimental.SuperBuilder;
import lombok.Data;

@SuperBuilder
@Data
public class TransferRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    public TransferRestClient transferRestClient() {
        return TransferRestClient.builder()
                .restClientHelper(RestClientHelper.<TransferDTO>builder()
                    .endpoint("")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .build())
                .build();
    }


}