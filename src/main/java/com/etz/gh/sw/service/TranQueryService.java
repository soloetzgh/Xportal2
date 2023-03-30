package com.etz.gh.sw.service;

import com.etz.gh.sw.dto.ExtraParams;
import com.etz.gh.sw.dto.Headers;
import com.etz.gh.sw.dto.TransQueryPayload;
import com.etz.gh.sw.utils.Encryption;
import com.etz.gh.sw.utils.Hash;
import com.etz.gh.sw.utils.PostClient;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import org.json.JSONObject;

public class TranQueryService {

    public static String postTransactionQuery(String url, TransQueryPayload payload, ExtraParams exParams) {
        String concatvalues = "";

        System.out.println("================Trasaction QueryObj===============");
        System.out.println("url:: " + url);

        System.out.println();

        JSONObject mainPayload = new JSONObject();

        mainPayload.put("session_id", payload.getSession_id());
        mainPayload.put("echo_data", payload.getEcho_data());
        mainPayload.put("extra_parameters", payload.getExtra_parameters());

        JSONObject query = new JSONObject();

        query.put("trace_id", payload.getQueries().getTrace_id());
        query.put("identifier", payload.getQueries().getIdentifier());
        query.put("client_trace_no", payload.getQueries().getClient_trace_no());
        query.put("dr_cr", payload.getQueries().getDr_cr());
        query.put("status_code", payload.getQueries().getStatus_code());

        query.put("client_trace_no", payload.getQueries().getClient_trace_no());

        query.put("start_txn_date", payload.getQueries().getStart_txn_date());

        query.put("end_txn_date", payload.getQueries().getEnd_txn_date());

        mainPayload.put("queries", query);

        LinkedHashMap<String, Object> hm = new LinkedHashMap<>();

        hm.put("echo_data", payload.getEcho_data());

        hm.put("extra_parameters", payload.getExtra_parameters());

        TreeMap<String, String> queryMap = new TreeMap<>();

        queryMap.put("trace_id", payload.getQueries().getTrace_id());
        queryMap.put("identifier", payload.getQueries().getIdentifier());
        queryMap.put("client_trace_no", payload.getQueries().getClient_trace_no());
        queryMap.put("dr_cr", payload.getQueries().getDr_cr());
        queryMap.put("status_code", payload.getQueries().getStatus_code());

        queryMap.put("start_txn_date", payload.getQueries().getStart_txn_date());

        queryMap.put("end_txn_date", payload.getQueries().getEnd_txn_date());

        hm.putAll(queryMap);

        hm.put("session_id", payload.getSession_id());

        System.out.println("mapkeyorder:: " + hm.keySet());

        for (Object value : hm.values()) {
            concatvalues += value;
        }

        System.out.println();

        System.out.println("concatvalues:: " + concatvalues);
        System.out.println();

        String hashOfPayload = Hash.HmacSHA256Encode(exParams.getHmacsha_256keyFromGSTKNS(), concatvalues);

        String rsaOfhashOfPayload = Encryption.RSAEncryptKey(hashOfPayload, exParams.getRsaPKeyFromGSTKNS());

        System.out.println("TransQueryPayload:: " + mainPayload);
        System.out.println();

        System.out.println("rsaOfhasOfPayload:: " + rsaOfhashOfPayload);
        System.out.println();
        System.out.println("hashOfPayload:: " + hashOfPayload);
        System.out.println();

        Headers headers = new Headers();
        headers.setSignature(rsaOfhashOfPayload);

        System.out.println("headers:: " + headers);
        System.out.println();

        System.out.println(PostClient.makePostRequest("POST", url, headers, mainPayload.toString()));
        return PostClient.makePostRequest("POST", url, headers, mainPayload.toString());

    }

}
