package com.etzgh.xportal.utility;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;

public class Encryption {

//    public static final Properties prop = new Properties();
    private static final Config prop = new Config();
    private static final Logger log = Logger.getLogger(Encryption.class.getName());

    public static String encryptWithRSA(String data) {
        String encData = null;
        ObjectInputStream inputStream = null;
        try {

            String PUBLIC_KEY_FILE = prop.getProperty("vasPublicKey");

            inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
            String publicKey = (String) inputStream.readUTF();
            String pubKey = publicKey;

            Cipher cipher = Cipher.getInstance("RSA");

            // BASE64Decoder decoder = new BASE64Decoder();
            byte[] sigBytes2 = Base64.getDecoder().decode(pubKey);

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(sigBytes2);
            KeyFactory keyFact = KeyFactory.getInstance("RSA");
            PublicKey pubKey2 = keyFact.generatePublic(keySpec);

            cipher.init(Cipher.ENCRYPT_MODE, pubKey2);

            byte[] cipherText = cipher.doFinal(data.getBytes());

            encData = Base64.getEncoder().encodeToString(cipherText);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return encData;
    }

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
