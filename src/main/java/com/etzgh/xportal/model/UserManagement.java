package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eugene
 */
@Entity
@XmlRootElement
public class UserManagement implements Serializable {

    private static final long serialVersionUID = -9163127405244300413L;

    @Id
    private String user_id;
    private String email;
    private String firstname;
    private String lastname;
    private String username;
    private String type_id;
    private String mobile;
    private String company;
    private String user_code;
    private String branch_code;
    private String first_logon;
    private String create_date;
    private String cardScheme;
    private String cardSchemeNumbers;
    private String role_id;
    private String Admin;
    private String last_login;
    private String deactivated_date;
    private String status_id;
    private String status_desc;
    private String require2FA;

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getFirst_logon() {
        return first_logon;
    }

    public void setFirst_logon(String first_logon) {
        this.first_logon = first_logon;
    }

    public String getCardScheme() {
        return cardScheme;
    }

    public void setCardScheme(String cardScheme) {
        this.cardScheme = cardScheme;
    }

    public String getCardSchemeNumbers() {
        return cardSchemeNumbers;
    }

    public void setCardSchemeNumbers(String cardSchemeNumbers) {
        this.cardSchemeNumbers = cardSchemeNumbers;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getAdmin() {
        return Admin;
    }

    public void setAdmin(String Admin) {
        this.Admin = Admin;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getDeactivated_date() {
        return deactivated_date;
    }

    public void setDeactivated_date(String deactivated_date) {
        this.deactivated_date = deactivated_date;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getRequire2FA() {
        return require2FA;
    }

    public void setRequire2FA(String require2FA) {
        this.require2FA = require2FA;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    @Override
    public String toString() {
        return "UserManagement{" + "user_id=" + user_id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username + ", type_id=" + type_id + ", mobile=" + mobile + ", company=" + company + ", user_code=" + user_code + ", branch_code=" + branch_code + ", first_logon=" + first_logon + ", create_date=" + create_date + ", cardScheme=" + cardScheme + ", cardSchemeNumbers=" + cardSchemeNumbers + ", role_id=" + role_id + ", Admin=" + Admin + ", last_login=" + last_login + ", deactivated_date=" + deactivated_date + ", status_id=" + status_id + ", status_desc=" + status_desc + ", require2FA=" + require2FA + '}';
    }

}
