package com.gz.pda.app;

/**
 * hold the constant
 * Created by host on 2015/11/5.
 */
public interface Constant {
    final class DataKey {
        public static final String SESS = "sess";
        public static final String EDIT_TYPE = "edit_type";
        public static final String EDTI_DATA = "edit_data";

        public static final String TIMETABLE = "timetable";
    }

    final class Code {
        public static final int NAME_EDIT = 0x231;
        public static final int DETAIL_EDIT = 0x232;
        public static final int PASSWORD_EDIT = 0x233;
        public static final int CREATE_TIMETABLE = 0x234;
        public static final int MODIFY_TIMETABLE = 0x235;
        public static final int SEARCH = 0x236;
    }

    final class URL{
        public static final String Server ="192.168.1.105";
        public static final String Register = "http://"+Server+"/PDA/index.php/Home/Index/register";
        public static final String Login = "http://"+Server+"/PDA/index.php/Home/Index/login";
    }
}
