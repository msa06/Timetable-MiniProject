package com.example.msa.timetable.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.msa.timetable.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar mytoolbar;
    private FirebaseUser mUser;
    private TextView username,useremail;
    private CircularImageView userimage;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //User mAuth Listner
        //Auth Listner
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(DashboardActivity.this,ChoiceActivity.class));
                    finish();
                }
            }
        };

        //Adding Toolbar
        mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mytoolbar.setTitle("Dashboard");
        setSupportActionBar(mytoolbar);

        //Setting Navigation Drawer
        setNavigationDrawer();
        navigationView.getMenu().getItem(0).setCheckable(true);

        if(ChoiceActivity.studentlogin){
            invalidateOptionsMenu();
        }

    }

    private void setNavigationDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,drawer,mytoolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        //Getting Reference
        userimage = (CircularImageView) header.findViewById(R.id.nav_userimage);
        username = (TextView) header.findViewById(R.id.nav_usernametext);
        useremail = (TextView) header.findViewById(R.id.nav_useremailid);

        Glide.with(DashboardActivity.this)
                .load(mUser.getPhotoUrl())
                .into(userimage);
        username.setText(mUser.getDisplayName());
        useremail.setText(mUser.getEmail());
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        MenuItem additem = menu.findItem(R.id.action_add);
        if(ChoiceActivity.studentlogin){
            additem.setVisible(false);
        }
        if(ChoiceActivity.teacherlogin){
            additem.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast.makeText(this, "Add a New Class!1", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_dashboard:

                break;
            case R.id.nav_dayview:
                startActivity(new Intent(DashboardActivity.this,DayViewActivity.class));
                finish();
                break;
            case R.id.nav_weekview:
                Toast.makeText(this, "Week View", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Settings!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                startActivity(new Intent(DashboardActivity.this,AboutActivity.class));
                finish();
                break;
            case R.id.nav_logout:
                ChoiceActivity.teacherlogin=false;
                ChoiceActivity.studentlogin=false;
                mAuth.signOut();
                Toast.makeText(this, "Signing Out!!", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListner);
    }
}
