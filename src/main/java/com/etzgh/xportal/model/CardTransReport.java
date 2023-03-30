/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

/**
 *
 * @author eugene
 */
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Eugene
 */
@Entity
@XmlRootElement
public class CardTransReport implements Serializable {

    @Id
    private String trans_no;
    private String card_num;
    private String source;
    private String merchant_code;
    private String destination;
    private String trans_descr;
    private String f_name;
    private String trans_type;
    private String trans_date;
    private String channel_name;
    private String unique_transid;
    private BigDecimal online_balance;
    private BigDecimal DR;
    private BigDecimal CR;
    private BigDecimal Balance;
    private String converted_online_balance;
    private String converted_CR;
    private String converted_DR;
    private String converted_Balance;

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getOnline_balance() {
        return online_balance;
    }

    public void setOnline_balance(BigDecimal online_balance) {
        this.online_balance = online_balance;
    }

    public BigDecimal getDR() {
        return DR;
    }

    public void setDR(BigDecimal DR) {
        this.DR = DR;
    }

    public BigDecimal getCR() {
        return CR;
    }

    public void setCR(BigDecimal CR) {
        this.CR = CR;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getMerchant_code() {
        return merchant_code;
    }

    public void setMerchant_code(String merchant_code) {
        this.merchant_code = merchant_code;
    }

    public String getTrans_descr() {
        return trans_descr;
    }

    public void setTrans_descr(String trans_descr) {
        this.trans_descr = trans_descr;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        if (f_name != null) {
            this.f_name = f_name;
        } else {
            this.f_name = "null";
        }

    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    public BigDecimal getBalance() {
        return Balance;
    }

    public void setBalance(BigDecimal Balance) {
        this.Balance = Balance;
    }

    public String getConverted_online_balance() {
        return converted_online_balance;
    }

    public void setConverted_online_balance(String converted_online_balance) {
        this.converted_online_balance = converted_online_balance;
    }

    public String getConverted_CR() {
        return converted_CR;
    }

    public void setConverted_CR(String converted_CR) {
        this.converted_CR = converted_CR;
    }

    public String getConverted_DR() {
        return converted_DR;
    }

    public void setConverted_DR(String converted_DR) {
        this.converted_DR = converted_DR;
    }

    public String getConverted_Balance() {
        return converted_Balance;
    }

    public void setConverted_Balance(String converted_Balance) {
        this.converted_Balance = converted_Balance;
    }

    @Override
    public String toString() {
        return "CardTransReport{" + "trans_no=" + trans_no + ", card_num=" + card_num + ", merchant_code=" + merchant_code + ", trans_descr=" + trans_descr + ", f_name=" + f_name + ", trans_date=" + trans_date + ", channel_name=" + channel_name + ", unique_transid=" + unique_transid + ", online_balance=" + online_balance + ", DR=" + DR + ", CR=" + CR + ", Balance=" + Balance + ", converted_online_balance=" + converted_online_balance + ", converted_CR=" + converted_CR + ", converted_DR=" + converted_DR + ", converted_Balance=" + converted_Balance + '}';
    }
}
