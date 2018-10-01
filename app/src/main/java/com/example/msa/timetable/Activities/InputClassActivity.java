package com.example.msa.timetable.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.msa.timetable.Model.Class;
import com.example.msa.timetable.Model.User;
import com.example.msa.timetable.R;
import com.example.msa.timetable.Util.ListViewHeight;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import static com.example.msa.timetable.Activities.ChoiceActivity.mUserAccess;


public class InputClassActivity extends AppCompatActivity {
    private Toolbar mytoolbar;
    private EditText classname,teacher,theoryperiod,practicalperiod;
    private Button teacheradd,theoryperiodadd,practicaadd;
    private ListView teacherlist,theorylist,practicallist;
    private ArrayList<String> teachers = new ArrayList<>();
    private ArrayList<String> theoryperiods = new ArrayList<>();
    private ArrayList<String> practicalperiods = new ArrayList<>() ;
    private ArrayList<String> classes = new ArrayList<>();
    private String randomcode ;


    //Firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mClassDatabaseReference,mUserDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ValueEventListener mValueEventListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_class);

        //Setting Toolbar
        setToolbar();

        //Getting All Reference
        classname = (EditText) findViewById(R.id.classnameet);
        teacher = (EditText) findViewById(R.id.newteacheret);
        theoryperiod = (EditText) findViewById(R.id.newthperiodet);
        practicalperiod = (EditText) findViewById(R.id.newprperiodet);
        teacheradd = (Button) findViewById(R.id.teacheraddbtn);
        theoryperiodadd = (Button) findViewById(R.id.thperiodaddbtn);
        practicaadd = (Button) findViewById(R.id.prperiodaddbtn);
        teacherlist = (ListView) findViewById(R.id.teacherlist);
        theorylist = (ListView) findViewById(R.id.thperiodlist);
        practicallist = (ListView) findViewById(R.id.prperiodlist);

        //Firebase
        mAuth =FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mClassDatabaseReference = mFirebaseDatabase.getReference().child("Classes");
        mUserDatabaseReference = mFirebaseDatabase.getReference().child("Users").child(mUser.getUid());



        //GenerateRandomCode
        randomcode = createRandomCode();
        //Adding the new Code


        teacheradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTeacher();
            }
        });
        theoryperiodadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTheoryPeriod();
            }
        });
        practicaadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPracticalPeriod();
            }
        });

    }

    private String createRandomCode() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)));
        for (int i = 0; i < 5; i++)
            sb.append(chars[rnd.nextInt(chars.length)]);
        return sb.toString();
    }

    private void addPracticalPeriod() {
        String periodname = practicalperiod.getText().toString();
        practicalperiods.add(periodname);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,practicalperiods);
        practicallist.setAdapter(adapter);
        ListViewHeight.setListViewHeightBasedOnChildren(practicallist);
        practicalperiod.setText("");
    }

    private void addTheoryPeriod() {
        String periodname = theoryperiod.getText().toString();
        theoryperiods.add(periodname);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,theoryperiods);
        theorylist.setAdapter(adapter);
        ListViewHeight.setListViewHeightBasedOnChildren(theorylist);
        theoryperiod.setText("");
    }

    private void addNewTeacher() {
        String teachername = teacher.getText().toString();
        teachers.add(teachername);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,teachers);
        teacherlist.setAdapter(adapter);
        ListViewHeight.setListViewHeightBasedOnChildren(teacherlist);
        teacher.setText("");
    }

    private void setToolbar() {
        mytoolbar = (Toolbar) findViewById(R.id.createtoolbar);
        mytoolbar.setTitle("Class Details");
        setSupportActionBar(mytoolbar);
        ActionBar ab  = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                //Store the Value in the Database
                createClass();
                startActivity(new Intent(InputClassActivity.this, DashboardActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public User saveUserData(FirebaseUser user,String randomCode) {
        String uid = user.getUid();
        String name = user.getDisplayName();
        String email = user.getEmail();
        int access = mUserAccess;
        classes.add(randomCode);
        User userdata = new User(uid,name,email,access,classes);
        return userdata;
    }


    private void createClass() {
        String name = classname.getText().toString();
        if (!TextUtils.isEmpty(name)){
            Class dataclass = new Class(randomcode,name,teachers,theoryperiods,practicalperiods);
            mClassDatabaseReference.child(randomcode).setValue(dataclass);
            User user = saveUserData(mUser,randomcode);
            mUserDatabaseReference.setValue(user);
        }

    }

}
