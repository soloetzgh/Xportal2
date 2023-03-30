package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@XmlRootElement
@Entity
public class E_CMS implements Serializable {

    private static final long serialVersionUID = 8864093469246881420L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String card_num;
    private String card2;
    private String Lastname;
    private String Firstname;
    private String issuer_code;
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
    private String action;
    private String reason;
    private String date_initiated;
    private String date_authorized;
    private String authorized_by;
    private String status;
    private String initiated_by;

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

    public String getDate_initiated() {
        return date_initiated;
    }

    public void setDate_initiated(String date_initiated) {
        this.date_initiated = date_initiated;
    }

    public String getDate_authorized() {
        return date_authorized;
    }

    public void setDate_authorized(String date_authorized) {
        this.date_authorized = date_authorized;
    }

    public String getAuthorized_by() {
        return authorized_by;
    }

    public void setAuthorized_by(String authorized_by) {
        this.authorized_by = authorized_by;
    }

    public String getInitiated_by() {
        return initiated_by;
    }

    public void setInitiated_by(String initiated_by) {
        this.initiated_by = initiated_by;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "E_CMS{" + "id=" + id + ", card_num=" + card_num + ", card2=" + card2 + ", Lastname=" + Lastname + ", Firstname=" + Firstname + ", issuer_code=" + issuer_code + ", Email=" + Email + ", Phone=" + Phone + ", Street=" + Street + ", card_expiration=" + card_expiration + ", bound_work=" + bound_work + ", bound_value=" + bound_value + ", Birth_Date=" + Birth_Date + ", Change_Pin=" + Change_Pin + ", User_Hotlist=" + User_Hotlist + ", User_Locked=" + User_Locked + ", Pin_Missed=" + Pin_Missed + ", Last_Used=" + Last_Used + ", Modified=" + Modified + ", Created=" + Created + ", Online_Balance=" + Online_Balance + ", PayFee=" + PayFee + ", CASHWTHDW_LIMIT=" + CASHWTHDW_LIMIT + ", Client_ID=" + Client_ID + ", city=" + city + ", fax=" + fax + ", company=" + company + ", action=" + action + ", reason=" + reason + ", date_initiated=" + date_initiated + ", date_authorized=" + date_authorized + ", authorized_by=" + authorized_by + ", status=" + status + ", initiated_by=" + initiated_by + '}';
    }
}
