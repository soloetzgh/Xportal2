package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class ExternalRequest implements Serializable {

    private static final long serialVersionUID = -8103711296540457156L;

    @Id
    private int id;
    private String req_data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReq_data() {
        return req_data;
    }

    public void setReq_data(String req_data) {
        this.req_data = req_data;
    }

    @Override
    public String toString() {
        return "ExternalRequest{" + "id=" + id + ", req_data=" + req_data + '}';
    }

}
