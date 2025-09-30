package com.sonny.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@Slf4j
public class KeyUtils {

    @Value("${app.security.private-key}")
    private String PRIVATE_KEY;

    @Value("${app.security.public-key}")
    private String PUBLIC_KEY;

    private KeyUtils() {}

    public static PrivateKey loadPrivateKey(final String pemPath) throws Exception {
        final String key = readKeyFromResource(pemPath)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");  // Remove all whitespace characters

        final byte[] decoded = Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return  KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    public PrivateKey loadPrivateKey() throws Exception {
        if (PRIVATE_KEY == null || PRIVATE_KEY.trim().isEmpty()) {
            throw new IllegalArgumentException("Private key is not configured properly in application properties");
        }

        log.debug("Loading private key from environment variable: {}", PRIVATE_KEY);
        final byte[] decoded = Base64.getDecoder().decode(PRIVATE_KEY);
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return  KeyFactory.getInstance("RSA").generatePrivate(spec);
    }

    public static PublicKey loadPublicKey(final String pemPath) throws Exception {
        final String key = readKeyFromResource(pemPath)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");  // Remove all whitespace characters

        final byte[] decoded = Base64.getDecoder().decode(key);
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return  KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public PublicKey loadPublicKey() throws Exception {
        if (PUBLIC_KEY == null || PUBLIC_KEY.trim().isEmpty()) {
            throw new IllegalArgumentException("Public key is not configured properly in application properties");
        }

        log.debug("Loading public key from environment variable: {}", PUBLIC_KEY);
        final byte[] decoded = Base64.getDecoder().decode(PUBLIC_KEY);
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return  KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private static String readKeyFromResource(final String pemPath) throws Exception {
        try(final InputStream inputStream = KeyUtils.class.getClassLoader().getResourceAsStream(pemPath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Could not find key file: " + pemPath);
            }
            return new String(inputStream.readAllBytes());
        }
    }
}
