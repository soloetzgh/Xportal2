package com.etzgh.xportal.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sunkwa-arthur
 */
public class BulkVerifyResponse {

    private String response;
    private String responseMsg;
    private List<NetworkList> networkList = new ArrayList<>();

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public List<NetworkList> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(List<NetworkList> networkList) {
        this.networkList = networkList;
    }

    public class NetworkList {

        private String msisdn;
        private String network;
        private String networkCode;
        private String name;

        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public String getNetworkCode() {
            return networkCode;
        }

        public void setNetworkCode(String networkCode) {
            this.networkCode = networkCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "NetworkList{" + "msisdn=" + msisdn + ", network=" + network + ", networkCode=" + networkCode + ", name=" + name + '}';
        }

    }

    @Override
    public String toString() {
        return "BulkVerifyResponse{" + "response=" + response + ", responseMsg=" + responseMsg + ", networkList=" + networkList + '}';
    }

}
