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
public class PortalSettingsData implements Serializable {

    private static final long serialVersionUID = -1171230224066620369L;

    @Id
    private String id;
    private String name;
    private String description;
    private String current_value;
    private String key_name;
    private String status;
    private String type_1;
    private String type_2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(String current_value) {
        this.current_value = current_value;
    }

    public String getKey_name() {
        return key_name;
    }

    public void setKey_name(String key_name) {
        this.key_name = key_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType_1() {
        return type_1;
    }

    public void setType_1(String type_1) {
        this.type_1 = type_1;
    }

    public String getType_2() {
        return type_2;
    }

    public void setType_2(String type_2) {
        this.type_2 = type_2;
    }

    @Override
    public String toString() {
        return "PortalSettingsData{" + "id=" + id + ", name=" + name + ", description=" + description + ", current_value=" + current_value + ", key_name=" + key_name + ", status=" + status + ", type_1=" + type_1 + ", type_2=" + type_2 + '}';
    }

}
