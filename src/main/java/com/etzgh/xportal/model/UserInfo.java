/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

    private String firstName;
    private String lastName;
    private String mobile;
    private String company;
    private String email;
    private String bankCode;
    private String branchCode;
    private String newUsername;
    private String userId;
    private String require2FA;
    private List<userOption> userOptions;

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequire2FA() {
        return require2FA;
    }

    public void setRequire2FA(String require2FA) {
        this.require2FA = require2FA;
    }

    public List<userOption> getUserOptions() {
        return userOptions;
    }

    public void setUserOptions(List<userOption> userOptions) {
        this.userOptions = userOptions;
    }

    public static class userOption {

        private String userOption0;
        private String userOption1;
        private String userOption2;

        public userOption() {
        }

        public String getUserOption0() {
            return userOption0;
        }

        public void setUserOption0(String userOption0) {
            this.userOption0 = userOption0;
        }

        public String getUserOption1() {
            return userOption1;
        }

        public void setUserOption1(String userOption1) {
            this.userOption1 = userOption1;
        }

        public String getUserOption2() {
            return userOption2;
        }

        public void setUserOption2(String userOption2) {
            this.userOption2 = userOption2;
        }

        @Override
        public String toString() {
            return "userOption{" + "userOption0=" + userOption0 + ", userOption1=" + userOption1 + ", userOption2=" + userOption2 + '}';
        }

    }

    @Override
    public String toString() {
        return "UserInfo{" + "firstName=" + firstName + ", lastName=" + lastName + ", mobile=" + mobile + ", company=" + company + ", email=" + email + ", bankCode=" + bankCode + ", newUsername=" + newUsername + ", userId=" + userId + ", require2FA=" + require2FA + ", userOptions=" + userOptions + '}';
    }

}
