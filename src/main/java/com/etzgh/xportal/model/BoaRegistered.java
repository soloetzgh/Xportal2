package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eugene
 */
@Entity
@XmlRootElement
public class BoaRegistered implements Serializable {

    private static final long serialVersionUID = 227209305565400668L;

    @Id
    private int id;
    private String mobile_no;
    private String account_no;
    private String status;
    private String created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "BoaRegistered{" + "id=" + id + ", mobile_no=" + mobile_no + ", account_no=" + account_no + ", status=" + status + ", created=" + created + '}';
    }

}
