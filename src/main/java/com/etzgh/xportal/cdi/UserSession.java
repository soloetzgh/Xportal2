package com.etzgh.xportal.cdi;

import com.etzgh.xportal.model.User;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sunkwa-arthur
 */
public class UserSession implements Serializable {

    private static final long serialVersionUID = 541986100826785816L;
    User user;
    int loginAttempts;
    Date created;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
