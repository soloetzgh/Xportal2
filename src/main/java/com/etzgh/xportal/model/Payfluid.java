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
public class Payfluid {

    private String txn_ref;
    private String client_ref;
    private String paygw_authref;
    private String pay_mthd;
    private String pay_scheme;
    private String pay_msisdn;
    private String pay_transid;
    private String pay_instr;
    private String payee_name;
    private String pay_descr;
    private String payee_email;
    private String payee_phone;
    private double pay_amt;
    private double fee;
    private String pay_merchnm;
    private String pay_status;
    private String pay_statusmsg;
    private String created;

    public String getTxn_ref() {
        return txn_ref;
    }

    public void setTxn_ref(String txn_ref) {
        this.txn_ref = txn_ref;
    }

    public String getClient_ref() {
        return client_ref;
    }

    public void setClient_ref(String client_ref) {
        this.client_ref = client_ref;
    }

    public String getPaygw_authref() {
        return paygw_authref;
    }

    public void setPaygw_authref(String paygw_authref) {
        this.paygw_authref = paygw_authref;
    }

    public String getPay_mthd() {
        return pay_mthd;
    }

    public void setPay_mthd(String pay_mthd) {
        this.pay_mthd = pay_mthd;
    }

    public String getPay_scheme() {
        return pay_scheme;
    }

    public void setPay_scheme(String pay_scheme) {
        this.pay_scheme = pay_scheme;
    }

    public String getPay_msisdn() {
        return pay_msisdn;
    }

    public void setPay_msisdn(String pay_msisdn) {
        this.pay_msisdn = pay_msisdn;
    }

    public String getPay_transid() {
        return pay_transid;
    }

    public void setPay_transid(String pay_transid) {
        this.pay_transid = pay_transid;
    }

    public String getPay_instr() {
        return pay_instr;
    }

    public void setPay_instr(String pay_instr) {
        this.pay_instr = pay_instr;
    }

    public String getPayee_name() {
        return payee_name;
    }

    public void setPayee_name(String payee_name) {
        this.payee_name = payee_name;
    }

    public String getPay_descr() {
        return pay_descr;
    }

    public void setPay_descr(String pay_descr) {
        this.pay_descr = pay_descr;
    }

    public String getPayee_email() {
        return payee_email;
    }

    public void setPayee_email(String payee_email) {
        this.payee_email = payee_email;
    }

    public String getPayee_phone() {
        return payee_phone;
    }

    public void setPayee_phone(String payee_phone) {
        this.payee_phone = payee_phone;
    }

    public double getPay_amt() {
        return pay_amt;
    }

    public void setPay_amt(double pay_amt) {
        this.pay_amt = pay_amt;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getPay_merchnm() {
        return pay_merchnm;
    }

    public void setPay_merchnm(String pay_merchnm) {
        this.pay_merchnm = pay_merchnm;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_statusmsg() {
        return pay_statusmsg;
    }

    public void setPay_statusmsg(String pay_statusmsg) {
        this.pay_statusmsg = pay_statusmsg;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Payfluid{" + "txn_ref=" + txn_ref + ", client_ref=" + client_ref + ", paygw_authref=" + paygw_authref + ", pay_mthd=" + pay_mthd + ", pay_scheme=" + pay_scheme + ", payee_name=" + payee_name + ", pay_descr=" + pay_descr + ", payee_email=" + payee_email + ", payee_phone=" + payee_phone + ", pay_amt=" + pay_amt + ", fee=" + fee + ", pay_merchnm=" + pay_merchnm + ", pay_status=" + pay_status + ", pay_statusmsg=" + pay_statusmsg + ", created=" + created + '}';
    }

}
