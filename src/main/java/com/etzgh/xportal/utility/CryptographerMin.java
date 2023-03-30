package com.etzgh.xportal.utility;

import com.etzgh.xportal.controller.PortalSettings;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import static java.util.stream.Collectors.joining;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hashids.Hashids;
import org.mindrot.jbcrypt.BCrypt;

//import java.util.Base64;
public class CryptographerMin {

//    private static final int a;
    private static final Logger log = Logger.getLogger(CryptographerMin.class.getName());

    private static final String hash_key = StringUtils.substringBeforeLast(new PortalSettings().getSetting("hash_key"), ":");

    private static final String characterEncoding = "UTF-8";
//    private static final String cipherTransformation = "AES/GCM/NoPadding";
    private static final String cipherTransformation = "AES/CBC/PKCS5PADDING";

    private static final String aesEncryptionAlgorithm = "AES";
    private static final String ERROR = "ERROR";
    private static Hashids hashids = null;

    static {
        hashids = new Hashids(hash_key, 10);
    }

    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    public CryptographerMin() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
        } catch (Exception er) {
            System.out.println("GET HERE");
            log.error(ERROR, er);
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    public static void main(String[] args) throws NoSuchAlgorithmException {
//        CryptographerMin keyPairGenerator = new CryptographerMin();
//        log.info(String.format("Public Key : %s", new String(Base64.encodeBase64(keyPairGenerator.getPublicKey().getEncoded()))));
//        log.info(String.format("Private Key : %s", new String(Base64.encodeBase64(keyPairGenerator.getPrivateKey().getEncoded()))));
        log.info("TTT: " + getSHA512("233208438661|91|0050013DCE504CF8E5E4A8", false));  
//log.info("TTT: " + getSHA512("233558263819|92|913001A1A852F5D93962F4", true)); 
  
//2022-09-29 15:05:26 INFO  MobileAppDao:4109 - Accountmap: {0210210000054507=01063030008, 0210010000056089=01063030008}
//2022-09-29 15:05:26 INFO  MobileAppDao:4114 - Checking::: 0210010000023120
//2022-09-29 15:05:26 INFO  MobileAppDao:4114 - Checking::: 0210010000027288
        String cards = "bankCode    ";
//
        cards = "'" + Arrays.asList(cards.split(",")).stream().map(f -> cryptPan(f, 2)).collect(joining("','")) + "'";

        log.info("L:"+cards);
//        decodeId("YQdJrBBvdO");

        log.info("CARDS: " + decodeId("6nJWrGZw30"));
    }

    public static String byte2hex(byte[] b) {
        StringBuilder d = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            char hi = Character.forDigit(b[i] >> 4 & 0xF, 16);
            char lo = Character.forDigit(b[i] & 0xF, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }
        return d.toString();
    }

    public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);

        return cipherText;
    }

    public static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(cipherTransformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);

        return plainText;
    }

    private static byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[16];
        byte[] parameterKeyBytes = key.getBytes(characterEncoding);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));

        return keyBytes;
    }

    public static String encrypt(String plainText, String key)
            throws UnsupportedEncodingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        byte[] plainTextbytes = plainText.getBytes(characterEncoding);
        byte[] keyBytes = getKeyBytes(key);

        return new String(Base64.encodeBase64(encrypt(plainTextbytes, keyBytes, keyBytes)));
    }

    public static String decrypt(String encryptedText, String key)
            throws KeyException, GeneralSecurityException,
            GeneralSecurityException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] cipheredBytes = Base64.decodeBase64(encryptedText.getBytes());
        byte[] keyBytes = getKeyBytes(key);
        return new String(decrypt(cipheredBytes, keyBytes, keyBytes),
                characterEncoding);
    }

    public static String doMd5Hash(String rawdata) {
        byte[] hash = (byte[]) null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(rawdata.getBytes());
            hash = digest.digest();
            return byte2hex(hash);
        } catch (Exception e) {
            log.error(ERROR, e);
        }
        return null;
    }

    public static String hashWithBCrypt(String password) {
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        return hash;
    }

    public static boolean validateBCryptHash(String candidate, String hash) {
        boolean verified = BCrypt.checkpw(candidate, hash);

        return verified;
    }

    public static String SHA1(byte[] text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text, 0, text.length);
        sha1hash = md.digest();
        return byte2hex(sha1hash);
    }

    public static String cryptPan(String pan, int encType) {
        String cryptedPan = "";
        int subIndex = 6;
//        log.info("length: " + pan);
        if (pan.length() == 19 || pan.length() == 25) {
            subIndex = 9;
        }
        Cryptographer crypt = new Cryptographer();
        byte[] epinblock = null;
        String mmk = "01010101010101010101010101010101";
        if (encType == 1) {
            String padValue = "FFFFFF" + pan.substring(subIndex);
            try {
                crypt.getClass();
                epinblock = crypt.doCryto(padValue, mmk, 1);
                cryptedPan = pan.substring(0, subIndex) + Cryptographer.byte2hex(epinblock);
            } catch (Exception e) {
                log.error(ERROR, e);
            }
        } else {
            try {
                crypt.getClass();
                epinblock = crypt.doCryto(pan.substring(subIndex), mmk, 2);
                String decPan = Cryptographer.byte2hex(epinblock).substring(6);
                if (decPan.startsWith("FFFFFF")) {
                    decPan = decPan.substring(6);
                }
                cryptedPan = pan.substring(0, subIndex) + decPan;
            } catch (Exception e) {
                log.error(ERROR, e);
            }
        }
//        log.info(pan + " === " + cryptedPan);

        return cryptedPan;
    }

    public static String getSHA512(String mprofile, boolean status) {

        String toReturn = null;
        String str = mprofile + "|" + status;
//        log.info("CHANA ::: " + str);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(str.getBytes("utf8"));
            toReturn = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            log.error(ERROR, e);
        }
        return toReturn;
    }

    public static String getSHA512(String data) {

        String toReturn = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(data.getBytes("utf8"));
            toReturn = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            log.error(ERROR, e);
        }
        return toReturn;
    }

    public static boolean compareSHA512(String data, String sha512) {

        boolean valid = false;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(data.getBytes("utf8"));
            valid = String.format("%040x", new BigInteger(1, digest.digest())).equals(sha512);

        } catch (Exception e) {
            log.error(ERROR, e);
        }
        return valid;
    }

    public static String generateMac(String data) {
        return DigestUtils.md5Hex(data);
    }

    public String decodeHex(String hex) {
        String decoded = "";
        try {
//            Hex.decodeHex(data)
            Hex.encodeHexString(hex.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage(), ex);
        }
        return decoded;
    }

    public static String encryptEncodeToHex(String data, String macData) {
        String result = "";

        try {
            String r = encrypt(data + (!macData.isEmpty() ? "&mac=" + generateMac(macData) : ""), hash_key);
//            log.info("MAC GENERATED ::: " + generateMac(macData));
//            log.info("ENCODEING :: " + macData);
            result = Hex.encodeHexString(r.getBytes("UTF-8"));
        } catch (Exception e) {
            log.error(ERROR, e);
        }

        return result;
    }

    public static String decodeDecryptToString(String data) {

        String result = "";
        try {

            byte[] bytes = Hex.decodeHex(data.toCharArray());
            String r = new String(bytes, "UTF-8");
            result = decrypt(r, hash_key);
        } catch (Exception e) {
            log.error(ERROR, e);
        }
        return result;
    }

    public static String encodeId(int id) {

        return hashids.encode(id);
    }

    public static int decodeId(String id) {

        return (int) hashids.decode(id)[0];
    }

    public static String AESCBCEncrypt(String clean, String key, String iv) throws Exception {
        IvParameterSpec _iv = new IvParameterSpec(iv.getBytes("UTF-8"));
        SecretKeySpec _key = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, _key, _iv);
        byte[] encrypted = cipher.doFinal(clean.getBytes());
        return new String(Base64.encodeBase64(encrypted));
    }

    public static String AESCBCDecrypt(String encrypted, String key, String iv) {
        String result = "";
        try {
            IvParameterSpec _iv = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeySpec _key = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, _key, _iv);
            byte[] clean = cipher.doFinal(Base64.decodeBase64(encrypted.getBytes()));
            result = new String(clean);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
}
