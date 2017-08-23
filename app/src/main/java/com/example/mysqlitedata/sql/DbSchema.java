package com.example.mysqlitedata.sql;

import android.provider.BaseColumns;

/**
 * This class design our DB structure >>> columns
 */

public final class DbSchema {

    private DbSchema(){}

    static final String CREATE_SEARCH_LIST_TBL =
            "CREATE TABLE " +
            DbColumns.TABLE_NAME +
            " (" +
            DbColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DbColumns.F_NAME + " TEXT NOT NULL, " +
            DbColumns.L_NAME + " TEXT NOT NULL, " +
            DbColumns.TEL + " INTEGER NOT NULL, " +
            DbColumns.ADDR  + " TEXT NOT NULL, " +
            DbColumns.ACTIVE  + " INTEGER NOT NULL, " +
            "UNIQUE (" + DbColumns._ID + ") ON CONFLICT REPLACE)";

    public static class DbColumns implements BaseColumns{

        public static final String TABLE_NAME = "search_list";
        public static final String F_NAME = "first_name";
        public static final String L_NAME = "last_name";
        public static final String TEL = "tel_num";
        public static final String ADDR = "address";
        public static final String ACTIVE = "is_active";
    }

}
