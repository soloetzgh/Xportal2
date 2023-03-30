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
public class MobileInvestigationDetails implements Serializable {

    @Id
    private String id;
    private String message_id;
    private String appid;
    private String response_code;
    private String response_message;
    private String mobile_no;
    private String sender_id;
    private String token;
    private String created;
    private String momo_no;
    private String momo_resp;
    private String momo_ref;
    private String vt_resp;
    private String vt_no;
    private String amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMomo_no() {
        return momo_no;
    }

    public void setMomo_no(String momo_no) {
        this.momo_no = momo_no;
    }

    public String getMomo_resp() {
        return momo_resp;
    }

    public void setMomo_resp(String momo_resp) {
        this.momo_resp = momo_resp;
    }

    public String getMomo_ref() {
        return momo_ref;
    }

    public void setMomo_ref(String momo_ref) {
        this.momo_ref = momo_ref;
    }

    public String getVt_resp() {
        return vt_resp;
    }

    public void setVt_resp(String vt_resp) {
        this.vt_resp = vt_resp;
    }

    public String getVt_no() {
        return vt_no;
    }

    public void setVt_no(String vt_no) {
        this.vt_no = vt_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MobileInvestigationDetails{" + "id=" + id + ", message_id=" + message_id + ", appid=" + appid + ", response_code=" + response_code + ", response_message=" + response_message + ", mobile_no=" + mobile_no + ", sender_id=" + sender_id + ", token=" + token + ", created=" + created + ", momo_no=" + momo_no + ", momo_resp=" + momo_resp + ", momo_ref=" + momo_ref + ", vt_resp=" + vt_resp + ", vt_no=" + vt_no + ", amount=" + amount + '}';
    }

}
