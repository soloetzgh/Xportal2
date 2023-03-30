package com.etzgh.xportal.utility;

import com.sun.crypto.provider.SunJCE;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.log4j.Logger;

public class Cryptographer {

    public final int ENCRYT = 1;
    public final int DECRYT = 2;
    public static final short LENGTH_DES = 64;
    public static final short LENGTH_DES3_2KEY = 128;
    public static final short LENGTH_DES3_3KEY = 192;
    private static boolean loaded = false;
    private byte[] result;
    private static final Logger log = Logger.getLogger(Cryptographer.class.getName());

    public Cryptographer() {
        if (!loaded) {
            init();
            loaded = true;
        }
    }

    public static String byte2hex(byte[] b) {
        StringBuilder d = new StringBuilder();
        if (b != null) {

            d = new StringBuilder(b.length * 2);
            for (int i = 0; i < b.length; i++) {
                char hi = Character.forDigit(b[i] >> 4 & 0xF, 16);
                char lo = Character.forDigit(b[i] & 0xF, 16);
                d.append(Character.toUpperCase(hi));
                d.append(Character.toUpperCase(lo));
            }
        }
        return d.toString();
    }

    public static String byte2hex(byte[] b, int offset, int len) {
        StringBuilder d = new StringBuilder(len * 2);
        len += offset;
        for (int i = offset; i < len; i++) {
            char hi = Character.forDigit(b[i] >> 4 & 0xF, 16);
            char lo = Character.forDigit(b[i] & 0xF, 16);
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
        }
        return d.toString();
    }

    public static byte[] hex2byte(byte[] b, int offset, int len) {
        byte[] d = new byte[len];
        for (int i = 0; i < len * 2; i++) {
            int shift = i % 2 == 1 ? 0 : 4;
            int tmp30_29 = (i >> 1);
            byte[] tmp30_25 = d;
            tmp30_25[tmp30_29] = ((byte) (tmp30_25[tmp30_29] & 0xff | Character.digit((char) b[(offset + i)], 16) << shift));
        }
        return d;
    }

    private void init() {
        try {
            Cipher c = Cipher.getInstance("RSA");
        } catch (Exception e) {
            log.info("Installing SunJCE provider driver.");
            Provider sunjce = new SunJCE();
            Security.addProvider(sunjce);
        }
    }

    public static byte[] hex2byte(String s) {
        return hex2byte(s.getBytes(), 0, s.length() >> 1);
    }

    private static byte[] trim(byte[] array, int length) {
        byte[] trimmedArray = new byte[length];
        System.arraycopy(array, 0, trimmedArray, 0, length);
        return trimmedArray;
    }

