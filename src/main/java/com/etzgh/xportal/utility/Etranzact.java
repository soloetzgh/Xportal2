package com.etzgh.xportal.utility;

/**
 *
 * @author Eugene
 */
public class Etranzact {

    public Boolean validatUserCode(String userCode) {
        return userCode.contains("[0]");
    }
}
