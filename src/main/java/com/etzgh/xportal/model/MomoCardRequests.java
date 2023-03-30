package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class MomoCardRequests implements Serializable {

    @Id
    private int id;
    private String mobile_no;
    private String name;
    private String enquiry;
    private String location;
    private String request_date;
    private String treated_date;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(String enquiry) {
        this.enquiry = enquiry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public String getTreated_date() {
        return treated_date;
    }

    public void setTreated_date(String treated_date) {
        this.treated_date = treated_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MomoCardRequests{" + "mobile_no=" + mobile_no + ", enquiry=" + enquiry + ", location=" + location + ", request_date=" + request_date + ", treated_date=" + treated_date + ", status=" + status + '}';
    }

}
