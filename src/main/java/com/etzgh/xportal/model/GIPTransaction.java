package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
public class GIPTransaction implements Serializable {

    private static final long serialVersionUID = 4683794409743784499L;

    @Id
    private String etzRef;
    private String amount;
    private String accountDebit;
    private String accountCredit;
    private String actCode;
    private String nameToCredit;
    private String datetime;
    private String sessionId;
    
    private String trackingnum;
    private String functioncode;
    private String originebank;
    private String destbank;
    private String sessionid;
    private String channelcode;
    private String nametodebit;
    private String accounttodebit;
    private String nametocredit;
    private String accounttocredit;
    private String narration;
    private String actcode;
    private String aprvcode;
    private String reprocessed;
    private String trans_status;
    private String process_attempt;
    private String created;
    private String updated;
    private String mac;
    private String source;
    private String qrcode;
    private String resp_code;

    public String getEtzRef() {
        return etzRef;
    }

    public void setEtzRef(String etzRef) {
        this.etzRef = etzRef;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccountDebit() {
        return accountDebit;
    }

    public void setAccountDebit(String accountDebit) {
        this.accountDebit = accountDebit;
    }

    public String getAccountCredit() {
        return accountCredit;
    }

    public void setAccountCredit(String accountCredit) {
        this.accountCredit = accountCredit;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public String getNameToCredit() {
        return nameToCredit;
    }

    public void setNameToCredit(String nameToCredit) {
        this.nameToCredit = nameToCredit;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTrackingnum() {
        return trackingnum;
    }

    public void setTrackingnum(String trackingnum) {
        this.trackingnum = trackingnum;
    }

    public String getFunctioncode() {
        return functioncode;
    }

    public void setFunctioncode(String functioncode) {
        this.functioncode = functioncode;
    }

    public String getOriginebank() {
        return originebank;
    }

    public void setOriginebank(String originebank) {
        this.originebank = originebank;
    }

    public String getDestbank() {
        return destbank;
    }

    public void setDestbank(String destbank) {
        this.destbank = destbank;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getChannelcode() {
        return channelcode;
    }

    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode;
    }

    public String getNametodebit() {
        return nametodebit;
    }

    public void setNametodebit(String nametodebit) {
        this.nametodebit = nametodebit;
    }

    public String getAccounttodebit() {
        return accounttodebit;
    }

    public void setAccounttodebit(String accounttodebit) {
        this.accounttodebit = accounttodebit;
    }

    public String getNametocredit() {
        return nametocredit;
    }

    public void setNametocredit(String nametocredit) {
        this.nametocredit = nametocredit;
    }

    public String getAccounttocredit() {
        return accounttocredit;
    }

    public void setAccounttocredit(String accounttocredit) {
        this.accounttocredit = accounttocredit;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getActcode() {
        return actcode;
    }

    public void setActcode(String actcode) {
        this.actcode = actcode;
    }

    public String getAprvcode() {
        return aprvcode;
    }

    public void setAprvcode(String aprvcode) {
        this.aprvcode = aprvcode;
    }

    public String getReprocessed() {
        return reprocessed;
    }

    public void setReprocessed(String reprocessed) {
        this.reprocessed = reprocessed;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getProcess_attempt() {
        return process_attempt;
    }

    public void setProcess_attempt(String process_attempt) {
        this.process_attempt = process_attempt;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getResp_code() {
        return resp_code;
    }

    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
    }

    @Override
    public String toString() {
        return "GIPTransaction{" + "etzRef=" + etzRef + ", amount=" + amount + ", accountDebit=" + accountDebit + ", accountCredit=" + accountCredit + ", actCode=" + actCode + ", nameToCredit=" + nameToCredit + ", datetime=" + datetime + ", sessionId=" + sessionId + ", trackingnum=" + trackingnum + ", functioncode=" + functioncode + ", originebank=" + originebank + ", destbank=" + destbank + ", sessionid=" + sessionid + ", channelcode=" + channelcode + ", nametodebit=" + nametodebit + ", accounttodebit=" + accounttodebit + ", nametocredit=" + nametocredit + ", accounttocredit=" + accounttocredit + ", narration=" + narration + ", actcode=" + actcode + ", aprvcode=" + aprvcode + ", reprocessed=" + reprocessed + ", trans_status=" + trans_status + ", process_attempt=" + process_attempt + ", created=" + created + ", updated=" + updated + ", mac=" + mac + ", source=" + source + ", qrcode=" + qrcode + ", resp_code=" + resp_code + '}';
    }
    
    

}
