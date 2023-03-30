package com.etzgh.xportal.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author seth.sebeh
 */
public class XRandom {

    private static final Logger log = Logger.getLogger(VerifyNumber.class.getName());

    public String generateUniqueId() {
        String message = "";
        try {
            String tt = "";
            for (int s = 0; s < 7; s++) {
                tt = tt + new Random().nextInt(7);
            }
            Date d = new Date();
            DateFormat df = new SimpleDateFormat("MMddHHmmss");
            String dt = df.format(d);
            message = "0XP" + dt + tt;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return message;
    }
}
