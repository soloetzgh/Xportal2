package com.etzgh.xportal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@Entity
@XmlRootElement

public class CscLog implements Serializable {

    @Id
    private String reference;
    private String source_account;
    private String dest_account;
    private String dest_type;
    private BigDecimal amount;
    private String trans_status;
    private String trans_date;
    private String is_reprocessed;
    private String reprocess_reference;
    private String is_reversed;
    private String status;
    private String status_description;
    private String process_date;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSource_account() {
        return source_account;
    }

    public void setSource_account(String source_account) {
        this.source_account = source_account;
    }

    public String getDest_account() {
        return dest_account;
    }

    public void setDest_account(String dest_account) {
        this.dest_account = dest_account;
    }

    public String getDest_type() {
        return dest_type;
    }

    public void setDest_type(String dest_type) {
        this.dest_type = dest_type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTrans_status() {
        return trans_status;
    }

    public void setTrans_status(String trans_status) {
        this.trans_status = trans_status;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getIs_reprocessed() {
        return is_reprocessed;
    }

    public void setIs_reprocessed(String is_reprocessed) {
        this.is_reprocessed = is_reprocessed;
    }

    public String getReprocess_reference() {
        return reprocess_reference;
    }

    public void setReprocess_reference(String reprocess_reference) {
        this.reprocess_reference = reprocess_reference;
    }

    public String getIs_reversed() {
        return is_reversed;
    }

    public void setIs_reversed(String is_reversed) {
        this.is_reversed = is_reversed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_description() {
        return status_description;
    }

    public void setStatus_description(String status_description) {
        this.status_description = status_description;
    }

    public String getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = process_date;
    }

    @Override
    public String toString() {
        return "CscLog{" + "reference=" + reference + ", source_account=" + source_account + ", dest_account=" + dest_account + ", dest_type=" + dest_type + ", amount=" + amount + ", trans_status=" + trans_status + ", trans_date=" + trans_date + ", is_reprocessed=" + is_reprocessed + ", reprocess_reference=" + reprocess_reference + ", is_reversed=" + is_reversed + ", status=" + status + ", status_description=" + status_description + ", process_date=" + process_date + '}';
    }

}