    public String setISOType0PINBlock(String pan, String clearPin, String key)
            throws Exception {
        String pinblock = "";
        String enpinblock = "";
        try {
            pinblock = formPINBlock(pan, clearPin);
            enpinblock = byte2hex(doCryto(hex2byte(pinblock), formDESKey((short) (key.length() / 16), hex2byte(key)), 1));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return enpinblock;
    }

    public String getISOType0PIN(String pan, String enpin, String key)
            throws Exception {
        String pin = "";
        String denpinblock = "";
        try {
            denpinblock = byte2hex(doCryto(hex2byte(enpin), formDESKey((short) (key.length() / 16), hex2byte(key)), 2));
            pin = getPINFromBlock(pan, denpinblock);
        } catch (Exception ee) {
            log.error(ee.getMessage(), ee);
        }
        return pin;
    }

    static byte[] encodedKey = null;

    public void setRSAPublicKey(String publicKeyFile) {
        InputStream is = null;
        try {
            File keyFile = new File(publicKeyFile);
            encodedKey = new byte[(int) keyFile.length()];
            is = new FileInputStream(keyFile);

            int count = 0;
            while ((count = is.read(encodedKey)) > 0) {

            }
        } catch (IOException ee) {
            encodedKey = null;
            log.info("Cryptographer::setRSAPublicKey() -- " + ee);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    public String doRSAEncryption(String data) {
        byte[] result = new byte[1000];
        try {
            if (encodedKey == null) {
                throw new Exception("You have not set public key");
            }
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);

            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pk = kf.generatePublic(publicKeySpec);

            Cipher rsa = Cipher.getInstance("RSA");

            rsa.init(1, pk);
            result = rsa.doFinal(data.getBytes());
            try (OutputStream os = new CipherOutputStream(new FileOutputStream("encrypted.rsa"), rsa); Writer out = new OutputStreamWriter(os)) {
                out.write(data);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new String(result);
    }

    public String formPINBlock(String pan, String pin)
            throws Exception {
        int startindex = pan.length() - 1 - 12;
        String panblock = "0000" + pan.substring(startindex, pan.length() - 1);
        String pinblock = "04" + pin + "FFFFFFFFFF";
        String block = hexor(panblock, pinblock);
        return block;
    }

    public String getPINFromBlock(String pan, String epinblock)
            throws Exception {
        String cpin = "";
        int startindex = pan.length() - 1 - 12;
        String panblock = "0000" + pan.substring(startindex, pan.length() - 1);
        String unxorpin = hexor(panblock, epinblock);
        cpin = unxorpin.substring(2, 6);
        return cpin;
    }

    public static byte[] xor(byte[] op1, byte[] op2) {
        byte[] result = new byte[1000];
        if (op2.length > op1.length) {
            result = new byte[op1.length];
        } else {
            result = new byte[op2.length];
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = ((byte) (op1[i] ^ op2[i]));
        }
        return result;
    }

    public static String hexor(String op1, String op2) {
        byte[] xor = xor(hex2byte(op1), hex2byte(op2));
        return byte2hex(xor);
    }

    /* Error */
    public void loadKeyFromFile(String path, int KEYSIZE)
            throws Exception {

    }

    public String doMd5Hash(String rawdata) {
        byte[] hash = new byte[1000];
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(rawdata.getBytes());
            hash = digest.digest();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return byte2hex(hash);
    }

    public Key getNewKey(short keyLength)
            throws NoSuchAlgorithmException {
        Key clearKey = null;
        try {
            KeyGenerator k1 = null;
            if (keyLength > 64) {
                k1 = KeyGenerator.getInstance("DESede");
            } else {
                k1 = KeyGenerator.getInstance("DES");
            }
            clearKey = k1.generateKey();
            byte[] clearKeyBytes = getDesKeyPart(keyLength, clearKey);
            adjustDESParity(clearKeyBytes);
            clearKey = formDESKey(keyLength, clearKeyBytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return clearKey;
    }

    public String getKeyString(short keyLength)
            throws NoSuchAlgorithmException {
        Key clearKey = null;
        String retkey = "";
        try {
            KeyGenerator k1 = null;
            if (keyLength > 64) {
                k1 = KeyGenerator.getInstance("DESede");
            } else {
                k1 = KeyGenerator.getInstance("DES");
            }
            clearKey = k1.generateKey();
            byte[] clearKeyBytes = getDesKeyPart(keyLength, clearKey);
            adjustDESParity(clearKeyBytes);
            clearKey = formDESKey(keyLength, clearKeyBytes);
            retkey = byte2hex(clearKey.getEncoded());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return retkey;
    }

    public String getKeyCheckValue(String skey) {
        String wholecheckvalue = null;
        try {
            byte[] zeros = hex2byte(skey.length() == 16 ? "0000000000000000" : "00000000000000000000000000000000");
            Key newkey = formDESKey(skey);
            wholecheckvalue = byte2hex(doCryto(zeros, newkey, 1));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return wholecheckvalue;
    }

    public Key formDESKey(String key)
            throws Exception {
        short len = (short) (key.length() / 2 * 8);
        return formDESKey(len, hex2byte(key));
    }

    public Key formDESKey(short keyLength, byte[] clearKeyBytes)
            throws Exception {
        Key key = null;
        switch (keyLength) {
            case 64:
                key = new SecretKeySpec(clearKeyBytes, "DES");

                break;
            case 128:
                clearKeyBytes = concat(
                        clearKeyBytes, 0, getBytesLength((short) 128),
                        clearKeyBytes, 0, getBytesLength((short) 64));

            case 192:
                key = new SecretKeySpec(clearKeyBytes, "DESede");
                break;
        }
        if (key == null) {
            throw new Exception("Unsupported DES key length: " + keyLength + " bits");
        }
        return key;
    }

    public static byte[] concat(byte[] array1, byte[] array2) {
        byte[] concatArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, concatArray, 0, array1.length);
        System.arraycopy(array2, 0, concatArray, array1.length, array2.length);
        return concatArray;
    }

    private static byte[] concat(byte[] array1, int beginIndex1, int length1, byte[] array2, int beginIndex2, int length2) {
        byte[] concatArray = new byte[length1 + length2];
        System.arraycopy(array1, beginIndex1, concatArray, 0, length1);
        System.arraycopy(array2, beginIndex2, concatArray, length1, length2);
        return concatArray;
    }

    private static void adjustDESParity(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            bytes[i] = ((byte) (b & 0xFE | (b >> 1 ^ b >> 2 ^ b >> 3 ^ b >> 4 ^ b >> 5 ^ b >> 6 ^ b >> 7 ^ 0x1) & 0x1));
        }
    }

    public static byte[] getAdjustedDESParity(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            bytes[i] = ((byte) (b & 0xFE | (b >> 1 ^ b >> 2 ^ b >> 3 ^ b >> 4 ^ b >> 5 ^ b >> 6 ^ b >> 7 ^ 0x1) & 0x1));
        }
        return bytes;
    }

    public static boolean isDESParityAdjusted(byte[] bytes) {
        byte[] correct = (byte[]) bytes.clone();
        adjustDESParity(correct);
        return Arrays.equals(bytes, correct);
    }

    private byte[] getDesKeyPart(short keyLength, Key clearDESKey)
            throws Exception {
        byte[] clearKeyBytes = clearDESKey.getEncoded();
        clearKeyBytes = trim(clearKeyBytes, getBytesLength(keyLength));
        return clearKeyBytes;
    }

    public int getBytesLength(short keyLength)
            throws Exception {
        int bytesLength = 0;
        switch (keyLength) {
            case 64:
                bytesLength = 8;
                break;
            case 128:
                bytesLength = 16;
                break;
            case 192:
                bytesLength = 24;
                break;
            default:
                throw new Exception("Unsupported key length: " + keyLength + " bits");
        }
        return bytesLength;
    }

    public byte[] doDesIvCrypto(byte[] theKey, byte[] theMsg, String algorithm, byte[] theIVp, int mode)
            throws Exception {
        byte[] theCph = new byte[1000];
        try {
            KeySpec ks = new DESKeySpec(theKey);
            SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
            SecretKey ky = kf.generateSecret(ks);
            Cipher cf = Cipher.getInstance(algorithm);
            if (theIVp == null) {
                cf.init(mode == 0 ? 1 : 2, ky);
            } else {
                AlgorithmParameterSpec aps = new IvParameterSpec(theIVp);
                cf.init(mode == 0 ? 1 : 2, ky, aps);
            }
            theCph = cf.doFinal(theMsg);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return theCph;
    }

    public static String SHA1(byte[] text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text, 0, text.length);
        sha1hash = md.digest();
        return byte2hex(sha1hash);
    }

    public byte[] doCryto(byte[] data, Key key, int CipherMode)
            throws Exception {
        try {
            this.transformation = (key.getAlgorithm() + "/" + getDesMode());
            if (!getDesPadding().trim().equals("")) {
                this.transformation = (this.transformation + "/" + getDesPadding());
            }
            Cipher c1 = Cipher.getInstance(this.transformation);
            c1.init(CipherMode, key);
            result = c1.doFinal(data);
        } catch (Exception e) {
            throw new Exception(e);
        }

        return result;
    }

    public byte[] doCryto(byte[] data, byte[] bkey, int CipherMode)
            throws Exception {
        Key key = null;
        try {
            key = formDESKey(byte2hex(bkey));
            this.transformation = (key.getAlgorithm() + "/" + getDesMode());
            if (!getDesPadding().trim().equals("")) {
                this.transformation = (this.transformation + "/" + getDesPadding());
            }
            Cipher c1 = Cipher.getInstance(this.transformation);
            c1.init(CipherMode, key);
            result = c1.doFinal(data);
        } catch (Exception e) {

            throw new Exception(e);
        }

        return result;
    }

    public byte[] doCryto(String data, String key, int CipherMode)
            throws Exception {
        try {
            byte[] data1 = hex2byte(data);
            Key key1 = formDESKey(key);
            this.transformation = (key1.getAlgorithm() + "/" + getDesMode());
            if (!getDesPadding().trim().equals("")) {
                this.transformation = (this.transformation + "/" + getDesPadding());
            }
            Cipher c1 = Cipher.getInstance(this.transformation);
            c1.init(CipherMode, key1);
            result = c1.doFinal(data1);
        } catch (Exception e) {

            throw new Exception(e);
        }

        return result;
    }

    public String getDynamicWorkingKey(String masterkey) {
        byte[] enkey = new byte[1000];
        byte[] checkvalue = new byte[1000];
        String chk = null;
        Key newkey = null;
        Key dend = null;
        int keyLen = 1;
        String fkey = "";
        try {
            byte[] keydata = hex2byte(masterkey);
            keyLen = byte2hex(keydata).length() / 16;
            Key master = formDESKey((short) (keyLen == 1 ? 64 : 128), keydata);

            newkey = getNewKey((short) (keyLen == 1 ? 64 : 128));

            chk = getKeyCheckValue(byte2hex(newkey.getEncoded()));
            enkey = doCryto(byte2hex(newkey.getEncoded()), masterkey, 1);
            if (isDESParityAdjusted(enkey)) {
                log.info("Key " + byte2hex(enkey) + "Parity Adjusted");
            } else {
                log.info("Key " + byte2hex(enkey) + " Parity NOT Adjusted");
            }
            fkey = byte2hex(enkey);
            log.info("Checkvalue of KWP:" + chk);
            fkey = fkey + chk.substring(0, 6);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return fkey;
    }

    String transformation = "";
    String desMode = "ECB";
    String desPadding = "NoPadding";

    public String getTransformation() {
        return this.transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public String getDesMode() {
        return this.desMode;
    }

    public void setDesMode(String desMode) {
        this.desMode = desMode;
    }

    public String getDesPadding() {
        return this.desPadding;
    }

    public void setDesPadding(String desPadding) {
        this.desPadding = desPadding;
    }
}
