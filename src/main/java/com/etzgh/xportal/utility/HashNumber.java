package com.etzgh.xportal.utility;

import org.apache.log4j.Logger;

/**
 * @author tony.ezeanya
 *
 */
public class HashNumber {

    private static final Logger log = Logger.getLogger(HashNumber.class.getName());

    public HashNumber() {
    }

    public static void main(String[] args) {

        String str = "1234567890987654321";

        System.out.println(maskString(str, 3, 4, '*'));
    }

    public static String maskString(String strText, int start, int end, char maskChar) {
        if (strText == null || strText.equals("")) {
            return "";
        }

        if (start < 1) {
            start = 1;
        }

        if (end > strText.length()) {
            end = strText.length();
        }

        int maskLength = strText.length() - (end + start);

        if (maskLength == 0) {
            return strText;
        }

        StringBuilder sbMaskString = new StringBuilder(maskLength);

        for (int i = 0; i < maskLength; i++) {
            sbMaskString.append(maskChar);
        }

        return strText.substring(0, start)
                + sbMaskString.toString()
                + strText.substring(start + sbMaskString.toString().length(), strText.length());
    }


    /*This method shows the first six characters and the last four*/
    public String hashStringValue(String value, int start, int end) {
        String message = "";

        try {
            message = value.substring(0, start) + "######" + value.substring(value.length() - end);
        } catch (Exception ex) {
            message = value;
            log.error(ex.getMessage(), ex);
        }
        return message;
    }

    public String hashStringValue(String value, int start, int end, String delimiter) {
        String message = "";

        try {
            message = value.substring(0, start) + delimiter + delimiter + delimiter + delimiter + delimiter + value.substring(value.length() - end);
        } catch (Exception ex) {
            message = value;
            log.error(ex.getMessage(), ex);
        }
        return message;
    }

    public String hashAccountValue(String value, int start, int end) {
        String message = "";

        try {
            message = value.substring(0, start) + "XXXXX" + value.substring(value.length() - end);
        } catch (Exception ex) {
            message = value;
            log.error(ex.getMessage(), ex);
        }
        return message;
    }

    /*This method hashes the first six characters */
    public String hashStringValue2(String value, int end) {
        String message = "";
        try {
            message = "######" + value.substring(6, value.length());
            log.info("message " + message);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return message;
    }

    /*This method hashes the specified last value characters */
    public String hashSpecifiedLastValue(String value, int end) {
        String message = "";
        try {
            message = value.substring(0, value.length() - end) + "####";

        } catch (Exception ex) {
            // Do Nothing
            log.error(ex.getMessage(), ex);

        }
        return message;
    }

}
