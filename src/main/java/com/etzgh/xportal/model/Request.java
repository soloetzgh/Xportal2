/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

public class Request {

    String reference;
    String bankCode;
    String paymentType;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String banckCode) {
        this.bankCode = banckCode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "Request{" + "reference=" + reference + ", bankCode=" + bankCode + ", paymentType=" + paymentType + '}';
    }
}
