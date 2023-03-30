/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.vasgate.util;

import com.etzgh.xportal.utility.Config;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.Cipher;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author yaw.owusu-koranteng
 */
public class NewEncryption {
    
     public static final Config prop = new Config();
    private static String KEY = "";

    private static final Logger log = Logger.getLogger(Encryption.class.getName());

    static {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(new File("cfg" + File.separator + "vas-smsinterface.key")));
            KEY = (String) inputStream.readUTF();
        } catch (Exception e) {
            log.error("error", e);
        }
    }

//    public static String encryptWithRSA(String data) {
//        String encData = null;
//        try {
//
//            Cipher cipher = Cipher.getInstance("RSA");
//
//            BASE64Decoder decoder = new BASE64Decoder();
//            byte[] sigBytes2 = decoder.decodeBuffer(KEY);
//
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(sigBytes2);
//            KeyFactory keyFact = KeyFactory.getInstance("RSA");
//            PublicKey pubKey2 = keyFact.generatePublic(keySpec);
//
//            cipher.init(Cipher.ENCRYPT_MODE, pubKey2);
//
//            byte[] cipherText = cipher.doFinal(data.getBytes());
//
//            encData = Base64.getEncoder().encodeToString(cipherText);
//
//        } catch (Exception e) {
//            log.error("error", e);
//        }
//        return encData;
//    }
    
    public static String encryptWithRSA(String data) {
		String encData = null;
		try {
//			// String PUBLIC_KEY_FILE = "C:/wsdl/vas-smsinterface.key";
//			String PUBLIC_KEY_FILE = prop.getProperty("vasPublicKey");
//			//ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
//			String publicKey = (String) inputStream.readUTF();
			String pubKey = KEY;

			Cipher cipher = Cipher.getInstance("RSA");// MD5withRSA

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] sigBytes2 = decoder.decodeBuffer(pubKey);

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(sigBytes2);
			KeyFactory keyFact = KeyFactory.getInstance("RSA");
			PublicKey pubKey2 = keyFact.generatePublic(keySpec);

			cipher.init(Cipher.ENCRYPT_MODE, pubKey2);

			byte[] cipherText = cipher.doFinal(data.getBytes());

			encData = new BASE64Encoder().encode(cipherText);

//			log.info("ENC>>: \n" + encData);

		} catch (Exception e) {
			e.printStackTrace(System.out);
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
