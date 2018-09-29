package com.example.msa.timetable.Data;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.msa.timetable.Model.Developer;
import com.example.msa.timetable.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class DeveloperAdaptor extends ArrayAdapter<Developer> {
    private Activity context;
    private List<Developer> developers;
    public DeveloperAdaptor (Activity context , List<Developer> developers){
        super(context,0,developers);
        this.context = context;
        this.developers = developers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View listview = layoutInflater.inflate(R.layout.developer_row,null,true);

        CircularImageView profilepic = (CircularImageView) listview.findViewById(R.id.profilepic);
        TextView name = (TextView) listview.findViewById(R.id.dname);

        Developer developer = developers.get(position);

        profilepic.setImageResource(developer.getPhotoid());
        name.setText(developer.getName());

        return listview;
    }
}
