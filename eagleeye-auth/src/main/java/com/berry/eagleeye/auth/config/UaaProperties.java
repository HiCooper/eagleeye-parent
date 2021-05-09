package com.berry.eagleeye.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Properties for UAA-based OAuth2 security.
 */
@Component
@ConfigurationProperties(prefix = "uaa", ignoreUnknownFields = false)
public class UaaProperties {
    private KeyStore keyStore = new KeyStore();

    public KeyStore getKeyStore() {
        return keyStore;
    }

    /**
     * Keystore configuration for signing and verifying JWT tokens.
     */
    public static class KeyStore {
        private String name = "keystore.jks";
        //password used to access the key
        private String password = "berry123";
        //name of the alias to fetch
        private String alias = "keystore";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }
}
