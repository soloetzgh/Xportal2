package com.etzgh.xportal.utility;

/**
 *
 * @author seth.sebeh
 */
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSAUtil {

    // public static String publicKey =
    // "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJt0SePUcQfmBjhhVnArFhOvY8B/zmWPYjG+1lJSs/P1PsueZYbgQx/szk/Btb0ZVe1qNCsk00QJv40PwG4/gJFjtTIKPZ0Nf+uR64p8LZ4z/ph+OnPczxh3uKHZXZbA+LJIYlcl9KR44770LBRI95hiLOiuuXX9FP5PkWv7SdiwIDAQAB";
    // public static String privateKey =
    // "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMm3RJ49RxB+YGOGFWcCsWE69jwH/OZY9iMb7WUlKz8/U+y55lhuBDH+zOT8G1vRlV7Wo0KyTTRAm/jQ/Abj+AkWO1Mgo9nQ1/65HrinwtnjP+mH46c9zPGHe4odldlsD4skhiVyX0pHjjvvQsFEj3mGIs6K65df0U/k+Ra/tJ2LAgMBAAECgYAFuyVB8a/BMjX+3eJhyNvNTWWNkxSGXuSvXq3B/+pYo634vddjj8yitwCPSPJmC3BY/3QoAvTalTNZQGbIs1Vqr79bEjTV6MzcjpwthuHhGG2UGJ5kz/Xcv/ZNGA0/dFp47ITGsBcshauAqOnrJXMkYQTLHLMO12EP1H8EEejfsQJBAO6uWfTuBtnMy+3psRXAEMPhrCqJfha4cCMMrHYRz3ocfPbzEMsu7AXOmS0depDz2HE+Kck02OUraE79UiNpANcCQQDYWkQGDT/nigylKOlz2HFdJPkk6lMswwQHWP9tcw7zDzO2jWpTV1SESebk/NjMl0DRo3T7KOvKIO0lZzRluI5tAkEAgiisP8hKDh4Dm6NUAbNysQ9xDuVZ0FQWOtbfeaeuyD2e3CCjKNpWtbf7ikHIJCIGB4NJ0EK0gQzR3jmAwHSzkwJAT9LeDi2NSmxQxg8WMzvEd81QbyJVlcpODjfz5fnxphQRzGTYue8aq8RogEwZ4UJdE/vvCUvL00Pau1Ycqvsw8QJAMrhub8cxZ8yX0XFG/cUtNnlT/nrjVKvIGwnHHaqjxJbEP7ZezzZK+B8Is5jg5UupauQiUtMuQcKWbfBmXJr4gg==";
    // public static String callbackPrivateKey =
    // "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCZzhIlJWsK6Vxpjy/WJH0YNMo+kdVeiIPmxKSNtQ2m1FzG8igpqYHp0II+edfmhBA8VUB54Ida/rsM6uJ/z052hTzo4vjZNJXGZ99KlTD9HRDgouodU0DWs9KnBPZTqePsDV2t3kN98moMyEevw2eyK91c9M6SzlJkhm/3sNCUseiPnHdOQloxSQEO34epxxfKrUj2AldPLEYSqQT+UsPbn8hdWog52cQPk4Fq6evR0Bw+AKkv/n+JXIo20XhM3iVC1xL7kipr+g+8HENBXl5lfRhf71n6bJv47X5bCDfrK7A0f3XhICjfzmEVj3yeFj/UYiUgVRFRdji0sHQSM601AgMBAAECggEAKAQYbne0yOr8My6g2Is3zMN3Vx4kMGs+Ph0tXv0bJyubU9uPHX/PoCCyskSLKptzie74Tn1fWgpqair2cSBjnqx9s23SJtjqmutNih8U1tXoHTnlxWlytimlBfVhjVTnSvQTSnhIgQ1AEEFPfWhOo2mMN3IuCL4La8IagYwMSJMzmW5ZmmTnRtLd51gGR4++cbBtTFoopAZvCOpYxfnjBqj+fFVE0rW0WNG9WFapC1P84TRQS5pl9gGe8f8Xlqtg57X9MQSABE7pT0IWXUaqsJISDmPmauPENTVyYD66XHkZrISEAIjEGPQz6lCAI6tmOjNghx5FJY4WffDCToUS5QKBgQD0XwpCfAuOldC+Jy+UERH5OcylXIy8IdRcWIBl5ymDjkc6FvqoYIQ9ThW3JsO0E7afS0fhQZMfXyJsgQNCnYSjaKNC6aHOFwrdEvEMxCzehqEoZNXwUyQi4SwZFdjGcNm4WN7O9Er3F3NeZJ5aHSgY+8jMGaQnEF+OqLXFR+FMHwKBgQChH73OS741p1vIx55zfXjedzDbnoMeYTrcRQPyjBf0jKTLJp/Bdh12zaa3F6nSo9MwwBPRKoJacEmgQsEb4T2tlg5x503tEUiJH2V41FkPsvKJ7N3qfWgqZ+tR/0eTioNcgEQN447JDPqfvhpPjrRwuEuYb9nDee1cUey3W+ecKwKBgGZEvAN07FVAx3o85gF+X1pA4S83G0LEmZ4H/7wl0M7YpWBfK4l50v3hBt9+XpqnQV9K3wsbKVoQs1RbbaTcTR0h9mlTYz7fj9FTUwP3uZtrFljUUmBgOxhJye25od3ybnti3eQYgqMD8mlHjKnD1/sUrEUWWgdrJ8KJQoEMgvIbAoGAeAR9zNoMsEiejNtswtg2nXepNB5Jyupf/s2F2DjUazwMUKrPmzrRTbp4Ma/1tsqDOLAK0uZYPOehYDf5QjwwRkodLmk+WZVjM4joe+2o/ZGtKmH/F3kci2HnHqgJ9/PYT6HXj4TuygHZ+f2P+azx6XjODplaW0pKLVd0/Uv/JQsCgYBOgOzFa8/UyYFOcuZbaoF5LWyyR2S5+u5nZ53frPJPV5+BkvPEZURZebj4YQNNuTGFlwXUjOfUV/uJxzXRBZUndL+sX7C0wve6BK2vJO1V3EUziNcdFd5iDzL/6XsjXU3Ko0AR5EJqubvwndnfqc1NsBMG+W6Ut3KCZJSRAaOVuw==";
    private static final Logger log = Logger.getLogger(RSAUtil.class.getName());

    static {
        java.security.Security.addProvider(new BouncyCastleProvider());
    }

    public static PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey1 = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey1 = keyFactory.generatePublic(keySpec);
            return publicKey1;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return publicKey1;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        PrivateKey privateKey1 = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (keyFactory != null) {
            try {
                privateKey1 = keyFactory.generatePrivate(keySpec);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return privateKey1;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException,
            InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException,
            InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    /*public static void main(String[] args)
            throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
            long start = System.currentTimeMillis();
            System.out.println("TAT:: " + start);

            String encryptedString = Base64.getEncoder().encodeToString(encrypt("233209157113#1234#BOA", publicKey));
            System.out.println(encryptedString);
            System.out.printf("Encrypted String:: %s", encryptedString);

            String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            System.out.println();
            long stop = System.currentTimeMillis();
            System.out.println("TAT:: " + (stop - start));
            System.out.printf("Decrypted String:: %s", decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    } */
}
