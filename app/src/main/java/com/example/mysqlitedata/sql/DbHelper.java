package com.example.mysqlitedata.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Database SQLite Open Helper
 */

public class DbHelper extends SQLiteOpenHelper{

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private static final String DB_NAME = "contact_list.db";
    private static final int VERSION = 1;

    //Constructor
    public DbHelper(Context context) {

        super(context, DB_NAME, null, VERSION);
        Log.i(TAG, "class called");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.i(TAG, "onCreate method called");

        try {
            Log.d(TAG, "SQL: " + DbSchema.CREATE_SEARCH_LIST_TBL);
            sqLiteDatabase.execSQL(DbSchema.CREATE_SEARCH_LIST_TBL);
            Log.d(TAG, DB_NAME + " DB is created");
            Log.d(TAG, DbSchema.DbColumns.TABLE_NAME + " table is created");
            Toast.makeText(context, DbSchema.DbColumns.TABLE_NAME + " table is created", Toast.LENGTH_LONG).show();
        }
        catch(Exception ex){

            Log.d(TAG, "ERROR: " + ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        Log.i(TAG, "onUpgrade method called");

        try {
            Log.d(TAG, "SQL: " + "DROP TABLE IF EXISTS " + DbSchema.DbColumns.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DbSchema.DbColumns.TABLE_NAME);
            Log.d(TAG, DbSchema.DbColumns.TABLE_NAME + " has been removed");

            onCreate(sqLiteDatabase); //Create a new table

            Log.d(TAG, DB_NAME + " DB is canged");
            Log.d(TAG, DbSchema.DbColumns.TABLE_NAME + " table is upgraded");
        }
        catch(Exception ex){

            Log.d(TAG, "ERROR: " + ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
