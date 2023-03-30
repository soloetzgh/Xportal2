package com.etzgh.xportal.utility;

import com.etz.security.util.KSM;
import com.etz.security.util.PBEncryptor;
import com.etzgh.xportal.model.CardAccounts;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eugene
 */
public class PinGenerator {

    private static final Logger log = Logger.getLogger(PinGenerator.class.getName());

    private static final String regex = "\\b([0-9])\\1\\1+\\b";

    public static String generatePin() {
        String pin = "";
        boolean invalid = true;
        // Generation random 4 digit pin.
        do {
            Pattern p = Pattern.compile(regex);
            pin = String.valueOf(1000 + new Random().nextInt(8769));
            Matcher m = p.matcher(pin);
            invalid = m.matches();
        } while (invalid);

        return pin;
    }

    public static String generatePin(String cardNumber) {
        String query = "";
        JSONObject card = new JSONObject();
        // log.info("CARDL: " + cardNumber);
        try {

            final KSM ksm = new KSM();
            final String pvvKey = "B546F7FBF8197FB607B319B00DBAD9C1";

            // Generation random 4 digit pin.
            String pin = generatePin();

            card.put("cardNumber", cardNumber);
            card.put("pin", pin);
            // log.info("Pin: " + pin);
            //// 0qFWoVCwlv0= q31Q3j0ia9U=
            log.info("CARD: " + cardNumber);
            String cpin = cardNumber.substring(8, 12);
            final String offset = ksm.genOffset(pvvKey, cardNumber, pin);
            card.put("x_offset", new PBEncryptor().PBEncrypt(offset.substring(0, 4), cardNumber));
            card.put("card_pin", new PBEncryptor().PBEncrypt(cpin, cardNumber));
            card.put("result", "success");

            // log.info("pinRequest : " + card.toString());
            query = card.toString();

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            card.put("result", "failed");
            // log.info("pinRequest : " + card.toString());
            query = card.toString();
        }
        return query;
    }

    public static JSONObject generatePin(List<String> cardNumbers) {

        JSONObject resp = new JSONObject();
        resp.put("result", "success");

        List<CardAccounts> recordsList = new ArrayList<>();
        try {

            final KSM ksm = new KSM();
            final String pvvKey = "B546F7FBF8197FB607B319B00DBAD9C1";

            boolean invalid = true;
            String pin = "";
            do {
                Pattern p = Pattern.compile(regex);
                pin = String.valueOf(1000 + new Random().nextInt(8769));
                Matcher m = p.matcher(pin);
                invalid = m.matches();
            } while (invalid);

            PBEncryptor pbEnc = new PBEncryptor();
            final String current_pin = pin;
            cardNumbers.stream().map((String card_number) -> {
                CardAccounts ma = new CardAccounts();

                try {
                    ma.setCard_number(card_number);
                    ma.setPin(current_pin);
                    String cpin = card_number.substring(8, 12);
                    final String offset = ksm.genOffset(pvvKey, card_number, current_pin);
                    ma.setX_offset(pbEnc.PBEncrypt(offset.substring(0, 4), card_number));
                    ma.setCard_pin(pbEnc.PBEncrypt(cpin, card_number));
                } catch (Exception et) {
                    resp.put("result", "failed");
                }
                return ma;

            }).forEachOrdered((ma) -> {
                recordsList.add(ma);
            });

            resp.put("accounts", new JSONArray(recordsList));

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            resp.put("result", "failed");
        }
        return resp;
    }

    public JSONObject verifyPin(String cardNumber, String oldPin, String newPin) {
        JSONObject card = new JSONObject();
        // log.info("CARDL: " + cardNumber);
        try {

            final KSM ksm = new KSM();
            final String pvvKey = "B546F7FBF8197FB607B319B00DBAD9C1";

            String pin = newPin;
            // do {
            // Pattern p = Pattern.compile(regex);
            // pin = String.valueOf(1000 + new Random().nextInt(8769));
            // Matcher m = p.matcher(pin);
            // invalid = m.matches();
            // } while (invalid);

            card.put("cardNumber", cardNumber);
            card.put("pin", pin);
            log.info("Pin: " + pin);
            //// 0qFWoVCwlv0= q31Q3j0ia9U=
            String cpin = newPin;// cardNumber.substring(8, 12);
            final String offset = ksm.genOffset(pvvKey, cardNumber, pin);
            card.put("x_offset", new PBEncryptor().PBEncrypt(offset.substring(0, 4), cardNumber));
            card.put("card_pin", new PBEncryptor().PBEncrypt(cpin, cardNumber));
            System.out.println("OUT: " + new PBEncryptor().PBDecrypt(card.getString("card_pin"), cardNumber));
            card.put("result", "success");

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            card.put("result", "failed");
        }
        return card;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        JSONObject res = new PinGenerator().verifyPin("9140010000000059", "", "8484");

        // for (int i = 0; i < 9999; i++) {
        //
        // val = new PinGenerator().verifyPin("0050010000000276", "",
        // padLeftZeros(String.valueOf(i), 4)).getString("card_pin");
        //
        // if (check.equalsIgnoreCase(val)) {
        // System.out.println("pinoff :::: " + val + " " +
        // padLeftZeros(String.valueOf(i), 4));
        // }
        // }
        // do {
        // val = new PinGenerator().verifyPin("0050010000000276", "",
        // "1111").getString("card_pin");
        // } while (!check.equalsIgnoreCase(val));
        System.out.println("Query: update ecarddb.e_cardholder set change_pin = '1', x_offset='"
                + res.getString("x_offset") + "', card_pin='" + res.getString("card_pin") + "' where card_num = '"
                + res.getString("cardNumber") + "'");

        System.out.println("OUT: " + new PBEncryptor().PBDecrypt("R7z/6iwbLKM=", "0068860077370006"));

        String accounts = "9140010000000059";
        List<String> accountNumbers = Arrays.asList(accounts.split(","));

        JSONObject pinResp = generatePin(accountNumbers);

        System.out.println("RE: " + pinResp);
        List<CardAccounts> resp = new Gson().fromJson(pinResp.get("accounts").toString(),
                new TypeToken<List<CardAccounts>>() {
                }.getType());

        System.out.println("TW: " + resp);

    }

    public static String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

}
