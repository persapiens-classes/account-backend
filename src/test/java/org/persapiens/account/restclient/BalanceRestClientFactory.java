package org.persapiens.account.restclient;

import java.math.BigDecimal;

import lombok.experimental.SuperBuilder;
import lombok.Data;

@SuperBuilder
@Data
public class BalanceRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    public BalanceRestClient balanceRestClient() {
        return BalanceRestClient.builder()
                .restClientHelper(RestClientHelper.<BigDecimal>builder()
                    .endpoint("")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .build())
                .build();
    }


}