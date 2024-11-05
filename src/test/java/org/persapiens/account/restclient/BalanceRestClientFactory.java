package org.persapiens.account.restclient;

import java.math.BigDecimal;

import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.web.client.RestTemplate;

@SuperBuilder
@Data
public class BalanceRestClientFactory {

    private String protocol;
    
    private String servername;

    private int port;

    private RestTemplate restTemplate;

    public BalanceRestClient balanceRestClient() {
        return BalanceRestClient.builder()
                .entityRestHelper(RestClientHelper.<BigDecimal>builder()
                    .endpoint("")
                    .protocol(protocol)
                    .servername(servername)
                    .port(port)
                    .restTemplate(restTemplate)
                    .build())
                .build();
    }


}