package com.example.msa.timetable.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.msa.timetable.Model.Period;
import com.example.msa.timetable.Model.User;
import com.example.msa.timetable.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import static com.example.msa.timetable.Activities.ChoiceActivity.mUserAccess;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Firebase Defineed variable
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private ValueEventListener mValueEventListner;
    private DatabaseReference mUserDatabaseReference;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar mytoolbar;
    private TextView username,useremail;
    private CircularImageView userimage;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        attachDatabaseReadListner();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        //Firebase Reference

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        //User mAuth Listner
        //Auth Listner
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    Toast.makeText(DashboardActivity.this, "User Not Present", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DashboardActivity.this,ChoiceActivity.class));
                    finish();

                }
                else {
                    Toast.makeText(DashboardActivity.this, "User Present", Toast.LENGTH_SHORT).show();
                    setAccessCode();
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

        if(mUserAccess!=1){
            invalidateOptionsMenu();
        }

    }

    private void setAccessCode() {

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

        if(mUserAccess==1){
            additem.setVisible(true);
        }
        else {
            additem.setVisible(false);
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
                mUserAccess=0;
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
        detachedDatabaseReadListner();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.removeAuthStateListener(mAuthListner);
        detachedDatabaseReadListner();
    }

    private void saveUserData(FirebaseUser user) {
        String uid = user.getUid();
        String name = user.getDisplayName();
        String email = user.getEmail();
        int access = mUserAccess;
        User userdata = new User(uid,name,email,access);
        mUserDatabaseReference.child(uid).setValue(userdata);
    }
    private void attachDatabaseReadListner() {
        if (mValueEventListner == null){
            mValueEventListner = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(mUser.getUid())){
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            User user = snapshot.getValue(User.class);
                            if (user.getUid().equals(mUser.getUid())){
                                Toast.makeText(DashboardActivity.this, "Welcome User : "+ user.getName() + " " + user.getAccess() + " "+ mUserAccess, Toast.LENGTH_SHORT).show();
                                if (user.getAccess() != mUserAccess){
                                    Toast.makeText(DashboardActivity.this, "You Are Not Authorized" + user.getName() + mUserAccess, Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                }
                                else{
                                    Toast.makeText(DashboardActivity.this, "Why are you here?", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(DashboardActivity.this, "You Shouldnt be here:" + user.getName() + " " + user.getAccess() + " " + mUser.getDisplayName() , Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    else{
                        saveUserData(mUser);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mUserDatabaseReference.addValueEventListener(mValueEventListner);
        }
    }
    private void detachedDatabaseReadListner() {
        if (mValueEventListner!=null){
            mUserDatabaseReference.removeEventListener(mValueEventListner);
            mValueEventListner=null;
        }

    }

}
