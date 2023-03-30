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
public class EmiPsp extends EmiPspRequest {

    private String RequestDate;
    private String RequestStatus;
    private String RequestError;
    private String RequestErrorMsg;
    private String ProcessedDate;

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String RequestDate) {
        this.RequestDate = RequestDate;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(String RequestStatus) {
        this.RequestStatus = RequestStatus;
    }

    public String getRequestError() {
        return RequestError;
    }

    public void setRequestError(String RequestError) {
        this.RequestError = RequestError;
    }

    public String getRequestErrorMsg() {
        return RequestErrorMsg;
    }

    public void setRequestErrorMsg(String RequestErrorMsg) {
        this.RequestErrorMsg = RequestErrorMsg;
    }

    public String getProcessedDate() {
        return ProcessedDate;
    }

    public void setProcessedDate(String ProcessedDate) {
        this.ProcessedDate = ProcessedDate;
    }

    @Override
    public String toString() {
        return "EmiPsp{" + "RequestDate=" + RequestDate + ", RequestStatus=" + RequestStatus + ", RequestError=" + RequestError + ", RequestErrorMsg=" + RequestErrorMsg + ", ProcessedDate=" + ProcessedDate + '}';
    }

}
