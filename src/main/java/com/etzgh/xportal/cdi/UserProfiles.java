package com.etzgh.xportal.cdi;

import com.etzgh.xportal.model.UserManagement;
import com.google.gson.Gson;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import javax.enterprise.context.SessionScoped;

/**
 * @author sunkwa-arthur
 */
@SessionScoped
public class UserProfiles implements Serializable {

    private static final long serialVersionUID = -8793642022542824398L;
    List<UserManagement> users;

    public List<UserManagement> getUsers() {
        return users;
    }

    public void setUsers(List<UserManagement> users) {
        this.users = users;
    }

    public String paginate(int pageNumber, int rowsPerPage) {

        int offset = 0;

        if (pageNumber < 1) {
            pageNumber = 1;
        }

        if (pageNumber > 1) {
            offset = (pageNumber - 1) * rowsPerPage;
        }

        int toIndex = offset + rowsPerPage;
        if (toIndex > this.users.size()) {
            toIndex = this.users.size();
        }

        return new Gson().toJson(this.users.subList(offset, toIndex));
    }

    public String getSearchData() {
        return new Gson().toJson(this.users);
    }

    public String getTotalCount() {
        return new Gson().toJson(this.users.size());
    }

    public static String currencyFormat(BigDecimal n) {

        return NumberFormat.getNumberInstance().format(n);
    }
}
