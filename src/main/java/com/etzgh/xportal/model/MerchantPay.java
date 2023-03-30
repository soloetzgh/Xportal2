package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class MerchantPay implements Serializable {

    private static final long serialVersionUID = 2400890589031059640L;

    @Id
    private String etz_reference;
    private String payment_type;
    private String mobile_no;
    private String transid;
    private String merchant;
    private BigDecimal amount;
    private BigDecimal fee;
    private String status;
    private String trans_status;
    private String status_msg;
    private String name;
    private String bank;
    private String reference;
    private String product;
    private String extra_info;
    private String trans_date;
    private String network;
    private String settlementStatus;
    private String settlementMessage;
    private String settlementRef;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public String getSettlementMessage() {
        return settlementMessage;
    }

    public void setSettlementMessage(String settlementMessage) {
        this.settlementMessage = settlementMessage;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getEtz_reference() {
        return etz_reference;
    }

    public void setEtz_reference(String etz_reference) {
        this.etz_reference = etz_reference;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getSettlementRef() {
        return settlementRef;
    }

    public void setSettlementRef(String settlementRef) {
        this.settlementRef = settlementRef;
    }

    @Override
    public String toString() {
        return "MerchantPay{" + "etz_reference=" + etz_reference + ", payment_type=" + payment_type + ", mobile_no=" + mobile_no + ", transid=" + transid + ", merchant=" + merchant + ", amount=" + amount + ", fee=" + fee + ", status=" + status + ", trans_status=" + trans_status + ", status_msg=" + status_msg + ", name=" + name + ", bank=" + bank + ", reference=" + reference + ", product=" + product + ", extra_info=" + extra_info + ", trans_date=" + trans_date + ", network=" + network + ", settlementStatus=" + settlementStatus + ", settlementMessage=" + settlementMessage + ", settlementRef=" + settlementRef + '}';
    }

}
