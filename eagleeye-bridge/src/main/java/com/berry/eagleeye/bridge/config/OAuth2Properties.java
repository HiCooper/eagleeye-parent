package com.berry.eagleeye.bridge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @since 2021/3/11
 * fileName：OAuth2Properties
 * Use：
 */
@Component
@ConfigurationProperties(prefix = "oauth2", ignoreUnknownFields = false)
@Data
public class OAuth2Properties {

    private WebClientConfiguration webClientConfiguration = new WebClientConfiguration();

    private SignatureVerification signatureVerification = new SignatureVerification();

    @Data
    public static class WebClientConfiguration {
        private String clientId = "";
        private String clientSecret = "";
        private String scope = "";
        private String grantType = "client_credentials";
        /**
         * Holds the session timeout in seconds for non-remember-me sessions.
         * After so many seconds of inactivity, the session will be terminated.
         * Only checked during token refresh, so long access token validity may
         * delay the session timeout accordingly.
         */
        private int sessionTimeoutInSeconds = 1800;
        /**
         * Defines the cookie domain. If specified, cookies will be set on this domain.
         * If not configured, then cookies will be set on the top-level domain of the
         * request you sent, i.e. if you send a request to {@code app1.your-domain.com},
         * then cookies will be set on {@code .your-domain.com}, such that they
         * are also valid for {@code app2.your-domain.com}.
         */
        private String cookieDomain;

        /**
         * Fetch access_token uri
         */
        private String accessTokenEndpointUri = "http://uaa/oauth/token";
    }

    @Data
    public static class SignatureVerification {
        /**
         * Maximum refresh rate for public keys in ms.
         * We won't fetch new public keys any faster than that to avoid spamming UAA in case
         * we receive a lot of "illegal" tokens.
         */
        private long publicKeyRefreshRateLimit = 10 * 1000L;
        /**
         * Maximum TTL for the public key in ms.
         * The public key will be fetched again from UAA if it gets older than that.
         * That way, we make sure that we get the newest keys always in case they are updated there.
         */
        private long ttl = 24 * 60 * 60 * 1000L;
        /**
         * Endpoint where to retrieve the public key used to verify token signatures.
         */
        private String publicKeyEndpointUri = "http://uaa/oauth/token_key";
    }
}
