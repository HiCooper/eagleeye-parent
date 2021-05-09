package com.berry.eagleeye.bridge.security.oauth2;

import com.alibaba.fastjson.JSONObject;
import com.berry.eagleeye.bridge.config.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @since 2021/3/11
 * fileName：UaaSignatureVerifierClient
 * Use：Uaa 授权密钥验证 client
 */
@Component
public class UaaSignatureVerifierClient {
    private final Logger log = LoggerFactory.getLogger(UaaSignatureVerifierClient.class);
    private final RestTemplate restTemplate;
    protected final OAuth2Properties oAuth2Properties;

    public UaaSignatureVerifierClient(DiscoveryClient discoveryClient, @Qualifier("loadBalancedRestTemplate") RestTemplate restTemplate,
                                      OAuth2Properties oAuth2Properties) {
        this.restTemplate = restTemplate;
        this.oAuth2Properties = oAuth2Properties;
        // Load available UAA servers
        discoveryClient.getServices();
    }

    /**
     * Fetches the public key from the UAA.
     *
     * @return the public key used to verify JWT tokens or {@code null}.
     */
    public SignatureVerifier getSignatureVerifier() {
        try {
            HttpEntity<Void> request = new HttpEntity<>(new HttpHeaders());
            ResponseEntity<JSONObject> exchange = restTemplate.exchange(getPublicKeyEndpoint(), HttpMethod.GET, request, JSONObject.class);
            JSONObject body = exchange.getBody();
            if (body != null) {
                String key = body.getString("value");
                return new RsaVerifier(key);
            }
        } catch (IllegalStateException ex) {
            log.warn("could not contact UAA to get public key");
        }
        return null;
    }

    /**
     * Returns the configured endpoint URI to retrieve the public key.
     *
     * @return the configured endpoint URI to retrieve the public key.
     */
    private String getPublicKeyEndpoint() {
        String tokenEndpointUrl = oAuth2Properties.getSignatureVerification().getPublicKeyEndpointUri();
        if (tokenEndpointUrl == null) {
            throw new InvalidClientException("no public key uri endpoint configured in application properties");
        }
        return tokenEndpointUrl;
    }

    /**
     * Fetch access_token
     *
     * @return access_token or  null
     */
    public String requestAccessToken() {
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", oAuth2Properties.getWebClientConfiguration().getGrantType());
            params.add("scope", oAuth2Properties.getWebClientConfiguration().getScope());
            params.add("client_id", oAuth2Properties.getWebClientConfiguration().getClientId());
            params.add("client_secret", oAuth2Properties.getWebClientConfiguration().getClientSecret());
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, new HttpHeaders());
            ResponseEntity<JSONObject> exchange = restTemplate.postForEntity(getAccessTokenEndpoint(), request, JSONObject.class);
            JSONObject body = exchange.getBody();
            if (body != null) {
                return body.getString("access_token");
            }
        } catch (IllegalStateException ex) {
            log.warn("could not contact UAA to get public key");
        } catch (Exception e) {
            log.error("request fail: {}", e.getMessage());
        }
        return null;
    }

    private String getAccessTokenEndpoint() {
        String accessTokenEndpointUri = oAuth2Properties.getWebClientConfiguration().getAccessTokenEndpointUri();
        if (accessTokenEndpointUri == null) {
            throw new InvalidClientException("no access token uri endpoint configured in application properties");
        }
        return accessTokenEndpointUri;
    }
}
