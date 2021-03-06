package com.example.hp.androidproject;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SearchCouses extends AppCompatActivity {

    /*
        This class creates a searchable interface and allows users
        to search through a list of courses and friends
     */

    private DrawerLayout drawerlayout;
    private ActionBarDrawerToggle abdt;
    private ListView search_course;
    private ListView search_friends;
    private ArrayAdapter<String> searchable;
    private ArrayAdapter<String> friend_searchable;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_courses);

        // initialising variables for nav bar
        drawerlayout = (DrawerLayout)findViewById(R.id.drawerlayout);
        abdt = new ActionBarDrawerToggle(this, drawerlayout, R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        drawerlayout.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){
                int id = item.getItemId();

                if( id == R.id.myprofile){
                    openUser();
                }
                else if( id == R.id.study){
                    openStudy();
                }
                else if( id == R.id.settings){
                    openSettings();
                }
                else if( id == R.id.search){
                    openSearch();
                }
                else if(id == R.id.login){
                    openMainActivity();
                }

                return true;
            }
        });

        //adding courses and friends to be searched
        List<String> courses = new ArrayList<String>(4);
        courses.add("Android Programming");
        courses.add("Programming for IoT");
        courses.add("Java Programming");
        courses.add("Data Analytics");
        List<String> friends = new ArrayList<String>(4);
        friends.add("Shane Bird");
        friends.add("Eimear Galligan");
        friends.add("Muireann MacCarthy");
        friends.add("Amy McCormack");


        search_friends = (ListView) findViewById(R.id.test_course);
        search_course = (ListView) findViewById(R.id.search_course);

        //creating onclick events if the listen item is clicked
        search_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Object selectedFromList = (search_course.getItemAtPosition(position));

                 String course = selectedFromList.toString();
                 if (course.equals("Android Programming")){
                     Intent intent=new Intent(getBaseContext(),AndroidProgramming.class);
                     startActivity(intent);
                 }
                 else if (course.equals("Programming for IoT")){
                     Intent intent=new Intent(getBaseContext(),IOTprogramming.class);
                     startActivity(intent);
                 }
                 else if (course.equals("Java Programming")){
                     Intent intent=new Intent(getBaseContext(),JavaProgramming.class);
                     startActivity(intent);
                 }
                 else if (course.equals("Data Analytics")){
                     Intent intent=new Intent(getBaseContext(),DataAnalytics.class);
                     startActivity(intent);
                 }
             }
         });


        //creating onclick events if the listen item is clicked
        search_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object selectedFromList = (search_friends.getItemAtPosition(position));
                String user = selectedFromList.toString();
                if (user.equals("Amy McCormack")){
                    openAmy();
                }
                else if (user.equals("Muireann MacCarthy")){
                    openMuireann();
                }
                else if (user.equals("Shane Bird")){
                    openShane();
                }
                else if (user.equals("Eimear Galligan")){
                    openEimear();
                }
            }
        });


        searchable = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                courses
        );

        friend_searchable = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                friends
        );
        //setting the contents of the listview
        search_course.setAdapter(searchable);
        search_friends.setAdapter(friend_searchable);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflaing the menu with the search widget
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search_couse);

        //selecting the serach widget
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));

        //adding a listener to the searach widget
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*
                Function when text is submitted
                 */
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*
                Function once a chanage in the text is detected
                 */
                searchable.getFilter().filter(newText);         //filter the lists by the text entered
                searchable.getFilter().filter(newText);         //filter the lists by the text entered
                friend_searchable.getFilter().filter(newText);
                return false;
            }

        });

        return true;

        //https://developer.android.com/guide/topics/search/search-dialog
        //https://www.youtube.com/watch?v=FZfjWXYm80k&vl=en
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void openEimear() {
        Intent intent = new Intent(this, eimearUser.class);
        startActivity(intent);
    }
    public void openShane() {
        Intent intent = new Intent(this, shaneUser.class);
        startActivity(intent);
    }

    public void openAmy() {
        Intent intent = new Intent(this, amyUser.class);
        startActivity(intent);
    }

    public void openMuireann() {
        Intent intent = new Intent(this, muireannUser.class);
        startActivity(intent);
    }

    public void openUser(){
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }

    public void openSettings(){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openStudy(){
        Intent intent = new Intent(this, StudyTimer.class);
        startActivity(intent);
    }

    public void openSearch(){
        Intent intent = new Intent(this, SearchCouses.class);
        startActivity(intent);
    }

    public void openMainActivity(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}



