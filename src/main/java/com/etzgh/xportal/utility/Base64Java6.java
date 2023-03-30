package com.etzgh.xportal.utility;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import javax.xml.bind.DatatypeConverter;

public class Base64Java6 {

    private static final Logger log = Logger.getLogger(Base64Java6.class.getName());

    private Base64Java6() {
    }

    public static String encode(String value) throws Exception {
        return DatatypeConverter.printBase64Binary(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String encode(byte[] value) throws Exception {
        return DatatypeConverter.printBase64Binary(value);
    }

    public static String decode(String value) throws Exception {
        byte[] decodedValue = DatatypeConverter.parseBase64Binary(value);
        return new String(decodedValue, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String test = "try this howto";

        String res1 = Base64Java6.encode(test);
        log.info(test + " base64 -> " + res1);

        String res2 = Base64Java6.decode(res1);
        log.info(res1 + " string --> " + res2);

        Pattern p = Pattern.compile("^GHA-\\d{9}-\\d{1}");
        System.out.println("MATCH:: " + p.matcher("GHA-002-00").find());
        System.out.println("MATCH:: " + p.matcher("GHA-003-00").find());
        System.out.println("MATCH:: " + p.matcher("GH-003-00").find());


        /*
       * output
       *  try this howto base64 -> dHJ5IHRoaXMgaG93dG8=
       *  dHJ5IHRoaXMgaG93dG8= string --> try t
         */
    }
}
