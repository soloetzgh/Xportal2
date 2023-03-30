package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement
@Table(name = "bulk_process_log")
public class BulkUploadLog implements Serializable {

    private static final long serialVersionUID = -7582338628425031450L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String batch_id;
    private String reference;
    private String status;
    private double amount;
    private String source;
    private String source_type;
    private String transaction_status;
//    private String channel;
    private String transaction_type;
    private String destination;
    private String destination_type;
    private String transaction_date;
    private String extra_info;
    private String initiated_date;
    private String initiated_by;
    private String authorized_date;
    private String authorized_by;
    private String transaction_check;
    private int reversed;
    private int reprocessed;
    private String process_type;
    private String process_category;

    private String process_status;

    public String getProcess_category() {
        return process_category;
    }

    public void setProcess_category(String process_category) {
        this.process_category = process_category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
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

    public String getInitiated_date() {
        return initiated_date;
    }

    public void setInitiated_date(String initiated_date) {
        this.initiated_date = initiated_date;
    }

    public String getInitiated_by() {
        return initiated_by;
    }

    public void setInitiated_by(String initiated_by) {
        this.initiated_by = initiated_by;
    }

    public String getAuthorized_date() {
        return authorized_date;
    }

    public void setAuthorized_date(String authorized_date) {
        this.authorized_date = authorized_date;
    }

    public String getAuthorized_by() {
        return authorized_by;
    }

    public void setAuthorized_by(String authorized_by) {
        this.authorized_by = authorized_by;
    }

    public String getTransaction_check() {
        return transaction_check;
    }

    public void setTransaction_check(String transaction_check) {
        this.transaction_check = transaction_check;
    }

    public int getReversed() {
        return reversed;
    }

    public void setReversed(int reversed) {
        this.reversed = reversed;
    }

    public int getReprocessed() {
        return reprocessed;
    }

    public void setReprocessed(int reprocessed) {
        this.reprocessed = reprocessed;
    }

    public String getProcess_type() {
        return process_type;
    }

    public void setProcess_type(String process_type) {
        this.process_type = process_type;
    }

    public String getProcess_status() {
        return process_status;
    }

    public void setProcess_status(String process_status) {
        this.process_status = process_status;
    }

    @Override
    public String toString() {
        return "BulkUploadLog{" + "id=" + id + ", batch_id=" + batch_id + ", reference=" + reference + ", status=" + status + ", amount=" + amount + ", source=" + source + ", source_type=" + source_type + ", transaction_status=" + transaction_status + ", transaction_type=" + transaction_type + ", destination=" + destination + ", destination_type=" + destination_type + ", transaction_date=" + transaction_date + ", extra_info=" + extra_info + ", initiated_date=" + initiated_date + ", initiated_by=" + initiated_by + ", authorized_date=" + authorized_date + ", authorized_by=" + authorized_by + ", transaction_check=" + transaction_check + ", reversed=" + reversed + ", reprocessed=" + reprocessed + ", process_type=" + process_type + ", process_category=" + process_category + ", process_status=" + process_status + '}';
    }

}
