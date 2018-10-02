package com.example.msa.timetable.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.msa.timetable.Model.User;
import com.example.msa.timetable.R;
import com.example.msa.timetable.Data.SimpleFragmentPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Calendar;


public class DayViewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private ValueEventListener mValueEventListner;
    private DatabaseReference mUserDatabaseReference;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar mytoolbar;
    private TextView username,useremail;
    private CircularImageView userimage;
    private SharedPreferences mUserAccessShared;
    public static int accesscode;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        attachDatabaseReadListner();
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayview);
        //Firebase Reference
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        //Firebase Reference

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        //User mAuth Listner
        //Auth Listner
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                getAccessCode();
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(DayViewActivity.this,ChoiceActivity.class));
                    finish();
                }
            }
        };

        //Adding Toolbar
        mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mytoolbar.setTitle("Dayview");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setElevation(0);

        //Setting Navigation Drawer
        setNavigationDrawer();
        navigationView.getMenu().getItem(0).setCheckable(true);

        if(accesscode!=1){
            invalidateOptionsMenu();
        }

        //Setting Up the ViewPager
        setViewPager();

        //setSharedPreference
        mUserAccessShared = getSharedPreferences("userAccessCode", Context.MODE_PRIVATE);

    }

    private void getAccessCode() {
        accesscode = mUserAccessShared.getInt("AccessCode",1);
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

        Glide.with(DayViewActivity.this)
                .load(mUser.getPhotoUrl())
                .into(userimage);
        username.setText(mUser.getDisplayName());
        useremail.setText(mUser.getEmail());
    }

    private void setViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //To Show the Fragments according to the day of the week
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (Calendar.MONDAY == dayOfWeek) {
            viewPager.setCurrentItem(0, true);
        } else if (Calendar.TUESDAY == dayOfWeek) {
            viewPager.setCurrentItem(1, true);
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            viewPager.setCurrentItem(2, true);
        } else if (Calendar.THURSDAY == dayOfWeek) {
            viewPager.setCurrentItem(3, true);
        } else if (Calendar.FRIDAY == dayOfWeek) {
            viewPager.setCurrentItem(4, true);
        } else if (Calendar.SATURDAY == dayOfWeek) {
            viewPager.setCurrentItem(5, true);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.slidingtabs);
        tabLayout.setupWithViewPager(viewPager);

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

        if(accesscode==1){
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
                startActivity(new Intent(DayViewActivity.this,InputActivity.class));
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
            case R.id.nav_dayview:
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Settings!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                startActivity(new Intent(DayViewActivity.this,AboutActivity.class));
                finish();
                break;
            case R.id.nav_logout:
                final SharedPreferences.Editor editor = mUserAccessShared.edit();
                editor.remove("AccessCode");
                mAuth.signOut();
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
        int access = accesscode;
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
                                if (user.getAccess() != accesscode){
                                    mAuth.signOut();
                                    Toast.makeText(DayViewActivity.this, "You Are Not Authorized!!" , Toast.LENGTH_SHORT).show();
                                    Toast.makeText(DayViewActivity.this, "Use a Different Login" , Toast.LENGTH_SHORT).show();
                                }
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
