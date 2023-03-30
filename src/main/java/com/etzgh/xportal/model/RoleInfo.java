package com.etzgh.xportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleInfo {

    private String id;
    private String name;
    private String userRole;
    @Valid
    private List<RoleOption> roleOptions;

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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<RoleOption> getRoleOptions() {
        return roleOptions;
    }

    public void setRoleOptions(List<RoleOption> roleOptions) {
        this.roleOptions = roleOptions;
    }

    @Override
    public String toString() {
        return "RoleInfo{" + "id=" + id + ", name=" + name + ", userRole=" + userRole + ", roleOptions=" + roleOptions + '}';
    }
}
