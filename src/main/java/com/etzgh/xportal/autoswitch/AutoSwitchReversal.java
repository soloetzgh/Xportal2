package com.etzgh.xportal.autoswitch;

import com.etz.http.etc.Card;
import com.etz.http.etc.TransCode;
import com.etz.http.etc.XProcessor;
import com.etz.http.etc.XRequest;
import com.etz.http.etc.XResponse;
import org.apache.log4j.Logger;

public class AutoSwitchReversal {

    private static final Logger log = Logger.getLogger(AutoSwitchReversal.class.getName());

    public static String doReversal(String cardNum, String cardExp, String uniqueTransId, String merchantCode, String descr, String transNo, double amount, String channel, String node) {
        String message = "";

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
            log.info("XProcessor message " + xResponse.getMessage());
            log.info("response code " + xResponse.getResponse());
            log.info("Reference " + xResponse.getReference());
            message = "" + xResponse.getResponse();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            message = "Server error occured";
        }
        return message;
    }

    public static void main(String[] args) {
        throw new UnsupportedOperationException();
    }
}
