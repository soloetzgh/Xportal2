package com.etzgh.xportal.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class CorporatePayMerchant {

    @Id
    private String company_id;
    private String company_name;

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String toString() {
        return "CorporatePayMerchant{" + "company_id=" + company_id + ", company_name=" + company_name + '}';
    }

}
