package com.jobapp.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class EncryptionUtils {

    public static void main(String[] args) throws Exception {
        // Generate RSA key pair
        KeyPair keyPair = generateRSAKeyPair();
        // Generate AES key
        SecretKey aesKey = generateAESKey();

        // Encrypt with AES
        byte[] iv = generateIV();
        byte[] encryptedData = encryptAES("Sudip", aesKey, iv);

        // Encrypt AES key with RSA public key
        byte[] encryptedAESKey = encryptRSA(aesKey.getEncoded(), keyPair.getPublic());

        // Decrypt AES key with RSA private key
        byte[] decryptedAESKeyBytes = decryptRSA(encryptedAESKey, keyPair.getPrivate());
        SecretKey decryptedAESKey = new SecretKeySpec(decryptedAESKeyBytes, "AES");

        // Decrypt with AES
        byte[] decryptedData = decryptAES(encryptedData, decryptedAESKey, iv);
        String decryptedString = new String(decryptedData);
        System.out.println("Decrypted Data: " + decryptedString);
    }

    // Generate RSA key pair
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    // Generate AES key
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    // Generate IV
    public static byte[] generateIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    // Encrypt with AES
    public static byte[] encryptAES(String plaintext, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(plaintext.getBytes());
    }

    // Decrypt with AES
    public static byte[] decryptAES(byte[] ciphertext, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(ciphertext);
    }

    // Encrypt with RSA
    public static byte[] encryptRSA(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    // Decrypt with RSA
    public static byte[] decryptRSA(byte[] ciphertext, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(ciphertext);
    }
}

