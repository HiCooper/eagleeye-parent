package com.berry.eagleeye.bridge.security.oauth2;

import com.berry.eagleeye.bridge.config.OAuth2Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created with IntelliJ IDEA.
 *
 * @author HiCooper.
 * @version 1.0
 * @since 2021/3/11
 * fileName：OAuth2JwtAccessTokenConverter
 * Use：
 */
@Component
public class OAuth2JwtAccessTokenConverter extends JwtAccessTokenConverter {
    private final Logger log = LoggerFactory.getLogger(OAuth2JwtAccessTokenConverter.class);

    private final UaaSignatureVerifierClient uaaSignatureVerifierClient;
    private final OAuth2Properties oAuth2Properties;

    /**
     * When did we last fetch the public key?
     */
    private long lastKeyFetchTimestamp;

    private String accessToken;

    public OAuth2JwtAccessTokenConverter(OAuth2Properties oAuth2Properties, UaaSignatureVerifierClient uaaSignatureVerifierClient) {
        this.oAuth2Properties = oAuth2Properties;
        this.uaaSignatureVerifierClient = uaaSignatureVerifierClient;
        tryCreateSignatureVerifier();
    }

    @Override
    protected Map<String, Object> decode(String token) {
        try {
            //check if our public key and thus SignatureVerifier have expired
            long ttl = oAuth2Properties.getSignatureVerification().getTtl();
            if (ttl > 0 && System.currentTimeMillis() - lastKeyFetchTimestamp > ttl) {
                throw new InvalidTokenException("public key expired");
            }
            return super.decode(token);
        } catch (InvalidTokenException ex) {
            if (tryCreateSignatureVerifier()) {
                return super.decode(token);
            }
            throw ex;
        }
    }

    /**
     * Fetch a new public key from the AuthorizationServer.
     *
     * @return true, if we could fetch it; false, if we could not.
     */
    private boolean tryCreateSignatureVerifier() {
        long t = System.currentTimeMillis();
        if (t - lastKeyFetchTimestamp < oAuth2Properties.getSignatureVerification().getPublicKeyRefreshRateLimit()) {
            return false;
        }
        try {
            SignatureVerifier verifier = uaaSignatureVerifierClient.getSignatureVerifier();
            if (verifier != null) {
                setVerifier(verifier);
                lastKeyFetchTimestamp = t;
                log.debug("Public key retrieved from OAuth2 server to create SignatureVerifier");
                return true;
            }
        } catch (Exception ex) {
            log.error("could not get public key from OAuth2 server to create SignatureVerifier", ex);
        }
        return false;
    }

    public boolean tryVerifyToken(String token) {
        if (isBlank(token)) {
            return false;
        }
        try {
            decode(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 尝试获取 access_token
     * 首先验证当前 token 是否有效， 有效直接返回，否则请求 uaa service 获取
     *
     * @return token
     */
    public synchronized String tryGetAccessToken() {
        if (isBlank(accessToken)) {
            accessToken = uaaSignatureVerifierClient.requestAccessToken();
            return accessToken;
        }
        try {
            decode(accessToken);
        } catch (Exception e) {
            accessToken = null;
        }
        return accessToken;
    }
}
