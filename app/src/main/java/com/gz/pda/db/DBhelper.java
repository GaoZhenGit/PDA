package com.gz.pda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.datamodel.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * create for operation of database
 * Created by host on 2015/11/11.
 */
public class DBhelper extends OrmLiteSqliteOpenHelper {
    // 数据库名称
    private static final String DATABASE_NAME = "pda.db";
    // 数据库version
    private static final int DATABASE_VERSION = 1;

    private Dao<User, Integer> userDao;
    private Dao<TimeTable, Integer> timetableDao;

    private static DBhelper instance;


    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized void init(Context context){
        instance = new DBhelper(context);
    }

    public static synchronized DBhelper getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, TimeTable.class);
            userDao = getDao(User.class);
            timetableDao = getDao(TimeTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource arg1, int arg2,
                          int arg3) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, TimeTable.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void add(User user) {
        try {
            if (userDao == null) {
                userDao = getDao(User.class);
            }
            userDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public User getUserById(int i) {
        User user = null;
        try {
            if (userDao == null) {
                userDao = getDao(User.class);
            }
            user = userDao.queryForId(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void add(TimeTable timeTable) {
        try {
            if (timetableDao == null) {
                timetableDao = getDao(TimeTable.class);
            }
            timetableDao.create(timeTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection<TimeTable> getTimeTableByUser(User user){
        try {
            if (timetableDao == null) {
                timetableDao = getDao(TimeTable.class);
            }
            Map<String,Object> q = new HashMap<>();
            q.put("user_id", user.getId() + "");
            return timetableDao.queryForFieldValues(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
