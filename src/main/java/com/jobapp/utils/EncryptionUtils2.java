package com.jobapp.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class EncryptionUtils2 {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] encryptAES(String plaintext, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(plaintext.getBytes());
    }

    public static String decryptAES(byte[] ciphertext, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] decryptedBytes = cipher.doFinal(ciphertext);
        return new String(decryptedBytes);
    }

    public static byte[] encryptRSA(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    public static byte[] decryptRSA(byte[] cipherText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(cipherText);
    }

    public static void main(String[] args) throws Exception {
        // Generate RSA key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Generate AES key and IV
        SecretKey secretKey = new SecretKeySpec("0123456789abcdef0123456789abcdef".getBytes(), "AES");
        byte[] iv = "abcdefghijklmnop".getBytes();

        // Encrypt and decrypt a sample plaintext with AES
        String plaintextAES = "Hello, AES!";
        byte[] encryptedAES = encryptAES(plaintextAES, secretKey, iv);
        String decryptedAES = decryptAES(encryptedAES, secretKey, iv);

        // Encrypt and decrypt a sample plaintext with RSA
        String plaintextRSA = "Hello, RSA!";
        byte[] encryptedRSA = encryptRSA(plaintextRSA.getBytes(), publicKey);
        byte[] decryptedRSA = decryptRSA(encryptedRSA, privateKey);

        // Print results
        System.out.println("Plaintext (AES): " + plaintextAES);
        System.out.println("Encrypted (AES): " + new String(encryptedAES));
        System.out.println("Decrypted (AES): " + decryptedAES);
        System.out.println("Plaintext (RSA): " + plaintextRSA);
        System.out.println("Encrypted (RSA): " + new String(encryptedRSA));
        System.out.println("Decrypted (RSA): " + new String(decryptedRSA));
    }
}

