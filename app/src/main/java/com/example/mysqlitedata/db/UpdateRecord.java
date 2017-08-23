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
import android.widget.TextView;
import android.widget.Toast;
import com.example.mysqlitedata.R;
import com.example.mysqlitedata.sql.DataSource;
import com.example.mysqlitedata.sql.SearchList;
import com.example.mysqlitedata.utils.MenuManager;

public class UpdateRecord extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();
    private final String title = "Update Record";
    private final String i = "This is '" + title + "' activity.";
    private Intent sourceIntent, newIntent;
    private DataSource dataSource;
    private long id;

    private SearchList record;

    private TextView txtId;

    private EditText updateFname, updateLname, updateTel, updateAddress;
    private CheckBox updateIsActive;
    private Button btnCloseUpdate, btnResetUpdate, btnUpdateItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);
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

        getData();
        record = getRecord(id);
        setIdField();
        setAllFields();
        setUpdateBtn();
        setResetBtn();
        setCloseBtn();

    }

    //Set ID field
    private void setIdField(){

        Log.i(TAG, "setIdField method called");
        txtId = (TextView) findViewById(R.id.txtId);
        txtId.setText("record id: " + Long.toString(id));
    }

    //Get Record
    private SearchList getRecord(long id){

        Log.i(TAG, "getRecord method called");
        return dataSource.getRecord(id);
    }

    private void setAllFields(){

        Log.i(TAG, "setAllFields method called");

        try{

            setFname();
            setLname();
            setPhone();
            setAddress();
            setIsActive();
        }
        catch(Exception ex){

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setFname(){

        Log.i(TAG, "setFname method called");

        updateFname = (EditText) findViewById(R.id.updateFname);
        updateFname.setText(record.getFirst_name());
    }

    private void setLname(){

        Log.i(TAG, "setLname method called");

        updateLname = (EditText) findViewById(R.id.updateLname);
        updateLname.setText(record.getLast_name());
    }

    private void setPhone(){

        Log.i(TAG, "setPhone method called");

        updateTel = (EditText) findViewById(R.id.updateTel);
        updateTel.setText(Integer.toString(record.getTel_num()));
        Log.d(TAG, "phone: " + record.getTel_num());
    }

    private void setAddress(){

        Log.i(TAG, "setAddress method called");

        updateAddress = (EditText) findViewById(R.id.updateAddress);
        updateAddress.setText(record.getAddress());
        Log.d(TAG, "address: " + record.getAddress());
    }

    private void setIsActive(){

        Log.i(TAG, "setIsActive method called");

        updateIsActive = (CheckBox) findViewById(R.id.updateIsActive);

        if(record.getIs_active() == 1){

            updateIsActive.setChecked(true);
        }
        else{

            updateIsActive.setChecked(false);
        }

        Log.d(TAG, "IsActive: " + record.getIs_active());
    }

    //Set UPDATE button
    private void setUpdateBtn(){

        Log.i(TAG, "gsetUpdateBtn method called");

        btnUpdateItem = (Button) findViewById(R.id.btnUpdateItem);
        btnUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first_name = updateFname.getText().toString().trim();
                String last_name = updateLname.getText().toString().trim();
                int tel_num = Integer.parseInt(updateTel.getText().toString().trim());
                String address = updateAddress.getText().toString().trim();
                int is_active = (updateIsActive.isChecked()) ? 1 : 0;;

                try {

                    SearchList item = new SearchList(first_name, last_name, tel_num, address, is_active);
                    item.setId(id);
                    dataSource.updateRecord(item);
                }
                catch (Exception e) {

                    e.printStackTrace();
                    Log.d(TAG, "ERROR: " + e.getMessage());
                    Toast.makeText(UpdateRecord.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Set RESET button
    private void setResetBtn(){

        Log.i(TAG, "setResetBtn method called");

        btnResetUpdate = (Button) findViewById(R.id.btnResetUpdate);
        btnResetUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setAllFields();
            }
        });
    }

    //Set Close button
    private void setCloseBtn(){

        Log.i(TAG, "setCloseBtn method called");

        btnCloseUpdate = (Button) findViewById(R.id.btnCloseUpdate);
        btnCloseUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    //Get data from source view
    private void getData(){

        Log.i(TAG, "getData method called");
        sourceIntent = getIntent();

        if(sourceIntent.hasExtra("ID")){

            id = sourceIntent.getLongExtra("ID", id);
            Log.d(TAG, "ID: " + id);
        }
        else{

            Toast.makeText(this, "Please select a record fist.", Toast.LENGTH_LONG).show();
            newIntent = new Intent(UpdateRecord.this, Search.class);
            startActivity(newIntent);
        }
    }

    @Override
    protected void onResume(){

        super.onResume();
        dataSource.openDB();
    }

    @Override
    protected void onPause(){

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

        MenuManager mManager = new MenuManager(UpdateRecord.this);
        newIntent = mManager.getMenu(id);
        startActivity(newIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
