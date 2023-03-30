/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eugene
 */
@Entity
@XmlRootElement
public class FeePayment implements Serializable {

    @Id
    private String unique_transid;
    private String merchant_id;
    private BigDecimal amount;
    private String trans_date;
    private String trans_channel;
    private String trans_status;
    private String trans_note;
    private String trans_descr;
    private String issuer_code;
    private String payment_code;
    private String mobile_no;
    private BigDecimal opening_balance;
    private BigDecimal closing_balance;
    private String cheque_number;
    private String branch_code;
    private String bank_code;
    private String currency;
    private String pro_branch;
    private String account;
    private String account_number;
    private String company;
    private String flag;

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_channel() {
        return trans_channel;
    }

    public void setTrans_channel(String trans_channel) {
        this.trans_channel = trans_channel;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getTrans_note() {
        return trans_note;
    }

    public void setTrans_note(String trans_note) {
        this.trans_note = trans_note;
    }

    public String getTrans_descr() {
        return trans_descr;
    }

    public void setTrans_descr(String trans_descr) {
        this.trans_descr = trans_descr;
    }

    public String getIssuer_code() {
        return issuer_code;
    }

    public void setIssuer_code(String issuer_code) {
        this.issuer_code = issuer_code;
    }

    public String getPayment_code() {
        return payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public BigDecimal getOpening_balance() {
        return opening_balance;
    }

    public void setOpening_balance(BigDecimal opening_balance) {
        this.opening_balance = opening_balance;
    }

    public BigDecimal getClosing_balance() {
        return closing_balance;
    }

    public void setClosing_balance(BigDecimal closing_balance) {
        this.closing_balance = closing_balance;
    }

    public String getCheque_number() {
        return cheque_number;
    }

    public void setCheque_number(String cheque_number) {
        this.cheque_number = cheque_number;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPro_branch() {
        return pro_branch;
    }

    public void setPro_branch(String pro_branch) {
        this.pro_branch = pro_branch;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "FeePayment{" + "unique_transid=" + unique_transid + ", merchant_id=" + merchant_id + ", amount=" + amount + ", trans_date=" + trans_date + ", trans_channel=" + trans_channel + ", trans_status=" + trans_status + ", trans_note=" + trans_note + ", trans_descr=" + trans_descr + ", issuer_code=" + issuer_code + ", payment_code=" + payment_code + ", mobile_no=" + mobile_no + ", opening_balance=" + opening_balance + ", closing_balance=" + closing_balance + ", cheque_number=" + cheque_number + ", branch_code=" + branch_code + ", bank_code=" + bank_code + ", currency=" + currency + ", pro_branch=" + pro_branch + ", account=" + account + ", account_number=" + account_number + ", company=" + company + ", flag=" + flag + '}';
    }
}
