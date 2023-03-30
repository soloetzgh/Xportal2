/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

/**
 *
 * @author sunkwa-arthur
 */
public class Reversal {

    private String chn;
    private String mti;
    private String narration;
    private String custAccount;
    private String merchAccount;
    private String reference;
    private String date;
    private String resp;
    private String network;
    private double amount;
    private double fee;
    private double elevy;
    private String reversalStatus;
    private String systemRef;
    private boolean processed;

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getChn() {
        return chn;
    }

    public void setChn(String chn) {
        this.chn = chn;
    }

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getCustAccount() {
        return custAccount;
    }

    public void setCustAccount(String custAccount) {
        this.custAccount = custAccount;
    }

    public String getMerchAccount() {
        return merchAccount;
    }

    public void setMerchAccount(String merchAccount) {
        this.merchAccount = merchAccount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getElevy() {
        return elevy;
    }

    public void setElevy(double elevy) {
        this.elevy = elevy;
    }

    public String getReversalStatus() {
        return reversalStatus;
    }

    public void setReversalStatus(String reversalStatus) {
        this.reversalStatus = reversalStatus;
    }

    public String getSystemRef() {
        return systemRef;
    }

    public void setSystemRef(String systemRef) {
        this.systemRef = systemRef;
    }

    @Override
    public String toString() {
        return "Reversal{" + "chn=" + chn + ", mti=" + mti + ", narration=" + narration + ", custAccount=" + custAccount + ", merchAccount=" + merchAccount + ", reference=" + reference + ", date=" + date + ", resp=" + resp + ", network=" + network + ", amount=" + amount + ", fee=" + fee + ", elevy=" + elevy + ", reversalStatus=" + reversalStatus + ", systemRef=" + systemRef + ", processed=" + processed + '}';
    }

}
