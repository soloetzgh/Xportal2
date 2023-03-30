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
import java.util.ArrayList;
import java.util.List;

public class ProviderStatistics {

    private String responseCode;
    private String message;
    private int totalCount;
    private List<SmsProvStatObj> smsProvStatObjs = new ArrayList<>();

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<SmsProvStatObj> getSmsProvStatObjs() {
        return smsProvStatObjs;
    }

    public void setSmsProvStatObjs(List<SmsProvStatObj> smsProvStatObjs) {
        this.smsProvStatObjs = smsProvStatObjs;
    }

    public class SmsProvStatObj {

        private int id;
        private String provider;
        private String totalCount;
        private String totalDeliver;
        private String totalFailed;
        private String totalPending;
        private String connectionStatus;
        private int upTime;
        private int downTime;
        private Double successRate;
        private Double pendingRate;
        private Double failureRate;
        private String dateCreated;
        private String dateUpdated;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getTotalDeliver() {
            return totalDeliver;
        }

        public void setTotalDeliver(String totalDeliver) {
            this.totalDeliver = totalDeliver;
        }

        public String getTotalFailed() {
            return totalFailed;
        }

        public void setTotalFailed(String totalFailed) {
            this.totalFailed = totalFailed;
        }

        public String getTotalPending() {
            return totalPending;
        }

        public void setTotalPending(String totalPending) {
            this.totalPending = totalPending;
        }

        public String getConnectionStatus() {
            return connectionStatus;
        }

        public void setConnectionStatus(String connectionStatus) {
            this.connectionStatus = connectionStatus;
        }

        public Integer getUpTime() {
            return upTime;
        }

        public void setUpTime(Integer upTime) {
            this.upTime = upTime;
        }

        public Integer getDownTime() {
            return downTime;
        }

        public void setDownTime(Integer downTime) {
            this.downTime = downTime;
        }

        public Double getSuccessRate() {
            return successRate;
        }

        public void setSuccessRate(Double successRate) {
            this.successRate = successRate;
        }

        public Double getPendingRate() {
            return pendingRate;
        }

        public void setPendingRate(Double pendingRate) {
            this.pendingRate = pendingRate;
        }

        public Double getFailureRate() {
            return failureRate;
        }

        public void setFailureRate(Double failureRate) {
            this.failureRate = failureRate;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(String dateCreated) {
            this.dateCreated = dateCreated;
        }

        public String getDateUpdated() {
            return dateUpdated;
        }

        public void setDateUpdated(String dateUpdated) {
            this.dateUpdated = dateUpdated;
        }

        @Override
        public String toString() {
            return "SmsProvStatObj{" + "id=" + id + ", provider=" + provider + ", totalCount=" + totalCount + ", totalDeliver=" + totalDeliver + ", totalFailed=" + totalFailed + ", totalPending=" + totalPending + ", connectionStatus=" + connectionStatus + ", upTime=" + upTime + ", downTime=" + downTime + ", successRate=" + successRate + ", pendingRate=" + pendingRate + ", failureRate=" + failureRate + ", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated + '}';
        }

    }

    @Override
    public String toString() {
        return "ProviderStatistics{" + "responseCode=" + responseCode + ", message=" + message + ", totalCount=" + totalCount + ", smsProvStatObjs=" + smsProvStatObjs + '}';
    }
}
