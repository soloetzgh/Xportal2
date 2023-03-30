package com.etz.gh.sw.dto;

public class Headers {

    private String signature;
    private String hash;
    private String userId;
    private String pin;

    public Headers() {
        super();

    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
        return "Headers [signature=" + signature + ", hash=" + hash + ", userId=" + userId + ", pin=" + pin + "]";
    }

}
