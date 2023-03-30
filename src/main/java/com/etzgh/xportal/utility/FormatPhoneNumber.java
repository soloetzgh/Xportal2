package com.etzgh.xportal.utility;

import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class FormatPhoneNumber {

    private static final Logger log = Logger.getLogger(FormatPhoneNumber.class.getName());

    public String formatNumber(String number) {
        try {
            if (number != null && !number.equals("")) {
                String start = number.substring(0, 4);
                if (start.startsWith("0") && number.length() == 10) {
                    number = "233" + number.substring(1, number.length());
                } else if (start.startsWith("233") && number.length() == 12) {

                } else if (start.startsWith("2330") && number.length() == 13) {
                    number = number.replaceFirst("0", "");
                } else {
                    number = "null";
                }
            }
        } catch (Exception ex) {
            log.info("Format Number Error ::: " + ex);
            number = "null";
        }
        return number;
    }
}
