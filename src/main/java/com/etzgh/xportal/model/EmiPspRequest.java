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
public class EmiPspRequest {

    private String Bank;
    private String RequestId;
    private String ChargingPartyID;
    private String AccountNumber;
    private String TINorGhanaCard;
    private String AccountType;
    private String PhoneNumber;

    public String getBank() {
        return Bank;
    }

    public void setBank(String Bank) {
        this.Bank = Bank;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public String getChargingPartyID() {
        return ChargingPartyID;
    }

    public void setChargingPartyID(String ChargingPartyID) {
        this.ChargingPartyID = ChargingPartyID;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public String getTINorGhanaCard() {
        return TINorGhanaCard;
    }

    public void setTINorGhanaCard(String TINorGhanaCard) {
        this.TINorGhanaCard = TINorGhanaCard;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String AccountType) {
        this.AccountType = AccountType;
    }

    @Override
    public String toString() {
        return "EmiPspRequest{" + "Bank=" + Bank + ", RequestId=" + RequestId + ", ChargingPartyID=" + ChargingPartyID + ", AccountNumber=" + AccountNumber + ", TINorGhanaCard=" + TINorGhanaCard + ", AccountType=" + AccountType + ", PhoneNumber=" + PhoneNumber + '}';
    }

}
