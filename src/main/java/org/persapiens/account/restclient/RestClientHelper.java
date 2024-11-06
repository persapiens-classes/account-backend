package org.persapiens.account.restclient;

import java.net.URI;

import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;

import lombok.experimental.SuperBuilder;
import lombok.Data;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@SuperBuilder
public class RestClientHelper <T> {

    private String endpoint;
    private String protocol;
    private String servername;
    private int port;
    private RestClient restClient;

    public String url() {
        return protocol + "://" + servername + ":" + port + "/" + endpoint;
    }

    private URI uri() {
        return UriComponentsBuilder.fromHttpUrl(url())
            .build().encode().toUri();
    }
    
    public URI uri(String suffix, String param, String value) {
        return UriComponentsBuilder.fromHttpUrl(url() + suffix)
            .queryParam(param, value)
            .build().encode().toUri();
    }
    
    public void delete(String suffix, String param, String value) {
        this.restClient
            .delete()
            .uri(uri(suffix, param, value))
            .retrieve()
            .toBodilessEntity();
    }
    
    public void deleteByDescription(String description) {
        delete("/deleteByDescription", "description", description);
    }

    public Iterable<T> findAll() {
        return this.restClient
            .get()
            .uri(findAllUri())
            .retrieve()
            .body(new ParameterizedTypeReference<Iterable<T>>(){});
    }
    
    public URI findByDescriptionUri(String description) {
        return uri("/findByDescription", "description", description);
    }

    public URI findAllUri() {
        return uri();
    }

    public URI saveUri() {
        return uri();
    }

}