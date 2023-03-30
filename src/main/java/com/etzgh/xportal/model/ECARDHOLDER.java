package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author sunkwa-arthur
 */
@Entity
public class ECARDHOLDER implements Serializable {

    @Id
    private String card_num;
    private String card2;
    private String Lastname;
    private String Firstname;
    private String issuer_code;
    private String sub_code;
    private String Email;
    private String Phone;
    private String Street;
    private String card_expiration;
    private String bound_work;
    private String bound_value;
    private String Birth_Date;
    private String Change_Pin;
    private String User_Hotlist;
    private String User_Locked;
    private String Pin_Missed;
    private String Last_Used;
    private String Modified;
    private String Created;
    private BigDecimal Online_Balance;
    private String PayFee;
    private String CASHWTHDW_LIMIT;
    private String Client_ID;
    private String city;
    private String fax;
    private String company;
    private String active;
    private String AppId;
    private String AppName;
    private String control_id;
    private String num_modified_count;

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getCard2() {
        return card2;
    }

    public void setCard2(String card2) {
        this.card2 = card2;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String Lastname) {
        this.Lastname = Lastname;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String AppName) {
        this.AppName = AppName;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String Firstname) {
        this.Firstname = Firstname;
    }

    public String getIssuer_code() {
        return issuer_code;
    }

    public void setIssuer_code(String issuer_code) {
        this.issuer_code = issuer_code;
    }

    public String getSub_code() {
        return sub_code;
    }

    public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getCard_expiration() {
        return card_expiration;
    }

    public void setCard_expiration(String card_expiration) {
        this.card_expiration = card_expiration;
    }

    public String getBound_work() {
        return bound_work;
    }

    public void setBound_work(String bound_work) {
        this.bound_work = bound_work;
    }

    public String getBound_value() {
        return bound_value;
    }

    public void setBound_value(String bound_value) {
        this.bound_value = bound_value;
    }

    public String getBirth_Date() {
        return Birth_Date;
    }

    public void setBirth_Date(String Birth_Date) {
        this.Birth_Date = Birth_Date;
    }

    public String getChange_Pin() {
        return Change_Pin;
    }

    public void setChange_Pin(String Change_Pin) {
        this.Change_Pin = Change_Pin;
    }

    public String getUser_Hotlist() {
        return User_Hotlist;
    }

    public void setUser_Hotlist(String User_Hotlist) {
        this.User_Hotlist = User_Hotlist;
    }

    public String getUser_Locked() {
        return User_Locked;
    }

    public void setUser_Locked(String User_Locked) {
        this.User_Locked = User_Locked;
    }

    public String getPin_Missed() {
        return Pin_Missed;
    }

    public void setPin_Missed(String Pin_Missed) {
        this.Pin_Missed = Pin_Missed;
    }

    public String getLast_Used() {
        return Last_Used;
    }

    public void setLast_Used(String Last_Used) {
        this.Last_Used = Last_Used;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String Modified) {
        this.Modified = Modified;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String Created) {
        this.Created = Created;
    }

    public BigDecimal getOnline_Balance() {
        return Online_Balance;
    }

    public void setOnline_Balance(BigDecimal Online_Balance) {
        this.Online_Balance = Online_Balance;
    }

    public String getPayFee() {
        return PayFee;
    }

    public void setPayFee(String PayFee) {
        this.PayFee = PayFee;
    }

    public String getCASHWTHDW_LIMIT() {
        return CASHWTHDW_LIMIT;
    }

    public void setCASHWTHDW_LIMIT(String CASHWTHDW_LIMIT) {
        this.CASHWTHDW_LIMIT = CASHWTHDW_LIMIT;
    }

    public String getClient_ID() {
        return Client_ID;
    }

    public void setClient_ID(String Client_ID) {
        this.Client_ID = Client_ID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String AppId) {
        this.AppId = AppId;
    }

    public String getControl_id() {
        return control_id;
    }

    public void setControl_id(String control_id) {
        this.control_id = control_id;
    }

    public String getNum_modified_count() {
        return num_modified_count;
    }

    public void setNum_modified_count(String num_modified_count) {
        this.num_modified_count = num_modified_count;
    }

    @Override
    public String toString() {
        return "ECARDHOLDER{" + "card_num=" + card_num + ", card2=" + card2 + ", Lastname=" + Lastname + ", Firstname=" + Firstname + ", issuer_code=" + issuer_code + ", sub_code=" + sub_code + ", Email=" + Email + ", Phone=" + Phone + ", Street=" + Street + ", card_expiration=" + card_expiration + ", bound_work=" + bound_work + ", bound_value=" + bound_value + ", Birth_Date=" + Birth_Date + ", Change_Pin=" + Change_Pin + ", User_Hotlist=" + User_Hotlist + ", User_Locked=" + User_Locked + ", Pin_Missed=" + Pin_Missed + ", Last_Used=" + Last_Used + ", Modified=" + Modified + ", Created=" + Created + ", Online_Balance=" + Online_Balance + ", PayFee=" + PayFee + ", CASHWTHDW_LIMIT=" + CASHWTHDW_LIMIT + ", Client_ID=" + Client_ID + ", city=" + city + ", fax=" + fax + ", company=" + company + ", active=" + active + ", AppId=" + AppId + ", AppName=" + AppName + ", control_id=" + control_id + ", num_modified_count=" + num_modified_count + '}';
    }

}
