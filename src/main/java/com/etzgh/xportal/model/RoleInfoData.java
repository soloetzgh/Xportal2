package com.etzgh.xportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sunkwa-arthur
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleInfoData {

    private List<RoleInfo> roleInfo;
    private List<NODES> typeIdList;

    public List<RoleInfo> getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(List<RoleInfo> roleInfo) {
        this.roleInfo = roleInfo;
    }

    public List<NODES> getTypeIdList() {
        return typeIdList;
    }

    public void setTypeIdList(List<NODES> typeIdList) {
        this.typeIdList = typeIdList;
    }

    @Override
    public String toString() {
        return "RoleInfoData{" + "roleInfo=" + roleInfo + ", typeIdList=" + typeIdList + '}';
    }

}
