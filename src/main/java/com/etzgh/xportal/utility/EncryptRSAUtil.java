package com.etzgh.xportal.utility;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * @author sunkwa-arthur
 */
public class EncryptRSAUtil {

    private static final Logger log = Logger.getLogger(EncryptRSAUtil.class.getName());

    /**
     *
     * @return
     */
    public static Map<String, String> generateKeyPair() {
        Map<String, String> map = new HashMap<>();

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

            String publicKey = Base64.encodeBase64String(rsaPublicKey.getEncoded());
            String privateKey = Base64.encodeBase64String(rsaPrivateKey.getEncoded());

            map.put("publicKey", publicKey);
            map.put("privateKey", privateKey);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }

        return map;
    }

    /**
     *
     * @param str
     * @param rsaPublicKey
     * @return
     */
    public static String encrypt(String str, String rsaPublicKey) {
        String returnStr = "";

        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(rsaPublicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(str.getBytes());
            returnStr = Base64.encodeBase64String(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return returnStr;
    }

    /**
     *
     * @param str
     * @param rsaPrivateKey
     * @return
     */
    public static String decrypt(String str, String rsaPrivateKey) {
        String returnStr = "";

        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(rsaPrivateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] result = cipher.doFinal(Base64.decodeBase64(str));
            returnStr = new String(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return returnStr;
    }

    public static void main(String[] args) {
        Map<String, String> map = generateKeyPair();

        String publicKey = map.get("publicKey");
        String privateKey = map.get("privateKey");

        System.out.println("Public Key : " + publicKey);
        System.out.println("Private Key : " + privateKey);

        String str = "hello etranzact";

        String encryptStr = encrypt(str, publicKey);

        System.out.println("Encrypt : " + encryptStr);

        String dencryptStr = decrypt(encryptStr, privateKey);

        System.out.println("Decrypt : " + dencryptStr);

        String praivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANDEizs5xoIxZCeGn/vlRXWPo1CEM7KBMvfL4XDTGFh/x7MsbuZAWvgxK9vj/ymucCeLVd86JXayqmgt8u9B4x9GuVyL0d6Yvhe+rVvPczjQMnBYZ33GawQcn/hUWXchct3rm3r9pD4VNpZy7taVHre4A6bf/1LtckDXId5CaWT9AgMBAAECgYEAg8vxEjzRQ5Qm5IUrLv15MlSyB7zOXl0Obj26X14FOqnAyy67/ISYaaOxSqrPheLoTy650amFyT/WMNsBSWbRGOM5GPPfzLzVdiPjHa/bBMBeVUIYzB9yQP1X4UqqyIyUR8Bh6e9q6UOkVWBpdtYMEfdr8xqYt65u3xkIGUeRoUkCQQD2BU+dhZq2WeieUi6oer84ExXDghiaIeF4qj7yjBaR9FJgKtdTRZRe5Hnhrmiso7wA/tIyOlaRB/I8+myaFBSbAkEA2TxldHylt2/PfBJA690V5K3teCLsBWBo645HO30xStIcY5hGM606IvAbuKXyCcaU4ekulEc66LsnwgTHpybqRwJAZ9GF72taBmmaiHUVy3NBDmC/ZmmYCDMT+t+dAK2tOJppyLtLcpCCNCkmZd47vd66j5D5EskbjshTqPfkrICc0QJBAMz2KRsxCAH9IpyDdHGV1TQ/zhBNkra38gZd5WOBiWJ4v4RQhdv4EyQnu2AKYkVK8en8YpBQbIptGuP6Mo3JL70CQQCJ92URWBfA4F/n6vnombl9vBcH5o42pWrdrjM7c18OOd54lmn7w3RTFd1mI4qhutJYVllG5yJ+POsB3s8zRNF3";
        str = "0LNNGqbfpUcyl1/DYmQMZ+Lbwp01akoD/s01HZCGvoa9iMlrebLHn+Hm9asHSQHtf1amsRbBDY2kuyNL4lopyzDH5UpvPlJQ13xMrYM7PFnAuL/w4g38FeLBjYSo3Ky3UigVj6XRs6fR1OKnRm6g09LSRagEo7AInKQFgKaxHRw=";
        String b = decrypt(str, praivateKey);
        System.out.println(b);

    }

}
