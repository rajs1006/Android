package app.com.alertme.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.alertme.R;
import app.com.alertme.db.helpers.DisasterHelper;
import app.com.alertme.db.helpers.UserHelper;
import app.com.alertme.db.schemas.DisasterSchema;
import app.com.alertme.db.schemas.UserSchema;

/**
 * Created by 21701234 on 10/5/2017.
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    UserHelper myUserDB;
    DisasterHelper myDisasterDB;

    private String latestDisasterIdView;
    private String mEmailView;
    private String mcountryView;
    private String mRegionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mEmailView = getIntent().getStringExtra("userEmail");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /*setSupportActionBar(toolbar);*/
        prepareDisasterHomeList();

        FloatingActionButton disaster = (FloatingActionButton) findViewById(R.id.disaster);
        disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent disasterIntent = new Intent(HomeActivity.this, MapActivity.class);
                disasterIntent.putExtra("mapRedirect", "disaster");
                disasterIntent.putExtra("userEmail", mEmailView);
                startActivity(disasterIntent);
                /*Snackbar.make(view, "Disaster Registration Page will occur", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void prepareDisasterHomeList() {

        myDisasterDB = new DisasterHelper(this);
        myUserDB = new UserHelper(this);
        // get User details.
        getUserDetails();
        // Get Disaster Details.
        final Map<Integer, String> disasterIdMap = new HashMap<>();
        List<String> disasterDetails = getDisasterDetails(disasterIdMap);

        ListView disasterList = (ListView) findViewById(R.id.dis_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, disasterDetails);
        disasterList.setAdapter(adapter);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.layout_header, disasterList, false);
        disasterList.addHeaderView(header, null, false);

        disasterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent disasterPageIntent = new Intent(HomeActivity.this, DisasterActivity.class);
                disasterPageIntent.putExtra("userEmail", mEmailView);
                disasterPageIntent.putExtra("disasterId", disasterIdMap.get(position));
                disasterPageIntent.putExtra("pageRedir", "true");
                startActivity(disasterPageIntent);
            }
        });
    }

    private void getUserDetails() {

        Cursor result = myUserDB.getUserByEmail(mEmailView);

        while (result.moveToNext()) {
            mcountryView = result.getString(result.getColumnIndex(UserSchema.Tableinfo.country));
            mRegionView = result.getString(result.getColumnIndex(UserSchema.Tableinfo.region));
        }
    }

    private List<String> getDisasterDetails(Map<Integer, String> disasterIdMap) {
        int count = 0;
        Cursor result = myDisasterDB.getDisastersByCountryAndRegion(mcountryView, mRegionView);
        List<String> disasterList = new ArrayList<>();
        while (result.moveToNext()) {
            disasterIdMap.put(++count,result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.disasterId)));
            String name = result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.disasterName));
            String details = result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.disasterDescription));
            String country = result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.country));
            String region = result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.region));
            String emergency = result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.emergency));
            disasterList.add(" Name : "+name+"\n Description : "+details+
                    "\n Country : "+country+"\n Region : "+region+"\n Emergency Contact : "+emergency);
        }

        latestDisasterIdView = disasterIdMap.get(1);
        return disasterList;
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
        /*if (id == R.id.action_settings) {*/
            return true;
        /*}*/

        /*return super.onOptionsItemSelected(item);*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent registerPageIntent = new Intent(HomeActivity.this, UserActivity.class);
            registerPageIntent.putExtra("userEmail", mEmailView);
            registerPageIntent.putExtra("pageRedir", "true");
            startActivity(registerPageIntent);
        } else if (id == R.id.nav_emergency) {
            Intent emergencyPageIntent = new Intent(HomeActivity.this, EmergencyActivity.class);
            emergencyPageIntent.putExtra("userEmail", mEmailView);
            emergencyPageIntent.putExtra("latestDisasterId", latestDisasterIdView);
            startActivity(emergencyPageIntent);
        } else if (id == R.id.nav_help) {
            startActivity(new Intent(HomeActivity.this, HelpActivity.class));

        } else if (id == R.id.nav_logout) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
