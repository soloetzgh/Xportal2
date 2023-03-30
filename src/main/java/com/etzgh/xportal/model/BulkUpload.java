package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
public class BulkUpload implements Serializable {

    private static final long serialVersionUID = -7582338628425031450L;

    @Id
    private String reference;
    private String status;
    private double amount;
    private String source;
    private String source_type;
    private String transaction_status;
    private String channel;
    private String transaction_type;
    private String destination;
    private String destination_type;
    private String transaction_date;
    private String extra_info;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDestination_type() {
        return destination_type;
    }

    public void setDestination_type(String destination_type) {
        this.destination_type = destination_type;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
    }

    @Override
    public String toString() {
        return "BulkUpload{" + "reference=" + reference + ", status=" + status + ", amount=" + amount + ", source=" + source + ", source_type=" + source_type + ", transaction_status=" + transaction_status + ", channel=" + channel + ", transaction_type=" + transaction_type + ", destination=" + destination + ", destination_type=" + destination_type + ", transaction_date=" + transaction_date + ", extra_info=" + extra_info + '}';
    }

}
