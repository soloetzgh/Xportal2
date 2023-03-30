package com.etzgh.xportal.utility;

import com.etz.http.etc.Card;
import com.etz.http.etc.TransCode;
import com.etz.http.etc.XProcessor;
import com.etz.http.etc.XRequest;
import com.etz.http.etc.XResponse;
import com.etzgh.xportal.autoswitch.AutoSwitchInfo;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.json.JSONObject;

/**
 *
 * @author sunkwa-arthur
 */
public class AutoRequest {

    // final Properties props = new Properties();
    // private static final config config = new config();
    // CREDIT BANK ACCOUNT
    private static final Logger log = Logger.getLogger(AutoRequest.class.getName());

    private static final Config config = new Config();

    // ft credit etz reloadable card
    public String[] creditAccount(double amount, String ben, String source, String narration, String ref,
            String channel) {
        String[] res = new String[3];
        // res[2] = id;
        try {
            // String source = config.getProperty("CardNumber");
            String pin = config.getProperty("CardPin");
            String expiry = config.getProperty("CardExpiry");
            String reference = ref;
            Card card = new Card();
            card.setCardNumber(source);
            card.setCardExpiration(expiry);
            card.setAccountType("CA");
            card.setCardPin(pin);

            XRequest request = new XRequest();
            request.setCard(card);
            request.setTransCode(TransCode.NOTIFICATION);
            request.setReference(reference);
            request.setTransAmount(amount);
            request.setMerchantCode(ben);
            // request.setOtherReference(ref);
            request.setFee(0.00);
            request.setDescription(narration);
            request.setChannelId(channel);
            request.setCurrency("566");

            XProcessor processor = new XProcessor();
            XResponse xResponse = processor.process(AutoSwitchInfo.getHttpHost(), request);
            if (xResponse == null) {
                res[0] = "-1";
                res[1] = (reference);
            } else if (xResponse.getResponse() != 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            } else if (xResponse.getResponse() == 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            }
            try {
                log.info("AutoSwitch XProcessor " + (xResponse == null ? "" : xResponse.toString()));
                log.info("AutoSwitch XProcessor message " + (xResponse == null ? "" : xResponse.getMessage()));
                log.info("AutoSwitch Response Code: " + (xResponse == null ? "" : xResponse.getResponse()));
                log.info("AutoSwitch Response Code: " + (xResponse == null ? "" : xResponse.getResponse()));

                log.info("AutoSwitch Message : " + (xResponse == null ? "" : xResponse.getMessage()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } catch (Exception ef) {
            log.error(ef.getMessage(), ef);
        }
        return res;
    }
    
        public String[] fundsTransfer(double amount, String ben, String source, String narration, String ref,
            String channel, String pin, String bankCode) {
        String[] res = new String[3];
        // res[2] = id;
        try {
            // String source = config.getProperty("CardNumber");
//            String pin = config.getProperty("CardPin");
//            String expiry = config.getProperty("CardExpiry");
            String reference = ref;
            Card card = new Card();
            card.setCardNumber(source);
            card.setCardExpiration("122028");
            card.setAccountType("CA");
            card.setCardPin(pin);

            XRequest request = new XRequest();
            request.setCard(card);
            request.setTransCode(TransCode.EFT);
            request.setReference(reference);
            request.setTransAmount(amount);
            request.setMerchantCode(ben);
            // request.setOtherReference(ref);
            request.setFee(0.00);
            request.setDescription(narration);
            request.setChannelId(channel);
            request.setCurrency("566");
           

            XProcessor processor = new XProcessor();
            XResponse xResponse = processor.process(AutoSwitchInfo.getHttpHost(), request);
            if (xResponse == null) {
                res[0] = "-1";
                res[1] = (reference);
            } else if (xResponse.getResponse() != 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            } else if (xResponse.getResponse() == 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            }
            try {
                log.info("AutoSwitch XProcessor " + (xResponse == null ? "" : xResponse.toString()));
                log.info("AutoSwitch XProcessor message " + (xResponse == null ? "" : xResponse.getMessage()));
                log.info("AutoSwitch Response Code: " + (xResponse == null ? "" : xResponse.getResponse()));
                log.info("AutoSwitch Response Code: " + (xResponse == null ? "" : xResponse.getResponse()));

                log.info("AutoSwitch Message : " + (xResponse == null ? "" : xResponse.getMessage()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } catch (Exception ef) {
            log.error(ef.getMessage(), ef);
        }
        return res;
    }

    public String[] statusCheck(String ref, String cardNumber, String channel) {
        String[] res = new String[3];
        // res[2] = id;
        try {
            // props.load(new FileInputStream(new File("C:\\cfg\\merchantpay.properties")));
            // String source = config.getProperty("CardNumber");
            String pin = config.getProperty("CARD_PIN");
            String expiry = config.getProperty("CARD_EXPIRATION");
            // log.info("CardPin:"+ pin);
            String reference = ref;
            Card card = new Card();
            card.setCardNumber(cardNumber);
            card.setCardExpiration(expiry);
            // card.setAccountType("CA");
            card.setCardPin(pin);

            XRequest request = new XRequest();
            request.setCard(card);
            request.setTransCode(TransCode.BANKSERVICE);
            request.setReference(reference);
            request.setXmlString("<CBARequest>SC:" + reference + "</CBARequest>");
            // request.setOtherReference("EUGENETEST001");
            request.setDescription("Status Check for " + reference);
            request.setChannelId(channel);
            request.setCurrency("566");

            // log.info("AutoSwitch R: ");
            XProcessor processor = new XProcessor();
            XResponse xResponse = processor.process(AutoSwitchInfo.getHttpHost(), request);

            // log.info("AutoSwitch XProcessor " + (xResponse == null ? "" :
            // xResponse.toString()));
            log.info("AutoSwitch Response Code: " + (xResponse == null ? "" : xResponse.getResponse()));
            log.info("Transaction Code: " + (xResponse == null ? "" : xResponse.getCustomXml()));
            log.info("Transaction Code: " + (xResponse == null ? "" : xResponse.getCustomXml()));

            if (xResponse == null) {
                res[0] = "-1";
                res[1] = (reference);
            } else if (xResponse.getResponse() != 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                res[2] = xResponse.getCustomXml();
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            } else if (xResponse.getResponse() == 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                res[2] = xResponse.getCustomXml();
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            }
        } catch (Exception ef) {
            log.error(ef.getMessage(), ef);
        }
        return res;
    }

    public String[] validatePin(String ref, String cardNumber, String channel, String cardpin) {
        String[] res = new String[3];
        // res[2] = id;
        try {
            // props.load(new FileInputStream(new File("C:\\cfg\\merchantpay.properties")));
            // String source = config.getProperty("CardNumber");
            String pin = config.getProperty("CARD_PIN");
            String expiry = config.getProperty("CARD_EXPIRATION");
            // log.info("CardPin:"+ pin);
            String reference = ref;
            Card card = new Card();
            card.setCardNumber(cardNumber);
            card.setCardExpiration(expiry);
            // card.setAccountType("CA");
            card.setCardPin(cardpin);

            XRequest request = new XRequest();
            request.setCard(card);
            request.setTransCode(TransCode.VERIFYCARD);
            request.setReference(reference);
            // request.setXmlString("<CBARequest>PAUTH:" + cardpin + "</CBARequest>");
            // request.setOtherReference("EUGENETEST001");
            request.setDescription("Pin Check for " + cardNumber);
            request.setChannelId(channel);
            request.setCurrency("566");

            // log.info("AutoSwitch R: ");
            XProcessor processor = new XProcessor();
            XResponse xResponse = processor.process(AutoSwitchInfo.getHttpHost(), request);

            // log.info("AutoSwitch XProcessor " + (xResponse == null ? "" :
            // xResponse.toString()));
            log.info("AutoSwitch Response Code: " + (xResponse == null ? "" : xResponse.getResponse()));
            log.info("Transaction Code: " + (xResponse == null ? "" : xResponse.getCustomXml()));
            log.info("Transaction Code: " + (xResponse == null ? "" : xResponse.getCustomXml()));

            if (xResponse == null) {
                res[0] = "-1";
                res[1] = (reference);
            } else if (xResponse.getResponse() != 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                res[2] = xResponse.getCustomXml();
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            } else if (xResponse.getResponse() == 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                res[2] = xResponse.getCustomXml();
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            }
        } catch (Exception ef) {
            log.error(ef.getMessage(), ef);
        }
        return res;
    }

    public String[] validateAccount(String cardNumber) {
        String[] res = {"", ""};
        // res[2] = id;
        try {
            // props.load(new FileInputStream(new File("C:\\cfg\\merchantpay.properties")));
            // String source = config.getProperty("CardNumber");
            String pin = config.getProperty("CARD_PIN");
            String expiry = config.getProperty("CARD_EXPIRATION");
            // log.info("CardPin:"+ pin);
            // String reference = ref;
            Card card = new Card();
            card.setCardNumber(cardNumber);
            card.setCardExpiration(expiry);
            // card.setAccountType("CA");
            card.setCardPin(pin);

            XRequest request = new XRequest();
            request.setCard(card);
            request.setTransCode(TransCode.BANKSERVICE);
            // request.setReference(reference);
            request.setXmlString("<CBARequest>AL</CBARequest>");
            // request.setOtherReference("EUGENETEST001");
            // request.setDescription("Status Check for " + reference);
            // request.setChannelId(channel);
            // request.setCurrency("566");

            XProcessor processor = new XProcessor();
            XResponse xResponse = processor.process(AutoSwitchInfo.getHttpHost(), request);

            // log.info("AutoSwitch XProcessor " + (xResponse == null ? "" :
            // xResponse.toString()));
            log.info("AutoSwitch Response Code: " + (xResponse == null ? "" : xResponse.getResponse()));
            log.info("Transaction Code: " + (xResponse == null ? "" : xResponse.getCustomXml()));

            if (xResponse == null) {
                res[0] = "-1";
            } else if (xResponse.getResponse() != 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = xResponse.getCustomXml();
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            } else if (xResponse.getResponse() == 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = xResponse.getCustomXml();
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            }

        } catch (Exception ef) {
            log.error(ef.getMessage(), ef);
            // log.info("");
        }
        return res;
    }

    // public String[] doReversal(String cardNum, String cardExp, String
    // uniqueTransId, String merchantCode, String descr, String transNo, double
    // amount, String channel, String clientRef) {
    // String message = "";
    // String[] res = new String[3];
    // try {
    // Card card = new Card();
    // card.setCardNumber(cardNum);
    // card.setCardExpiration(cardExp);
    //
    // XRequest xrq = new XRequest();
    // xrq.setCard(card);
    // xrq.setTransCode(TransCode.REVERSAL);
    // xrq.setReference(uniqueTransId);
    // xrq.setTransAmount(amount);
    // xrq.setMerchantCode(merchantCode);
    // xrq.setDescription(descr);
    // xrq.setChannelId(channel);
    // xrq.setTransno(transNo);
    // xrq.setOtherReference(clientRef);
    //
    // log.info("transNo " + transNo);
    //
    // XResponse xResponse = null;
    // XProcessor processor = new XProcessor();
    // xResponse = processor.process(AutoSwitchInfo.getHttpHost(), xrq);
    // try {
    // log.info("AutoSwitch XProcessor " + (xResponse == null ? "" :
    // xResponse.toString()));
    // log.info("AutoSwitch XProcessor message " + (xResponse == null ? "" :
    // xResponse.getMessage()));
    // log.info("AutoSwitch Response Code: " + (xResponse == null ? "" :
    // xResponse.getResponse()));
    // log.info("Transaction Code: " + (xResponse == null ? "" :
    // xResponse.getCustomXml()));
    //
    // } catch (Exception e) {
    // log.error(e.getMessage(),e);
    // }
    //
    // if (xResponse != null) {
    // log.info("XProcessor message " + xResponse.getMessage());
    // log.info("response code " + xResponse.getResponse());
    // log.info("Reference " + xResponse.getReference());
    // message = "" + xResponse.getResponse();
    // }
    //
    // if (xResponse == null) {
    // res[0] = "-1";
    //// res[1] = (xResponse.getReference());
    // } else if (xResponse.getResponse() != 0) {
    // res[0] = ("" + xResponse.getResponse());
    // res[1] = (xResponse.getReference());
    //// res[1] = (getErrorDesc(new
    // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
    // Ref:" + id);
    // } else if (xResponse.getResponse() == 0) {
    // res[0] = ("" + xResponse.getResponse());
    // res[1] = (xResponse.getReference());
    // res[2] = xResponse.getCustomXml();
    //// res[1] = (getErrorDesc(new
    // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
    // Ref:" + id);
    // }
    //
    // } catch (Exception ex) {
    // log.error(ex.getMessage(), ex);
    // message = "Server error occured";
    // }
    // return res;
    // }
    public String[] doAutoSwitchReversal(String cardNum, String cardExp, String uniqueTransId, String merchantCode,
            String descr, String transNo, double amount, String channel, String node) {
        String message = "";
        String[] res = new String[3];
        res[0] = "-1";
        res[1] = uniqueTransId;
        try {
            Card card = new Card();
            card.setCardNumber(cardNum);
            card.setCardExpiration(cardExp);

            XRequest xrq = new XRequest();
            xrq.setCard(card);
            xrq.setTransCode(TransCode.REVERSAL);
            xrq.setReference(uniqueTransId);
            xrq.setTransAmount(amount);
            xrq.setMerchantCode(merchantCode);
            xrq.setDescription(descr);
            xrq.setChannelId(channel);
            xrq.setTransno(transNo);

            XResponse xResponse = null;
            XProcessor processor = new XProcessor();
            xResponse = processor.process(AutoSwitchInfo.getHttpHost(node), xrq);
            if (xResponse != null) {
                log.info("XProcessor message " + xResponse.getMessage());
                log.info("response code " + xResponse.getResponse());
                log.info("Reference " + xResponse.getReference());
                message = "" + xResponse.getResponse();
            }
            if (xResponse == null) {
                res[0] = "-1";
                // res[1] = (xResponse.getReference());
            } else if (xResponse.getResponse() != 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (xResponse.getReference());
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            } else if (xResponse.getResponse() == 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (xResponse.getReference());
                res[2] = xResponse.getCustomXml();
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            res[2] = "Server error occured";
        }
        return res;
    }

    public String[] doReprocess(String cardNum, String cardExp, String uniqueTransId, String merchantCode, String descr,
            String transNo, double amount, String channel) {
        String message = "";
        String[] res = new String[3];
        try {
            Card card = new Card();
            card.setCardNumber(cardNum);
            card.setCardExpiration(cardExp);

            XRequest xrq = new XRequest();
            xrq.setCard(card);
            xrq.setTransCode(TransCode.NOTIFICATION);
            xrq.setReference(uniqueTransId);
            xrq.setTransAmount(amount);
            xrq.setMerchantCode(merchantCode);
            xrq.setDescription(descr);
            xrq.setChannelId(channel);
            xrq.setTransno(transNo);

            log.info("transNo " + transNo);

            XResponse xResponse = null;
            XProcessor processor = new XProcessor();
            xResponse = processor.process(AutoSwitchInfo.getHttpHost(), xrq);
            log.info("XProcessor message " + xResponse.getMessage());
            log.info("response code " + xResponse.getResponse());
            log.info("Reference " + xResponse.getReference());
            message = "" + xResponse.getResponse();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            message = "Server error occured";
        }
        return res;
    }

    public String getAPL(String phone, String bankCode) {

        // DoHTTPRequest.get2("http://172.16.30.6:8670/gipverify?bankcode=" + bankCode +
        // "&phone=" + phone);
        long st = System.currentTimeMillis();
        String returnValue = "";
        log.info("APL: " + config.getProperty("VERIFY_ACCOUNT_URL") + "?bankcode=" + bankCode + "&phone=" + phone);
        String json = DoHTTPRequest
                .get2(config.getProperty("VERIFY_ACCOUNT_URL") + "?bankcode=" + bankCode + "&phone=" + phone);
        log.info("APL RESPONSE: " + config.getProperty("VERIFY_ACCOUNT_URL") + "?bankcode=" + bankCode + "&phone="
                + phone + " --- " + json);

        JSONObject js = new JSONObject(json);

        if (js.optString("responseCode", "06").equals("00")) {
            returnValue = js.optString("lookup");
        }

        // return returnValue;
        // String expiryDate = "0000";
        //
        // XProcessor processor = new XProcessor();
        //
        // Card card = new Card();
        //// card.setCardNumber(String.format("021%s", accountNumber));
        // card.setCardNumber(bankCode + phone);
        //
        // card.setCardExpiration(expiryDate);
        // XRequest request = new XRequest();
        //// request.setXmlString("<CBARequest>ISGHS</CBARequest>");
        // request.setXmlString("<CBARequest>APL:" + phone + "</CBARequest>");
        // request.setCard(card);
        // request.setTransCode(TransCode.BANKSERVICE);
        //
        // XResponse response = null;
        // long st = 0;
        // try {
        // st = System.currentTimeMillis();
        // response = processor.process(AutoSwitchInfo.getHttpHost(), request);
        //
        // log.info("Response: " + response.getResponse());
        //// log.info("Message: " + response.getMessage());
        //// log.info("Custom XML: " + response.getCustomXml());
        //// log.info("Reference: " + response.getReference());
        //
        // if (response.getResponse() == 0) {
        // returnValue = response.getCustomXml();
        // }
        //// log.info("RT: "+ returnValue);
        // } catch (Exception e) {
        // e.log.error(ex.getMessage(), ex);
        // }
        log.info("TAT: " + (System.currentTimeMillis() - st));

        return returnValue;
    }

    public String[] doPayment(double amount, String ben, String source, String narration, String ref, String channel) {
        String[] res = new String[3];
        // res[2] = id;
        try {
            // String source = config.getProperty("CardNumber");
            String pin = config.getProperty("BULK_PROCESS_PIN");
            String expiry = config.getProperty("BULK_PROCESS_EXPIRY");
            String cardNumber = config.getProperty("BULK_PROCESS_CARD");
            String reference = ref;
            Card card = new Card();
            card.setCardNumber(cardNumber);
            card.setCardExpiration(expiry);
            card.setAccountType("CA");
            card.setCardPin(pin);

            XRequest request = new XRequest();
            request.setCard(card);
            request.setTransCode(TransCode.NOTIFICATION);
            request.setReference(reference);
            request.setTransAmount(amount);
            request.setMerchantCode(ben);
            // request.setOtherReference(ref);
            request.setFee(0.00);
            request.setDescription(narration);
            request.setChannelId(channel);
            request.setCurrency("566");

            XProcessor processor = new XProcessor();
            XResponse xResponse = processor.process(AutoSwitchInfo.getHttpHost(), request);
            if (xResponse == null) {
                res[0] = "-1";
                res[1] = (reference);
            } else if (xResponse.getResponse() != 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            } else if (xResponse.getResponse() == 0) {
                res[0] = ("" + xResponse.getResponse());
                res[1] = (reference);
                // res[1] = (getErrorDesc(new
                // StringBuilder().append("").append(xResponse.getResponse()).toString()) + "
                // Ref:" + id);
            }
            try {
                log.info("AutoSwitch XProcessor " + (xResponse == null ? "" : xResponse.toString()));
                log.info("AutoSwitch XProcessor message " + (xResponse == null ? "" : xResponse.getMessage()));
                log.info("AutoSwitch Response Code: " + (xResponse == null ? "" : xResponse.getResponse()));
                log.info("AutoSwitch Response Code: " + (xResponse == null ? "" : xResponse.getResponse()));

                log.info("AutoSwitch Message : " + (xResponse == null ? "" : xResponse.getMessage()));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } catch (Exception ef) {
            log.error(ef.getMessage(), ef);
        }
        return res;
    }

    public static void main(String args[]) {
        try {
            // log.info("AUTOREQUEST: " + Arrays.toString(new AutoRequest().creditBank(0.01,
            // "0046011010155856", "TestMPaySettlement007", "")));
            // log.info("AUTOREQUEST: " + new
            // AutoRequest().isBOAAccountGHS("005140203832950"));
            System.out.println(
                    "AUTOREQUEST: " + Arrays.toString(new AutoRequest().validateAccount("0049996011010155856")));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().validateAccount("0051402038329501")));

            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("AQA6EP0hqZWQQBBWjbzI", "005123456789098", "02")));
            // //success
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("dRzOH7sVm4YbcdD7S6z7", "005123456789098", "02")));
            // //success
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("02QL91646992922", "005123456789098", "02")));
            // //FAILED
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("02XX2021011110341764", "005123456789098", "02")));
            // //FAILED
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("02XX2021012615005177", "9050008472213100",
            // "02")));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("02LK53526DF5083", "0210008472213100", "02")));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("02XX2021020414031921", "9050008472213100",
            // "02")));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("02XX2021020414184364", "0050008472213100",
            // "02")));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("02ON40094929127", "0210008472213100", "02")));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().statusCheck("ArFqCXty7oedfP2cXbGdJUyfgD/KSkx69kUyNY+UZFFFEibEzB0mqzd3b/wxJjHPXSZC+iI/Mjj191iXLbuyUTNCeES279WOSCyX1j7rMixvaBbvso8Dy/gATihJS873+HKgCGjZPD4ShS9g9OSwEPkq5caXFE20AerS7DGVRWzn+X7VJnekUkMZg0/nsjXmdOZ3SAp55p/uexRWKSYt0Ybr9yFF4muTsbHS/V1R0mRAs5j5T6GcBsRyS6jdVU5n0fdc0FLBN4JvCwoN6c3qP8VaXuyEz+Gt7HXciMy41aweDDlh5CiBOaVLv8+r7Vj8vBDs5zmAfy+oMfc28lVVhw==",
            // "0050008472213100", "02")));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().validatePin("testingpin9232", "0050010000000054", "02", "")));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().validatePin("testingpin222", "0050010000000054", "02",
            // "NGDeV8hehuZHvRZAS+SX0lr7FMigKLcRuTxM0t5SveMgYCVATMPdbaCARSiV2ERoxqlnhLfVGEW2/E89lTvPNsqpaFcHvFnBSMS6ENy9CIoZjMMgD1i5XikiXSw98GzgO41Fjs1Ws90xcuCb6tYVBsaqugsywKSdtmYIKkHFJX/OUCBhM9mHrPv56DItNFMBaJpLi3bLUMKVWYMTuYQEueiIdpZlfsdE/cFIoWq1B+jpTl59V8X9hx94LqaoBP+dQnustHx5vRf7YJJX3Zo3xA1E+idjB9qVerQ2IJ3WSUnNySKqurp5Q64o7+Zewuys1oaW4e/r0eTmPPkHjz7vMA==")));
            // double amount, String ben, String source, String narration, String ref,
            // String channel
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().creditAccount(0.01, "9050008472213100", "686233241234567",
            // "Test Bestpoint 0900", "testbestpoint000wo9", "02")));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().creditAccount(0.01, "0068860474250000", "686233241234567",
            // "Test Seth 0900", "testseth05wo95", "02")));
            System.out
                    .println("AUTOREQUEST: " + Arrays.toString(new AutoRequest().validateAccount("0050000092313100")));
            // System.out.println("AUTOREQUEST: " + new AutoRequest().getAPL("233543646331",
            // "005"));
            // new AutoRequest().changePin();

            // log.info("AUTOREQUEST: " + new AutoRequest().getAPL("233507889445"));
            // log.info("AUTOREQUEST: " + Arrays.toString(new
            // AutoRequest().validateAccount("0050010000000161")));
        } catch (Exception ed) {
            log.error(ed.getMessage(), ed);
            log.info("AUTOREQUEST Error: " + ed);
        }
    }
}
