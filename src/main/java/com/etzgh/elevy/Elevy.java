package com.etzgh.elevy;

import com.etzgh.xportal.model.EmiConfig;
import com.etzgh.xportal.model.EmiPspRequest;
import com.etzgh.xportal.utility.Config;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class Elevy {

    private static final Config config = new Config();
    private static final Logger log = Logger.getLogger(Elevy.class.getName());

    private static final String EMI_URL;

    static {
        EMI_URL = config.getProperty("EMI_URL");
    }

    public String[] sendEmiData(EmiPspRequest emiReq) {
        String[] result = {"06", emiReq.getRequestId(), "An Error Occured"};
        try {

            EmiConfig bankData = config.getEmiConfig().getOrDefault(emiReq.getBank(), new EmiConfig());
            if (bankData.getCHARGING_PARTY_ID() != null) {

                log.info("BANK DATA");
                log.info(bankData.toString());

                String tloc = "cfg/emi/" + bankData.getTRUSTSTORE_LOC();
                String kloc = "cfg/emi/" + bankData.getKEYSTORE_LOC();
                String apiKey = bankData.getAPI_KEY();
                String tpass = bankData.getTRUSTSTORE_PASSWORD();
                String kpass = bankData.getKEYSTORE_PASSWORD();

                String payload = "{\n"
                        + "  \"issuerID\": \"" + bankData.getISSUER_ID() + "\",\n"
                        + "  \"accountNumber\": \"" + emiReq.getAccountNumber() + "\",\n"
                        + "  \"accountType\": \"" + emiReq.getAccountType() + "\",\n"
                        + "  \"TIN\": \"" + emiReq.getTINorGhanaCard() + "\"\n"
                        + "}";

                log.info(payload);

                String response = SuperHttpClient.doPostSSL(EMI_URL, payload, tloc, tpass, kloc, kpass, apiKey);
                log.info("EMI RESPONSE for  "+emiReq.getRequestId()+": " + response);
                JSONObject resp = new JSONObject(response);
                result[0] = resp.optString("statusCode", "05");
                result[2] = resp.optString("statusMessage", "No Response");
            }

        } catch (Exception er) {
            log.error(er.getMessage(), er);
        }
        return result;
    }

}
