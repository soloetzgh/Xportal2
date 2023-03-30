/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

/**
 *
 * @author sunkwa-arthur
 */
public class BankApplication {

    private String reference;
    private String phoneNumber;
    private String bank;
    private String bankCode;
    private String firstName;
    private String otherNames;
    private String lastName;
    private String dob;
    private String idType;
    private String idNumber;
    private String status;
    private String idImage;
    private String gender;
    private String ghPostAddress;
    private String homeAddress;
    private String requestDate;
    private String accountType;
    private String processedDate;
    private String processedBy;
    private String closestBranch;
    private String email;

    public String getClosestBranch() {
        return closestBranch;
    }

    public void setClosestBranch(String closestBranch) {
        this.closestBranch = closestBranch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(String processedDate) {
        this.processedDate = processedDate;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGhPostAddress() {
        return ghPostAddress;
    }

    public void setGhPostAddress(String ghPostAddress) {
        this.ghPostAddress = ghPostAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "BankApplication{" + "reference=" + reference + ", phoneNumber=" + phoneNumber + ", bank=" + bank + ", bankCode=" + bankCode + ", firstName=" + firstName + ", otherNames=" + otherNames + ", lastName=" + lastName + ", dob=" + dob + ", idType=" + idType + ", idNumber=" + idNumber + ", status=" + status + ", idImage=" + idImage + ", gender=" + gender + ", ghPostAddress=" + ghPostAddress + ", homeAddress=" + homeAddress + ", requestDate=" + requestDate + ", accountType=" + accountType + ", processedDate=" + processedDate + ", processedBy=" + processedBy + ", closestBranch=" + closestBranch + ", email=" + email + '}';
    }

}
