/**
 *
 */
package com.etzgh.xportal.model;

import java.io.Serializable;

/**
 * @author sunkwa-arthur
 *
 */
public class User implements Serializable {

    private static final long serialVersionUID = -3633764010880238388L;

    private String user_id;
    private String email;
    private String firstname;
    private String lastname;
    private String username;
    private String type_id;

    private String mobile;
    private String type_desc;
    private String status_id;
    private String user_code;
    private String branchCode;
    private String first_logon;
    private String service_id;
    private String cardScheme;
    private String cardSchemeNumbers;
    private String role_id;
    private String admin;
    private String cms_role;
    private String cop_issuer_code;
    private String cop_role_id;
    private String cop_company_id;
    private String temp_password;
    private String firstRoute;
    private String bankCode;
    private Boolean requires2FA;
    private Boolean isLoggedIn;
    private String company;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
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

    public String getType_desc() {
        return type_desc;
    }

    public void setType_desc(String type_desc) {
        this.type_desc = type_desc;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
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

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
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

    public String getCms_role() {
        return cms_role;
    }

    public void setCms_role(String cms_role) {
        this.cms_role = cms_role;
    }

    public String getCop_issuer_code() {
        return cop_issuer_code;
    }

    public void setCop_issuer_code(String cop_issuer_code) {
        this.cop_issuer_code = cop_issuer_code;
    }

    public String getCop_role_id() {
        return cop_role_id;
    }

    public void setCop_role_id(String cop_role_id) {
        this.cop_role_id = cop_role_id;
    }

    public String getCop_company_id() {
        return cop_company_id;
    }

    public void setCop_company_id(String cop_company_id) {
        this.cop_company_id = cop_company_id;
    }

    public String getTemp_password() {
        return temp_password;
    }

    public void setTemp_password(String temp_password) {
        this.temp_password = temp_password;
    }

    public String getFirstRoute() {
        return firstRoute;
    }

    public void setFirstRoute(String firstRoute) {
        this.firstRoute = firstRoute;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Boolean getRequires2FA() {
        return requires2FA;
    }

    public void setRequires2FA(Boolean requires2FA) {
        this.requires2FA = requires2FA;
    }

    public Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "User{" + "user_id=" + user_id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username + ", type_id=" + type_id + ", mobile=" + mobile + ", type_desc=" + type_desc + ", status_id=" + status_id + ", user_code=" + user_code + ", branchCode=" + branchCode + ", first_logon=" + first_logon + ", service_id=" + service_id + ", cardScheme=" + cardScheme + ", cardSchemeNumbers=" + cardSchemeNumbers + ", role_id=" + role_id + ", admin=" + admin + ", cms_role=" + cms_role + ", cop_issuer_code=" + cop_issuer_code + ", cop_role_id=" + cop_role_id + ", cop_company_id=" + cop_company_id + ", temp_password=" + temp_password + ", firstRoute=" + firstRoute + ", bankCode=" + bankCode + ", requires2FA=" + requires2FA + ", isLoggedIn=" + isLoggedIn + ", company=" + company + '}';
    }

}
