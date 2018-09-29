package com.example.msa.timetable.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.example.msa.timetable.Data.DeveloperAdaptor;
import com.example.msa.timetable.Model.Developer;
import com.example.msa.timetable.R;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.abouttoolbar);
        toolbar.setTitle("About");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        List<Developer> developers = new ArrayList<>();
        developers.add(new Developer("Mohammad Suhaib Ahmed",R.drawable.suhaib));
        developers.add(new Developer("Ankur Pandey",R.drawable.ankur));
        developers.add(new Developer("Sanika Haval",R.drawable.sanika));

        listView=(ListView)findViewById(R.id.listview);
        DeveloperAdaptor adaptor = new DeveloperAdaptor(this,developers);
        listView.setAdapter(adaptor);
    }
}
