package com.example.mysqlitedata.db;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.mysqlitedata.R;
import com.example.mysqlitedata.sql.DbSchema;
import com.example.mysqlitedata.utils.MenuManager;

public class Search extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();
    private final String title = "Search";
    private final String i = "This is '" + title + "' activity.";
    private Intent newIntent;

    private EditText editFnameSearch, editLnameSearch, editTelSearch, editAddressSearch;

    private RadioGroup fNameGrp, lNameGrp, activeNameGrp, telNameGrp, addrNameGrp;

    private RadioButton radioFOff, radioFoN;
    private RadioButton radioLOff, radioLoN;
    private RadioButton radioActiveOff, radioActiveoN;
    private RadioButton radioTelOff, radioTeloN;
    private RadioButton radioAddrOff, radioAddroN;

    private CheckBox checkIsActiveSearch;

    private Button btnCloseSearch, btnResetSearch, btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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

        setTxtFields();
        setCheckBox();
        setButtons();

        setfNameGrp();
        setlNameGrp();
        setActiveNameGrp();
        setTelNameGrp();
        setAddrNameGrp();
    }

    //Set fNameGrp group
    private void setfNameGrp(){

        Log.i(TAG, "setfNameGrp method called");
        radioFOff = (RadioButton) findViewById(R.id.radioFOff);

        fNameGrp = (RadioGroup) findViewById(R.id.fNameGrp);
        fNameGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if(!radioFOff.isChecked()){

                    radioLOff.setChecked(true);
                    radioActiveOff.setChecked(true);
                    radioTelOff.setChecked(true);
                    radioAddrOff.setChecked(true);
                }
            }
        });

        //radioFoN = (RadioButton) findViewById(R.id.radioFoN );
        //radioFOr = (RadioButton) findViewById(R.id.radioFOr);
    }

    //Set fNameGrp group
    private void setlNameGrp(){

        Log.i(TAG, "setlNameGrp method called");
        radioLOff = (RadioButton) findViewById(R.id.radioLOff);

        lNameGrp = (RadioGroup) findViewById(R.id.lNameGrp);
        lNameGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if(!radioLOff.isChecked()){

                    radioFOff.setChecked(true);
                    radioActiveOff.setChecked(true);
                    radioTelOff.setChecked(true);
                    radioAddrOff.setChecked(true);
                }
            }
        });

        //radioLoN = (RadioButton) findViewById(R.id.radioLoN);
        //radioLOr = (RadioButton) findViewById(R.id.radioLOr);
    }

    //Set fNameGrp group
    private void setActiveNameGrp(){

        Log.i(TAG, "setActiveNameGrp method called");
        radioActiveOff = (RadioButton) findViewById(R.id.radioActiveOff);

        activeNameGrp = (RadioGroup) findViewById(R.id.activeNameGrp);
        activeNameGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if(!radioActiveOff.isChecked()){

                    radioFOff.setChecked(true);
                    radioLOff.setChecked(true);
                    radioTelOff.setChecked(true);
                    radioAddrOff.setChecked(true);
                }
            }
        });

        //radioFoN = (RadioButton) findViewById(R.id.radioFoN );
        //radioFOr = (RadioButton) findViewById(R.id.radioFOr);
    }

    //Set fNameGrp group
    private void setTelNameGrp(){

        Log.i(TAG, "setTelNameGrp method called");
        radioTelOff = (RadioButton) findViewById(R.id.radioTelOff);

        telNameGrp = (RadioGroup) findViewById(R.id.telNameGrp);
        telNameGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if(!radioTelOff.isChecked()){

                    radioFOff.setChecked(true);
                    radioLOff.setChecked(true);
                    radioActiveOff.setChecked(true);
                    radioAddrOff.setChecked(true);
                }
            }
        });

        //radioFoN = (RadioButton) findViewById(R.id.radioFoN );
        //radioFOr = (RadioButton) findViewById(R.id.radioFOr);
    }

    //Set fNameGrp group
    private void setAddrNameGrp(){

        Log.i(TAG, "setAddrNameGrp method called");
        radioAddrOff = (RadioButton) findViewById(R.id.radioAddrOff);

        addrNameGrp = (RadioGroup) findViewById(R.id.addrNameGrp);
        addrNameGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if(!radioAddrOff.isChecked()){

                    radioFOff.setChecked(true);
                    radioLOff.setChecked(true);
                    radioActiveOff.setChecked(true);
                    radioTelOff.setChecked(true);
                }
            }
        });

        //radioFoN = (RadioButton) findViewById(R.id.radioFoN );
        //radioFOr = (RadioButton) findViewById(R.id.radioFOr);
    }

    //Create all fields
    private void setTxtFields(){

        Log.i(TAG, "setTxtFields method called");
        editFnameSearch = (EditText) findViewById(R.id.editFnameSearch);
        editLnameSearch = (EditText) findViewById(R.id.editLnameSearch);
        editTelSearch = (EditText) findViewById(R.id.editTelSearch);
        editAddressSearch = (EditText) findViewById(R.id.editAddressSearch);
    }

    //Set checkbox
    private void setCheckBox(){

        Log.i(TAG, "setCheckBox method called");
        checkIsActiveSearch = (CheckBox) findViewById(R.id.checkIsActiveSearch);
    }

    //Create all buttons objects
    private void setButtons(){

        Log.i(TAG, "setButtons method called");
        setCloseBtn();
        setResetBtn();
        setSearchBtn();
    }

    //Close button
    private void setCloseBtn() {

        Log.i(TAG, "setCloseBtn method called");
        btnCloseSearch = (Button) findViewById(R.id.btnCloseSearch);
        btnCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    //Close button
    private void setResetBtn() {

        Log.i(TAG, "setResetBtn method called");
        btnResetSearch = (Button) findViewById(R.id.btnResetSearch);
        btnResetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetAll();
            }
        });
    }

    //Reset all fields
    private void resetAll(){

        Log.i(TAG, "resetAll method called");
        editFnameSearch.getText().clear();
        editFnameSearch.requestFocus();

        radioFOff.setChecked(true);
        radioLOff.setChecked(true);
        radioActiveOff.setChecked(true);
        radioTelOff.setChecked(true);
        radioAddrOff.setChecked(true);

        editLnameSearch.getText().clear();
        editTelSearch.getText().clear();
        editAddressSearch.getText().clear();
        checkIsActiveSearch.setChecked(true);
    }

    //Close button
    private void setSearchBtn() {

        Log.i(TAG, "setSearchBtn method called");
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "setSearchBtn >>> onClick listener called");

                try{

                    String colName = null, colValue = null;

                    if(!radioFOff.isChecked()){
                        colName = DbSchema.DbColumns.F_NAME;
                        colValue = editFnameSearch.getText().toString().trim();
                    }
                    else if(!radioLOff.isChecked()){
                        colName = DbSchema.DbColumns.L_NAME;
                        colValue = editLnameSearch.getText().toString().trim();
                    }
                    else if(!radioActiveOff.isChecked()){
                        colName = DbSchema.DbColumns.ACTIVE;
                        int oNoff = checkIsActiveSearch.isChecked() ? 1 : 0;
                        colValue = String.format("%n", oNoff);
                    }
                    else if(!radioTelOff.isChecked()){
                        colName = DbSchema.DbColumns.TEL;
                        colValue = editTelSearch.getText().toString().trim();
                    }
                    else if(!radioAddrOff.isChecked()){
                        colName = DbSchema.DbColumns.ADDR;
                        colValue = editAddressSearch.getText().toString().trim();
                    }

                    if(colName != null && colValue != null && colValue != ""){

                        newIntent = new Intent(Search.this, SearchResults.class);
                        newIntent.putExtra("colName", colName);
                        newIntent.putExtra("colValue", colValue);
                        startActivity(newIntent);
                    }
                    else{

                        Toast.makeText(Search.this, "Please specify search parameter.", Toast.LENGTH_LONG).show();
                    }

                }
                catch (Exception ex){

                    Log.d(TAG, "ERROR: " + ex.getMessage());
                    Toast.makeText(Search.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
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

        MenuManager mManager = new MenuManager(Search.this);
        newIntent = mManager.getMenu(id);
        startActivity(newIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
