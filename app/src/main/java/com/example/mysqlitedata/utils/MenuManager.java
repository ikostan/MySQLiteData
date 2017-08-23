package com.example.mysqlitedata.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.mysqlitedata.db.AddRecord;
import com.example.mysqlitedata.db.DeleteRecord;
import com.example.mysqlitedata.MainActivity;
import com.example.mysqlitedata.R;
import com.example.mysqlitedata.db.Search;
import com.example.mysqlitedata.db.UpdateRecord;

/**
 * Menu manager class
 */

public class MenuManager {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;

    public MenuManager(Context sourceContext){

        Log.i(TAG, "class called");
        this.context = sourceContext;
    }

    public Intent getMenu(int menuId){

        Log.i(TAG, "getMenu method called");

        Intent intent = null;

        switch(menuId){

            case R.id.nav_main:
                intent = new Intent(context, MainActivity.class);
                break;

            case R.id.nav_search:
                intent = new Intent(context, Search.class);
                break;

            case R.id.nav_add:
                intent = new Intent(context, AddRecord.class);
                break;

            case R.id.nav_remove:
                intent = new Intent(context, DeleteRecord.class);
                break;

            case R.id.nav_update:
                intent = new Intent(context, UpdateRecord.class);
                break;
        }

        return intent;
    }

}
