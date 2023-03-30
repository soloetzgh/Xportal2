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
import java.io.Serializable;
import java.math.BigDecimal;

public class GCBMerchantPay implements Serializable {

    private static final long serialVersionUID = 6586865684986830654L;
    private String merchant;
    private BigDecimal trans_amount;
    private String batch_id;
    private String dest_acct;
    private String src_acct;
    private String client;
    private String extra_info;
    private String unique_transid;

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public BigDecimal getTrans_amount() {
        return trans_amount;
    }

    public void setTrans_amount(BigDecimal trans_amount) {
        this.trans_amount = trans_amount;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getDest_acct() {
        return dest_acct;
    }

    public void setDest_acct(String dest_acct) {
        this.dest_acct = dest_acct;
    }

    public String getSrc_acct() {
        return src_acct;
    }

    public void setSrc_acct(String src_acct) {
        this.src_acct = src_acct;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }

    public String getUnique_transid() {
        return unique_transid;
    }

    public void setUnique_transid(String unique_transid) {
        this.unique_transid = unique_transid;
    }

    @Override
    public String toString() {
        return "GCBMerchantPay{" + "merchant=" + merchant + ", trans_amount=" + trans_amount + ", batch_id=" + batch_id + ", dest_acct=" + dest_acct + ", src_acct=" + src_acct + ", client=" + client + ", extra_info=" + extra_info + ", unique_transid=" + unique_transid + '}';
    }

}
