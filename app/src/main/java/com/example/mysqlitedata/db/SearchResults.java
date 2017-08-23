package com.example.mysqlitedata.db;

import android.app.DialogFragment;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysqlitedata.R;
import com.example.mysqlitedata.sql.DataSource;
import com.example.mysqlitedata.sql.SearchList;
import com.example.mysqlitedata.utils.GenericConfirmDialog;
import com.example.mysqlitedata.utils.MenuManager;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GenericConfirmDialog.GenericDialogListener {

    private final String TAG = this.getClass().getSimpleName();
    private final String title = "Search Results";
    //private final String i = "This is '" + title + "' activity.";
    private final String i = "Tap on one of the listed items in order to edit it.";
    private Intent newIntent, sourceIntent;
    private DataSource dataSource;

    private List<SearchList> searchResults;
    private ArrayAdapter adapter;

    private ListView listView;

    private long id;

    String colName, colValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
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
        searchResults = getRecords();
        setListView(searchResults);
    }

    //Set ListView
    private void setListView(List<SearchList> searchResults){

        Log.i(TAG, "setListView method called");

        listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> arrlistofOptions = new ArrayList<>();

        for(SearchList obj : searchResults){

            String row = "";

            row += "ID: " + obj.getId() + "\nNAME: ";
            row += obj.getFirst_name() + " ";
            row += obj.getLast_name() + "\nPHONE: ";
            row += obj.getTel_num() + "\nADDRESS: ";
            row += obj.getAddress();
            int isActive = obj.getIs_active();
            row += (isActive == 1)? "\nACTIVE\n" : "\nNOT ACTIVE\n";

            arrlistofOptions.add(row);
        }

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrlistofOptions);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String info = ((TextView) view).getText().toString();
                String[] substr = info.split(":");

                try{

                    Log.d(TAG, "Before split" + substr[1]);
                    String str  = substr[1].replace(" ", "").trim();
                    str = str.replace("NAME", "").trim();
                    Log.d(TAG, "After split: " + str);
                    id = Long.parseLong(substr[1].replace("NAME", "").trim());
                    Log.d(TAG, "ID: " + id);

                    setDialog(id);

                }
                catch(Exception ex){

                    Toast.makeText(
                            SearchResults.this,
                            ex.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDialog(long id){

        Log.i(TAG, "setDialog method called");

        GenericConfirmDialog dialog =  new GenericConfirmDialog();
        dialog.setButtons("DELETE", "UPDATE", "CANCEL", id);
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), TAG);
    }

    //DELETE record
    @Override
    public void genericPositiveResult(DialogFragment dlg) {

        newIntent = new Intent(SearchResults.this, DeleteRecord.class);
        newIntent.putExtra("ID", id);
        dataSource.closeDB();
        startActivity(newIntent);
    }

    //UPDATE record
    @Override
    public void genericNegativeResult(DialogFragment dlg) {

        //TODO: update view
        newIntent = new Intent(SearchResults.this, UpdateRecord.class);
        newIntent.putExtra("ID", id);
        dataSource.closeDB();
        startActivity(newIntent);
    }

    //CANCEL
    @Override
    public void genericNeutralResult(DialogFragment dlg) {
        //DO NOTHING
    }

    //Get data from source view
    private void getData(){

        Log.i(TAG, "getData method called");
        sourceIntent = getIntent();
        colName = sourceIntent.getStringExtra("colName");
        colValue = sourceIntent.getStringExtra("colValue");
    }

    //Get records from DB
    private List<SearchList> getRecords(){

        Log.i(TAG, "getRecords method called");
        List<SearchList> records = dataSource.fingAllRecords(colName, colValue);
        return records;
    }

    @Override
    protected void onResume(){

        Log.i(TAG, "onResume method called");
        super.onResume();
        dataSource.openDB();
        getData();
        searchResults = getRecords();
        setListView(searchResults);
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

        MenuManager mManager = new MenuManager(SearchResults.this);
        newIntent = mManager.getMenu(id);
        startActivity(newIntent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
