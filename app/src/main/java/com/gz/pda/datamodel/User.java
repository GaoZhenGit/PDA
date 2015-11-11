package com.gz.pda.datamodel;

import java.io.Serializable;
import java.util.List;

/**
 * user as single user
 * Created by host on 2015/11/5.
 */
public class User implements Serializable{
    private String phone;
    private String username;
    private String password;
    private String detail;
    private List<TimeTable> timeTables;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<TimeTable> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }
}
