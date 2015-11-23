package com.gz.pda.datamodel;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * user as single user
 * Created by host on 2015/11/5.
 */
@DatabaseTable(tableName = "user")
public class User implements Serializable{

    @DatabaseField(columnName = "phone",id = true)
    @Expose
    private String phone;

    @DatabaseField(columnName = "username")
    @Expose
    private String username;

    @DatabaseField(columnName = "detail")
    @Expose
    private String detail;

    @ForeignCollectionField(columnName="timetable")
    @Expose
    private Collection<TimeTable> timeTables;

    private String password;

    private String aeskey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Collection<TimeTable> getTimeTables() {
        return timeTables;
    }

    public void setTimeTables(List<TimeTable> timeTables) {
        this.timeTables = timeTables;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAeskey() {
        return aeskey;
    }

    public void setAeskey(String aeskey) {
        this.aeskey = aeskey;
    }
}
