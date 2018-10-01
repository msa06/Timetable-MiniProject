package com.example.msa.timetable.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.msa.timetable.R;

import java.util.ArrayList;

public class InputClassActivity extends AppCompatActivity {
    private Toolbar mytoolbar;
    private EditText classname,teacher,theoryperiod,practicalperiod;
    private Button teacheradd,theoryperiodadd,practicaadd;
    private ListView teacherlist,theorylist,practicallist;
    private ArrayList<String> teachers = new ArrayList<>();
    private ArrayList<String> theoryperiods = new ArrayList<>();
    private ArrayList<String> practicalperiods = new ArrayList<>() ;
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
                startActivity(new Intent(InputClassActivity.this, DashboardActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
