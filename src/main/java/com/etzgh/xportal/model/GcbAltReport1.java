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
public class GcbAltReport1 implements Serializable {

    private static final long serialVersionUID = -23875934903046774L;

    @Id
    private String unique_TRANSID;
    private String created;
    private String de102;
    private String de123;
    private String de39;
    private String de4;
    private String de37;
    private String de103;
    private String de28;

    public String getUnique_TRANSID() {
        return unique_TRANSID;
    }

    public void setUnique_TRANSID(String unique_TRANSID) {
        this.unique_TRANSID = unique_TRANSID;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDe102() {
        return de102;
    }

    public void setDe102(String de102) {
        this.de102 = de102;
    }

    public String getDe123() {
        return de123;
    }

    public void setDe123(String de123) {
        this.de123 = de123;
    }

    public String getDe39() {
        return de39;
    }

    public void setDe39(String de39) {
        this.de39 = de39;
    }

    public String getDe37() {
        return de37;
    }

    public void setDe37(String de37) {
        this.de37 = de37;
    }

    public String getDe103() {
        return de103;
    }

    public void setDe103(String de103) {
        this.de103 = de103;
    }

    public String getDe4() {
        return de4;
    }

    public void setDe4(String de4) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.000");
        this.de4 = decimalFormat.format(Double.parseDouble(de4) / 100);
    }

    public String getDe28() {
        return de28;
    }

    public void setDe28(String de28) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.000");
        this.de28 = decimalFormat.format(Double.parseDouble(de28.replace("D", "")) / 100);
    }

    @Override
    public String toString() {
        return "GcbAltReport{" + "unique_TRANSID=" + unique_TRANSID + ", created=" + created + ", de102=" + de102 + ", de123=" + de123 + ", de39=" + de39 + ", de4=" + de4 + ", de37=" + de37 + ", de103=" + de103 + ", de28=" + de28 + '}';
    }
}
