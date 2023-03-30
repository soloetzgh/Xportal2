package com.etzgh.xportal.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class MenuOptions implements Serializable {

    private static final long serialVersionUID = 2811636946220874410L;

    @Id
    private String name;
    private String routePath;
    private String templateUrl;
    private String controller;
    private String menu;
    private String showOnMenu;
    private String icon;
    private String accessLevel;
    private String open;
    private String openTo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getShowOnMenu() {
        return showOnMenu;
    }

    public void setShowOnMenu(String showOnMenu) {
        this.showOnMenu = showOnMenu;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getOpenTo() {
        return openTo;
    }

    public void setOpenTo(String openTo) {
        this.openTo = openTo;
    }

    @Override
    public String toString() {
        return "MenuOptions{" + "name=" + name + ", routePath=" + routePath + ", templateUrl=" + templateUrl + ", controller=" + controller + ", menu=" + menu + ", showOnMenu=" + showOnMenu + ", icon=" + icon + ", accessLevel=" + accessLevel + ", open=" + open + ", openTo=" + openTo + '}';
    }

}
