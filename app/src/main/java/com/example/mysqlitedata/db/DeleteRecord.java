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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysqlitedata.R;
import com.example.mysqlitedata.sql.DataSource;
import com.example.mysqlitedata.utils.MenuManager;

public class DeleteRecord extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();
    private final String title = "Delete Record";
    private final String i = "This is '" + title + "' activity.";
    private Intent sourceIntent, newIntent;
    private DataSource dataSource;
    private long id;

    private Button btnCloseDelete, btnDeleteItem;

    private TextView txtRecordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_record);
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

        txtRecordId = (TextView) findViewById(R.id.txtRecordId);

        getData();
        setCancelBtn();
        setDeleteBtn();

    }

    //Set CANCEL button
    private void setCancelBtn(){

        Log.i(TAG, "setCancelBtn method called");
        btnCloseDelete = (Button) findViewById(R.id.btnCloseDelete);
        btnCloseDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataSource.closeDB();
                finish();
            }
        });
    }

    //Set DELETE button
    private void setDeleteBtn(){

        Log.i(TAG, "setDeleteBtn method called");
        btnDeleteItem = (Button) findViewById(R.id.btnDeleteItem);
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dataSource.deleteRecord(id) > -1){

                    dataSource.closeDB();
                    finish();
                }
            }
        });
    }

    //Get data from source view
    private void getData(){

        Log.i(TAG, "getData method called");
        sourceIntent = getIntent();

        if(sourceIntent.hasExtra("ID")){

            id = sourceIntent.getLongExtra("ID", id);
            txtRecordId.setText(Long.toString(id));
            Log.d(TAG, "ID: " + id);
        }
        else{

            dataSource.closeDB();
            Toast.makeText(this, "Please select a record fist.", Toast.LENGTH_LONG).show();
            newIntent = new Intent(DeleteRecord.this, Search.class);
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

        MenuManager mManager = new MenuManager(DeleteRecord.this);
        newIntent = mManager.getMenu(id);
        startActivity(newIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
