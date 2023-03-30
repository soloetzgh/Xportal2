package com.etz.gh.sw.utils;

/**
 *
 * @author Oteng David
 */
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class Crypto {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey) {

        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");

            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (Exception e) {
            System.out.println("Could not Generate key");
        }

    }

    public String encrypt(String strToEncrypt, String myKey) {
        try {

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));

        } catch (Exception e) {

            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
}
