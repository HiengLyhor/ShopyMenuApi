package com.my.api.config;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryption {

    private static final String key = "2b7e151628aed2a6abf7158809cf4f3c";

    public static String encrypt(String text) throws Exception {
        // Generate a random IV
        byte[] iv = new byte[16]; // 16 bytes for AES
        new java.security.SecureRandom().nextBytes(iv);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

        byte[] encryptedText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

        // Combine IV and ciphertext to send both to the client (Base64 encode them)
        byte[] combined = new byte[iv.length + encryptedText.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedText, 0, combined, iv.length, encryptedText.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    // Method to decrypt text using AES
    public static String decrypt(String encryptedText) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedText);

        // Extract the IV from the combined data
        byte[] iv = new byte[16]; // 16 bytes for AES
        System.arraycopy(combined, 0, iv, 0, iv.length);

        // Extract the actual encrypted message
        byte[] encryptedMessage = new byte[combined.length - iv.length];
        System.arraycopy(combined, iv.length, encryptedMessage, 0, encryptedMessage.length);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        byte[] originalText = cipher.doFinal(encryptedMessage);

        return new String(originalText, StandardCharsets.UTF_8);
    }

}
