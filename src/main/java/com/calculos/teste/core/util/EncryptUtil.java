package com.calculos.teste.core.util;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptUtil {

    public static String encrypt(String data, String publicKeyPem) {
        try {
            PublicKey publicKey = loadPublicKey(publicKeyPem);

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("RSA encryption error", e);
        }
    }

    private static PublicKey loadPublicKey(String pem) throws Exception {

        String cleaned = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(cleaned);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);

        KeyFactory factory = KeyFactory.getInstance("RSA");

        return factory.generatePublic(spec);
    }

    public static String decrypt(String encryptedData, String privateKeyPem) {
        try {
            PrivateKey privateKey = loadPrivateKey(privateKeyPem);

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decoded = Base64.getDecoder().decode(encryptedData);

            byte[] decrypted = cipher.doFinal(decoded);

            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("RSA decryption error", e);
        }
    }

    private static PrivateKey loadPrivateKey(String pem) throws Exception {

        String cleaned = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(cleaned);

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);

        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }
}