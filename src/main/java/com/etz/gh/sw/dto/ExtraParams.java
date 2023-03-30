package com.etz.gh.sw.dto;

public class ExtraParams {

    private String rsaPKeyFromGSTKNS;
    private String hmacsha_256keyFromGSTKNS;
    private String userId;

    private String pin;

    public String getRsaPKeyFromGSTKNS() {
        return rsaPKeyFromGSTKNS;
    }

    public void setRsaPKeyFromGSTKNS(String rsaPKeyFromGSTKNS) {
        this.rsaPKeyFromGSTKNS = rsaPKeyFromGSTKNS;
    }

    public String getHmacsha_256keyFromGSTKNS() {
        return hmacsha_256keyFromGSTKNS;
    }

    public void setHmacsha_256keyFromGSTKNS(String hmacsha_256keyFromGSTKNS) {
        this.hmacsha_256keyFromGSTKNS = hmacsha_256keyFromGSTKNS;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "ExtraParams [rsaPKeyFromGSTKNS=" + rsaPKeyFromGSTKNS + ", hmacsha_256keyFromGSTKNS="
                + hmacsha_256keyFromGSTKNS + ", userId=" + userId + ", pin=" + pin + "]";
    }

}
