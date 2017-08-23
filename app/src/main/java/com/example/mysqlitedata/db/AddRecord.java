package com.example.mysqlitedata.db;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mysqlitedata.R;
import com.example.mysqlitedata.sql.DataSource;
import com.example.mysqlitedata.sql.SearchList;
import com.example.mysqlitedata.utils.MenuManager;


public class AddRecord extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();
    private final String title = "Add a new Record";
    private final String i = "This is '" + title + "' activity.";

    private Intent newIntent;
    private DataSource dataSource;
    private SearchList listItem;

    private EditText editFname, editLname, editTel, editAddress;

    private CheckBox checkIsActive;

    private Button btnCloseAdd, btnResetAdd, btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, i, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.setTitle(title);
        Log.i(TAG, "class called");

        dataSource = new DataSource(this);
        dataSource.openDB();

        setTxtFields();
        setCheckBox();
        setButtons();
    }

    //Create all fields
    private void setTxtFields(){

        Log.i(TAG, "setTxtFields method called");
        editFname = (EditText) findViewById(R.id.editFname);
        editLname = (EditText) findViewById(R.id.editLname);
        editTel = (EditText) findViewById(R.id.editTel);
        editAddress = (EditText) findViewById(R.id.editAddress);
    }

    //Set checkbox
    private void setCheckBox(){

        Log.i(TAG, "setCheckBox method called");
        checkIsActive = (CheckBox) findViewById(R.id.checkIsActive);
    }

    //Create all buttons objects
    private void setButtons(){

        Log.i(TAG, "setButtons method called");
        setCloseBtn();
        setResetBtn();
        setAddBtn();
    }

    //Close button
    private void setCloseBtn() {

        Log.i(TAG, "setCloseBtn method called");
        btnCloseAdd = (Button) findViewById(R.id.btnCloseAdd);
        btnCloseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    //Close button
    private void setResetBtn() {

        Log.i(TAG, "setResetBtn method called");
        btnResetAdd = (Button) findViewById(R.id.btnResetAdd);
        btnResetAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetAll();
            }
        });
    }

    //Reset all fields
    private void resetAll(){

        Log.i(TAG, "resetAll method called");
        editFname.getText().clear();
        editFname.requestFocus();
        editLname.getText().clear();
        editTel.getText().clear();
        editAddress.getText().clear();
        checkIsActive.setChecked(true);
    }

    //Close button
    private void setAddBtn() {

        Log.i(TAG, "setAddBtn method called");
        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "setAddBtn >>> onClick listener called");

                try{

                    String first_name = editFname.getText().toString();
                    String last_name = editLname.getText().toString();
                    int tel_num = Integer.parseInt(editTel.getText().toString());
                    String address = editAddress.getText().toString();
                    int is_active = (checkIsActive.isChecked()) ? 1 : 0;

                    listItem = new SearchList(first_name, last_name, tel_num, address, is_active);

                    dataSource.addRecord(listItem);
                    Log.i(TAG, "a new item is added to DB");
                    resetAll();
                }
                catch (NumberFormatException ex){

                    Log.d(TAG, "ERROR: " + ex.getMessage());
                    Toast.makeText(AddRecord.this, "Please type a valid phone number.", Toast.LENGTH_LONG).show();
                }
                catch (Exception ex){

                    Log.d(TAG, "ERROR: " + ex.getMessage());
                    Toast.makeText(AddRecord.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume(){

        Log.i(TAG, "onResume method called");
        super.onResume();
        dataSource.openDB();
    }

    @Override
    protected void onPause(){

        Log.i(TAG, "onPause method called");
        super.onPause();
        dataSource.closeDB();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        MenuManager mManager = new MenuManager(AddRecord.this);
        newIntent = mManager.getMenu(id);
        startActivity(newIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
