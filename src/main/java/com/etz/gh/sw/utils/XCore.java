package com.etz.gh.sw.utils;

import org.apache.log4j.Logger;

public class XCore {

    private static final Logger log = Logger.getLogger(XCore.class.getName());

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

}
