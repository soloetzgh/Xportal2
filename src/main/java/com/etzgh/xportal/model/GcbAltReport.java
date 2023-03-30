package com.etzgh.xportal.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@XmlRootElement
@Entity
public class GcbAltReport implements Serializable {

    private static final long serialVersionUID = -23875934903046774L;

    @Id
    private String UNIQUE_TRANSID;
    private String CREATED;
    private String DE102;
    private String DE123;
    private String DE39;
    private String DE4;
    private String DE37;
    private String DE103;
    private String DE28;

    public String getUNIQUE_TRANSID() {
        return UNIQUE_TRANSID;
    }

    public void setUNIQUE_TRANSID(String UNIQUE_TRANSID) {
        this.UNIQUE_TRANSID = UNIQUE_TRANSID;
    }

    public String getCREATED() {
        return CREATED;
    }

    public void setCREATED(String CREATED) {
        this.CREATED = CREATED;
    }

    public String getDE102() {
        return DE102;
    }

    public void setDE102(String DE102) {
        this.DE102 = DE102;
    }

    public String getDE123() {
        return DE123;
    }

    public void setDE123(String DE123) {
        this.DE123 = DE123;
    }

    public String getDE39() {
        return DE39;
    }

    public void setDE39(String DE39) {
        this.DE39 = DE39;
    }

    public String getDE4() {
        return DE4;
    }

    public void setDE4(String DE4) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.000");
        this.DE4 = decimalFormat.format(Double.parseDouble(DE4) / 100);
    }

    public String getDE37() {
        return DE37;
    }

    public void setDE37(String DE37) {
        this.DE37 = DE37;
    }

    public String getDE103() {
        return DE103;
    }

    public void setDE103(String DE103) {
        this.DE103 = DE103;
    }

    public String getDE28() {
        return DE28;
    }

    public void setDE28(String DE28) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.000");
        this.DE28 = decimalFormat.format(Double.parseDouble(DE28.replace("D", "")) / 100);
    }

    @Override
    public String toString() {
        return "GcbAltReport{" + "UNIQUE_TRANSID=" + UNIQUE_TRANSID + ", CREATED=" + CREATED + ", DE102=" + DE102 + ", DE123=" + DE123 + ", DE39=" + DE39 + ", DE4=" + DE4 + ", DE37=" + DE37 + ", DE103=" + DE103 + ", DE28=" + DE28 + '}';
    }
}
