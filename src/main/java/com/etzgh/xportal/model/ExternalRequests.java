package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ExternalRequests implements Serializable {

    @Id
    private int id;
    private String unique_ref;
    private String msisdn;
    private String merchant_name;
    private String req_data;
    private String req_status;
    private String req_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnique_ref() {
        return unique_ref;
    }

    public void setUnique_ref(String unique_ref) {
        this.unique_ref = unique_ref;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getReq_data() {
        return req_data;
    }

    public void setReq_data(String req_data) {
        this.req_data = req_data;
    }

    public String getReq_status() {
        return req_status;
    }

    public void setReq_status(String req_status) {
        this.req_status = req_status;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    @Override
    public String toString() {
        return "ExternalRequests{" + "id=" + id + ", unique_ref=" + unique_ref + ", msisdn=" + msisdn + ", merchant_name=" + merchant_name + ", req_data=" + req_data + ", req_status=" + req_status + ", req_date=" + req_date + '}';
    }

}
