package com.etz.gh.sw.service;

import com.etz.gh.sw.dto.ExtraParams;
import com.etz.gh.sw.dto.GetSafeTokensPayload;
import com.etz.gh.sw.dto.Headers;
import com.etz.gh.sw.utils.Encryption;
import com.etz.gh.sw.utils.Hash;
import com.etz.gh.sw.utils.PostClient;
import java.util.HashMap;
import java.util.TreeMap;
import org.apache.commons.codec.binary.Base64;

public class GetSafeTokenService {

    public static HashMap<String, String> postGetSafeToken(String url, GetSafeTokensPayload payload, ExtraParams exParams) {

        System.out.println("================Get Safe Token===============");
        System.out.println("url:: " + url);
        System.out.println();

        TreeMap<String, String> map = new TreeMap<>();

        map.put("datetime", payload.getDatetime());

        System.out.println(map.values().toString());
        System.out.println();

        String concatvalues = "";

        for (String value : map.values()) {
            concatvalues += value;
        }

        System.out.println("concatvalues:: " + concatvalues);
        System.out.println();

        String md5hashOfPayload = Hash.md5(concatvalues);

        System.out.println("userid::" + exParams.getUserId());
        System.out.println();

        System.out.println("rsa public key from email::" + exParams.getRsaPKeyFromGSTKNS());

        String rsaOfUserIdPlusDate = Encryption.RSAEncryptKey(exParams.getUserId() + "." + payload.getDatetime(), exParams.getRsaPKeyFromGSTKNS());

        System.out.println();

        System.out.println("GetSafeTokensPayload:: " + payload);
        System.out.println();

        System.out.println("rsa Of UserId PlusDate:: " + rsaOfUserIdPlusDate);
        System.out.println();
        System.out.println("md5hashOfPayload:: " + md5hashOfPayload);
        System.out.println();

        Headers headers = new Headers();
        headers.setSignature(rsaOfUserIdPlusDate);

        byte[] base64HashOfUserId = Base64.encodeBase64(exParams.getUserId().getBytes());
        System.out.println("base64HashOfUserId:: " + new String(base64HashOfUserId));
        System.out.println();

        headers.setUserId(new String(base64HashOfUserId));

        headers.setHash(md5hashOfPayload);

        System.out.println("headers:: " + headers);

        System.out.println();

        return PostClient.doPost2("POST", url, headers, payload.toString());

    }

}
