package com.etz.gh.sw.utils;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.log4j.Logger;

public class Encryption {

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final Logger log = Logger.getLogger(Encryption.class.getName());

    public static String generateRandomApiKey() {
        String randKey = generateKey(8);

        return randKey;
    }

    private static String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public static String generateKey(int length) {
        return generateRandomString(length);
    }

    public static String RSAEncryptKey(String message, String publicKey) {
        String encryptedMyKey = null;

        try {
            encryptedMyKey = java.util.Base64.getEncoder().encodeToString(RSAUtil.encrypt(message, publicKey));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException
                | NoSuchAlgorithmException e) {

            log.info("context", e);
        }

        return encryptedMyKey;
    }

    public static String rsaEncrypt(String input, String rsaPublicKey) {
        String result = "";
        try {

            byte[] buffer = Base64.getDecoder().decode(rsaPublicKey);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] inputArray = input.getBytes();
            int inputLength = inputArray.length;
            System.out.println("Encrypted Bytes:" + inputLength);

            int MAX_ENCRYPT_BLOCK = 117;

            int offSet = 0;
            byte[] resultBytes = {};
            byte[] cache = {};
            while (inputLength - offSet > 0) {
                if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                    offSet += MAX_ENCRYPT_BLOCK;
                } else {
                    cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                    offSet = inputLength;
                }
                resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
                System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
            }

            result = Base64.getEncoder().encodeToString(resultBytes);

        } catch (Exception e) {

            log.info("context", e);
        }
        System.out.println("Encrypted result: " + result);
        return result;
    }

    public static String AESEncryptPayloadWithNormalKey(String myKey, String payload) {
        Crypto.setKey(myKey);

        String encryptedString = new Crypto().encrypt(payload.trim(), myKey);

        return encryptedString;
    }
}
