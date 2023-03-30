package com.etzgh.xportal.model;

import java.sql.Timestamp;

public class TAlertLog {

    private char MessageType;
    private String SourceAddr;
    private String DestAddr;
    private String MessageHeader;
    private String MessageText;
    private Timestamp MessageDate;
    private char ProcessStatus;
    private String InitiatedBy;
    private String StatusCode;
    private Timestamp ProcessedDate;
    private int Attempts;
    private String UniqueTransId;

    public char getMessageType() {
        return MessageType;
    }

    public void setMessageType(char messageType) {
        MessageType = messageType;
    }

    public String getSourceAddr() {
        return SourceAddr;
    }

    public void setSourceAddr(String sourceAddr) {
        SourceAddr = sourceAddr;
    }

    public String getDestAddr() {
        return DestAddr;
    }

    public void setDestAddr(String destAddr) {
        DestAddr = destAddr;
    }

    public String getMessageHeader() {
        return MessageHeader;
    }

    public void setMessageHeader(String messageHeader) {
        MessageHeader = messageHeader;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public Timestamp getMessageDate() {
        return MessageDate;
    }

    public void setMessageDate(Timestamp messageDate) {
        MessageDate = messageDate;
    }

    public char getProcessStatus() {
        return ProcessStatus;
    }

    public void setProcessStatus(char processStatus) {
        ProcessStatus = processStatus;
    }

    public String getInitiatedBy() {
        return InitiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        InitiatedBy = initiatedBy;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public Timestamp getProcessedDate() {
        return ProcessedDate;
    }

    public void setProcessedDate(Timestamp processedDate) {
        ProcessedDate = processedDate;
    }

    public int getAttempts() {
        return Attempts;
    }

    public void setAttempts(int attempts) {
        Attempts = attempts;
    }

    public String getUniqueTransId() {
        return UniqueTransId;
    }

    public void setUniqueTransId(String uniqueTransId) {
        UniqueTransId = uniqueTransId;
    }

    @Override
    public String toString() {
        return "TAlertLog{" + "MessageType=" + MessageType + ", SourceAddr=" + SourceAddr + ", DestAddr=" + DestAddr + ", MessageHeader=" + MessageHeader + ", MessageText=" + MessageText + ", MessageDate=" + MessageDate + ", ProcessStatus=" + ProcessStatus + ", InitiatedBy=" + InitiatedBy + ", StatusCode=" + StatusCode + ", ProcessedDate=" + ProcessedDate + ", Attempts=" + Attempts + ", UniqueTransId=" + UniqueTransId + '}';
    }

}
