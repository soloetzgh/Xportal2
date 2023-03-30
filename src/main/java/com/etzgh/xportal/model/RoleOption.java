package com.etzgh.xportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleOption implements Serializable {

    private static final long serialVersionUID = -6094992579215987181L;

    @Id
    private String alias;
    private String name;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleOption{" + "name=" + name + ", alias=" + alias + '}';
    }

}
