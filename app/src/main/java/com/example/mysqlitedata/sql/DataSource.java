package com.example.mysqlitedata.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Handle all SQL activities
 */

public class DataSource {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    //Constructor
    public DataSource(Context context) {

        Log.i(TAG, "class called");
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    //Add a new record
    public void addRecord(SearchList listItem) {

        Log.i(TAG, "addRecord method called");

        ContentValues values = new ContentValues(); //Creates a HASH TABLE object that will hold all the values
        values.put(DbSchema.DbColumns.F_NAME, listItem.getFirst_name());
        values.put(DbSchema.DbColumns.L_NAME, listItem.getLast_name());
        values.put(DbSchema.DbColumns.TEL, listItem.getTel_num());
        values.put(DbSchema.DbColumns.ADDR, listItem.getAddress());
        values.put(DbSchema.DbColumns.ACTIVE, listItem.getIs_active());

        Long rowId = db.insert(DbSchema.DbColumns.TABLE_NAME, null, values);

        if(rowId != -1){

            Log.i(TAG, "a new record has been inserted");
            Log.d(TAG, "Record ID: " + rowId);
            Toast.makeText(
                    context,
                    "A new record is created:\n" + listItem.getFirst_name() + " " + listItem.getLast_name(),
                    Toast.LENGTH_LONG).show();
        }
        else{

            Log.i(TAG, "a new record has not been inserted");
            Toast.makeText(
                    context,
                    "SQL general error. For more info please check the logs.",
                    Toast.LENGTH_LONG).show();
        }
    }

    //Delete a record
    public int deleteRecord(long id){

        Log.i(TAG, "deleteRecord method called");

        int isDeleted = -1;

        String[] args = {Long.toString(id)};

        try{

            isDeleted =  db.delete(DbSchema.DbColumns.TABLE_NAME,
                    DbSchema.DbColumns._ID + " = ?",
                    args);

            Toast.makeText(context, "Record ID: " + id + " is deleted.", Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){

            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return isDeleted;
    }

    public void updateRecord(SearchList item){

        Log.i(TAG, "updateRecord method called");

        String[] args = {Long.toString(item.getId())};

        ContentValues values = new ContentValues(); //Creates a HASH TABLE object that will hold all the values
        values.put(DbSchema.DbColumns.F_NAME, item.getFirst_name());
        values.put(DbSchema.DbColumns.L_NAME, item.getLast_name());
        values.put(DbSchema.DbColumns.TEL, item.getTel_num());
        values.put(DbSchema.DbColumns.ADDR, item.getAddress());
        values.put(DbSchema.DbColumns.ACTIVE, item.getIs_active());

        try{

            db.update(
                    DbSchema.DbColumns.TABLE_NAME,
                    values,
                    DbSchema.DbColumns._ID + " = ?",
                    args);

            Toast.makeText(context, "Record ID: " + item.getId() + " is updated.", Toast.LENGTH_LONG).show();
        }
        catch(Exception ex){

            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Find all records
    public List<SearchList> getAllRecords() {

        Log.i(TAG, "getAllRecords method called");

        List<SearchList> records = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DbSchema.DbColumns.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try{

            while (cursor.moveToNext()){

                long id = cursor.getLong(cursor.getColumnIndex(DbSchema.DbColumns._ID));
                String first_name = cursor.getString(cursor.getColumnIndex(DbSchema.DbColumns.F_NAME));
                String last_name = cursor.getString(cursor.getColumnIndex(DbSchema.DbColumns.L_NAME));
                int tel_num = cursor.getInt(cursor.getColumnIndex(DbSchema.DbColumns.TEL));
                String address = cursor.getString(cursor.getColumnIndex(DbSchema.DbColumns.ADDR));
                int is_active = cursor.getInt(cursor.getColumnIndex(DbSchema.DbColumns.ACTIVE));

                SearchList item = new SearchList(first_name, last_name, tel_num, address, is_active);
                item.setId(id);
                records.add(item);
                Log.i(TAG, "getAllRecords >>> new record added to the list");
            }
        }
        catch (Exception ex){

            Log.d(TAG, "ERROR " + ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally{

            cursor.close();
        }

        return records;
    }

    //Get a record
    public SearchList getRecord(long id) {

        Log.i(TAG, "getRecord method called");

        SearchList record = null;
        String selectQuery = "SELECT * FROM "  + DbSchema.DbColumns.TABLE_NAME + " WHERE ";
        String[] args={Long.toString(id)};
        Cursor cursor = db.rawQuery(selectQuery + DbSchema.DbColumns._ID + " = ?", args);

        try{

            while(cursor.moveToNext()){

                String first_name = cursor.getString(cursor.getColumnIndex(DbSchema.DbColumns.F_NAME));
                String last_name = cursor.getString(cursor.getColumnIndex(DbSchema.DbColumns.L_NAME));
                int tel_num = cursor.getInt(cursor.getColumnIndex(DbSchema.DbColumns.TEL));
                String address = cursor.getString(cursor.getColumnIndex(DbSchema.DbColumns.ADDR));
                int is_active = cursor.getInt(cursor.getColumnIndex(DbSchema.DbColumns.ACTIVE));

                record = new SearchList(first_name, last_name, tel_num, address, is_active);
                Log.i(TAG, "getAllRecords >>> record found");
            }
        }
        catch (Exception ex){

            Log.d(TAG, "ERROR " + ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally{

            cursor.close();
        }

        return record;
    }

    //Add a new record
    public List<SearchList> fingAllRecords(String colName, String colValue) {

        Log.i(TAG, "getAllRecords method called");

        List<SearchList> records = new ArrayList<>();

        String selectQuery = "SELECT * FROM "  + DbSchema.DbColumns.TABLE_NAME + " WHERE ";
        String[] args={colValue};
        Cursor cursor = db.rawQuery(selectQuery + colName + " = ?", args);

        try{

            while (cursor.moveToNext()){

                long id = cursor.getLong(cursor.getColumnIndex(DbSchema.DbColumns._ID));
                String first_name = cursor.getString(cursor.getColumnIndex(DbSchema.DbColumns.F_NAME));
                String last_name = cursor.getString(cursor.getColumnIndex(DbSchema.DbColumns.L_NAME));
                int tel_num = cursor.getInt(cursor.getColumnIndex(DbSchema.DbColumns.TEL));
                String address = cursor.getString(cursor.getColumnIndex(DbSchema.DbColumns.ADDR));
                int is_active = cursor.getInt(cursor.getColumnIndex(DbSchema.DbColumns.ACTIVE));

                SearchList item = new SearchList(first_name, last_name, tel_num, address, is_active);
                item.setId(id);

                records.add(item);
                Log.i(TAG, "getAllRecords >>> new record added to the list");
            }
        }
        catch (Exception ex){

            Log.d(TAG, "ERROR " + ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally{

            cursor.close();
        }

        return records;
    }

    //Open DB connection
    public void openDB() {

        Log.i(TAG, "openDB method called");
        this.db = dbHelper.getWritableDatabase();
        Log.i(TAG, "DB connection is opened");
    }

    //Close DB connection
    public void closeDB() {

        Log.i(TAG, "closeDB method called");
        dbHelper.close();
        Log.i(TAG, "DB connection is closed");
    }


}
