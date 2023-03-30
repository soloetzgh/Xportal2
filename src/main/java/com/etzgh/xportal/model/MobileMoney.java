package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class MobileMoney implements Serializable {

    @Id
    private String reference;
    private String client;
    private String msisdn;
    private String paymenttype;
    private BigDecimal amount;
    private String trnxdate;
    private String respcode;
    private String respcode2;
    private String cardno;
    private String channel;
    private String clientid;
    private String narration;
    private String switchcode;
    private String flag;
    private String clientcode;
    private String bank;
    private String code;
    private String mainoptions;
    private String mac;
    private String processdate;
    private String descr;
    private String verified_status;
    private String verify_resp;
    private String reverse_status;
    private String toreverse_flag;
    private String token;
    private String db_tat;
    private String txn_tat;
    private String process_attempt;
    private String ext1;
    private String ext2;
    private String ext3;
    

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTrnxdate() {
        return trnxdate;
    }

    public void setTrnxdate(String trnxdate) {
        this.trnxdate = trnxdate;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getRespcode2() {
        return respcode2;
    }

    public void setRespcode2(String respcode2) {
        this.respcode2 = respcode2;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getSwitchcode() {
        return switchcode;
    }

    public void setSwitchcode(String switchcode) {
        this.switchcode = switchcode;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getClientcode() {
        return clientcode;
    }

    public void setClientcode(String clientcode) {
        this.clientcode = clientcode;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMainoptions() {
        return mainoptions;
    }

    public void setMainoptions(String mainoptions) {
        this.mainoptions = mainoptions;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getProcessdate() {
        return processdate;
    }

    public void setProcessdate(String processdate) {
        this.processdate = processdate;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getVerified_status() {
        return verified_status;
    }

    public void setVerified_status(String verified_status) {
        this.verified_status = verified_status;
    }

    public String getVerify_resp() {
        return verify_resp;
    }

    public void setVerify_resp(String verify_resp) {
        this.verify_resp = verify_resp;
    }

    public String getReverse_status() {
        return reverse_status;
    }

    public void setReverse_status(String reverse_status) {
        this.reverse_status = reverse_status;
    }

    public String getToreverse_flag() {
        return toreverse_flag;
    }

    public void setToreverse_flag(String toreverse_flag) {
        this.toreverse_flag = toreverse_flag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDb_tat() {
        return db_tat;
    }

    public void setDb_tat(String db_tat) {
        this.db_tat = db_tat;
    }

    public String getTxn_tat() {
        return txn_tat;
    }

    public void setTxn_tat(String txn_tat) {
        this.txn_tat = txn_tat;
    }

    public String getProcess_attempt() {
        return process_attempt;
    }

    public void setProcess_attempt(String process_attempt) {
        this.process_attempt = process_attempt;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
    }


    @Override
    public String toString() {
        return "MobileMoney{" + "reference=" + reference + ", client=" + client + ", msisdn=" + msisdn + ", paymenttype=" + paymenttype + ", amount=" + amount + ", trnxdate=" + trnxdate + ", respcode=" + respcode + ", respcode2=" + respcode2 + ", cardno=" + cardno + ", channel=" + channel + ", clientid=" + clientid + ", narration=" + narration + ", switchcode=" + switchcode + ", flag=" + flag + ", clientcode=" + clientcode + ", bank=" + bank + ", code=" + code + ", mainoptions=" + mainoptions + ", mac=" + mac + ", processdate=" + processdate + ", descr=" + descr + ", verified_status=" + verified_status + ", verify_resp=" + verify_resp + ", reverse_status=" + reverse_status + ", toreverse_flag=" + toreverse_flag + ", token=" + token + ", db_tat=" + db_tat + ", txn_tat=" + txn_tat + ", process_attempt=" + process_attempt + ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3=" + ext3 + '}';
    }
    
    

}
