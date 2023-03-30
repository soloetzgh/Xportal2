package com.etz.vasgate.dao.impl;

import com.etz.vasgate.dao.VasgateDAO;
import com.etz.vasgate.models.VasgateRequest;
import com.etz.vasgate.util.Encryption;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DoHTTPRequest;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class VasgateDAOImpl implements VasgateDAO {

    public static final String CLIENT = "XPORTAL";
    private static final Logger log = Logger.getLogger(VasgateDAOImpl.class.getName());
    private static final Config prop = new Config();
    private static String vas_url = "";
    private static String vas_user = "";
    private static String vas_pass = "";

    public static void main(String[] args) {

        JSONObject res = new JSONObject(
                "{\"response\":\"63\",\"vasresponse\":\"{\\\"alias\\\":\\\"ECG\\\",\\\"reference\\\":\\\"02YB93439B65748\\\",\\\"clientRef\\\":\\\"02YB93439B65748\\\",\\\"amount\\\":38.0,\\\"action\\\":\\\"process\\\",\\\"type\\\":\\\"0\\\",\\\"mode\\\":\\\"1\\\",\\\"mac\\\":\\\"66701deffe9af6f0c85eca71effcb8ea\\\",\\\"account\\\":\\\"0041000007\\\",\\\"error\\\":\\\"63\\\",\\\"fault\\\":\\\"Security violation\\\",\\\"subscriber\\\":\\\" 721504369\\\",\\\"client\\\":\\\"XPORTAL\\\",\\\"channel\\\":\\\"02\\\",\\\"ip\\\":\\\"172.16.10.38\\\",\\\"bank\\\":\\\"07\\\",\\\"otherinfo\\\":\\\"02YB93439B65748| 721504369|0000000000\\\",\\\"date\\\":\\\"May 8, 2020 6:16:36 PM\\\"}\"}");

        log.info("Res: " + res.has("response"));
        new VasgateDAOImpl().verifyBiller("DSTV", "1025610754", "", "DSTVref9862");

    }

    static {
        try {
            final String vas_env = prop.getProperty("VAS_ENV");
            vas_url = prop.getProperty("VAS_" + vas_env.toUpperCase() + "_URL");
            vas_user = prop.getProperty("VAS_" + vas_env.toUpperCase() + "_USER");
            vas_pass = prop.getProperty("VAS_" + vas_env.toUpperCase() + "_PASS");
        } catch (Exception e) {
            log.error("error: ", e);
        }

//        disableSslVerification();
    }

    @Override
    public String doTopup(String vastype, String destacct, double amount, String reference) {

        String mac = Encryption.getMD5(vastype + reference + destacct + "0");
        VasgateRequest vasReq = new VasgateRequest();

        vasReq.setApiId(vas_user);
        vasReq.setApiSecret(vas_pass);
        vasReq.setProduct("VTU");
        vasReq.setAccount(destacct);
        vasReq.setAction("process");
        vasReq.setAlias(vastype);
        vasReq.setAmount(amount);
        vasReq.setBank(getChannel(reference));
        vasReq.setClient(CLIENT);
        vasReq.setMac(mac);
        vasReq.setMerchant("VTU");
        vasReq.setMobile(destacct);
        vasReq.setMode("1");
        vasReq.setName(CLIENT);
        vasReq.setOtherinfo(CLIENT);
        vasReq.setReference(reference);
        vasReq.setType("0");
        vasReq.setType2("0");

        Gson gson = new Gson();
        String requestData = gson.toJson(vasReq);
        log.info("Vas Request \n" + requestData);
        String resp = "";
        JSONObject json = new JSONObject();

        try {
            resp = new DoHTTPRequest().post(vas_url, Encryption.encryptWithRSA(requestData));
            log.info("Vasgate url:: " + vas_url);

            log.info("VASGATE PROCESS RESP " + resp);

            JSONObject jsonObject = new JSONObject(resp);
            String error = jsonObject.optString("error");
            String fault = jsonObject.optString("fault");

            log.info(fault);

            json.put("response", error);
            json.put("vasresponse", resp);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
        }
        return json.toString();
    }

    @Override
    public String payBill(String vastype, String destacct, double amount, String reference, String otherInfo) {

        String mac = Encryption.getMD5(reference + "" + amount + "" + destacct + CLIENT);

        VasgateRequest vasReq = new VasgateRequest();
        vasReq.setApiId(vas_user);
        vasReq.setApiSecret(vas_pass);
        vasReq.setProduct(vastype);
        vasReq.setAccount(destacct);
        vasReq.setAction("process");
        vasReq.setAlias(vastype);
        vasReq.setAmount(amount);
        vasReq.setBank(getChannel(reference));
        vasReq.setClient(CLIENT);
        vasReq.setMac(mac);
        vasReq.setMerchant("0041000007");
        vasReq.setMobile(destacct);
        vasReq.setMode("1");
        vasReq.setName(CLIENT);
        vasReq.setOtherinfo(otherInfo);
        vasReq.setReference(reference);
        vasReq.setType("0");
        vasReq.setType2("0");

        Gson gson = new Gson();
        String requestData = gson.toJson(vasReq);
        log.info("Vas Request \n" + requestData);

        String resp = "";
        JSONObject json = new JSONObject();

        try {
            resp = new DoHTTPRequest().post(vas_url, Encryption.encryptWithRSA(requestData));
            log.info("Vasgate url:: " + vas_url);

            log.info("VASGATE PROCESS RESP " + resp);

            JSONObject jsonObject = new JSONObject(resp);
            String error = jsonObject.optString("error");
            String fault = jsonObject.optString("fault");

            log.info(fault);

            json.put("response", error);
            json.put("vasresponse", resp);

        } catch (Exception e) {
            log.error("error: ", e);
        }
        return json.toString();
    }

    @Override
    public void verifyBiller(String vastype, String account, String otherInfo, String reference) {

        VasgateRequest vasRequest = new VasgateRequest();

        vasRequest.setApiId(vas_user);
        vasRequest.setApiSecret(vas_pass);
        vasRequest.setAccount(account);
        vasRequest.setAction("query");
        vasRequest.setProduct(vastype);
        vasRequest.setReference(reference);
        vasRequest.setAlias(vastype);
        vasRequest.setMerchant(vastype);
        vasRequest.setMobile(account);
        vasRequest.setMode("1");
        vasRequest.setMac("b805c1462e1ccc66071c3978596df08d");
        vasRequest.setClient(CLIENT);
        vasRequest.setOtherinfo(otherInfo);
        vasRequest.setType("0");
        vasRequest.setType2("0");

        Gson gson = new Gson();
        String requestData = gson.toJson(vasRequest);
        log.info("Vas Request \n" + requestData);
        String resp;

        try {
            resp = new DoHTTPRequest().post(vas_url, Encryption.encryptWithRSA(requestData));
            System.out.println("Vasgate url:: " + vas_url);

            System.out.println("VASGATE PROCESS RESP " + resp);

            JSONObject jsonObject = new JSONObject(resp);
            String fault = jsonObject.optString("fault");

            log.info(fault);

        } catch (Exception e) {
            log.error("error: ", e);
        }
    }

    public String getChannel(String reference) {

        String code = reference.substring(0, 2);

        switch (code) {
            case "ju":
                code = "07";
                break;
            default:
                break;
        }
        return code;
    }
}
