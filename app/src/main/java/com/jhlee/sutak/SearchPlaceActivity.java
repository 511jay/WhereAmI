package com.jhlee.sutak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class SearchPlaceActivity extends AppCompatActivity {

    String List = "List";
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);

        TextView place_name = (TextView)findViewById(R.id.place_name);
        TextView content = (TextView)findViewById(R.id.place_content);
        TextView date_text = (TextView)findViewById(R.id.search_place_date);
        name = ((PlaceListActivity)PlaceListActivity.PlaceList).checked_item;
        place_name.setText(name);
        content.setText(getPreferencesContent());
        date_text.setText(getPreferencesDate());

        Button backB= (Button)findViewById(R.id.backButton);
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public String getPreferencesContent(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        return sharedPreferences.getString(name+"_cont", "0");
    }

    public String getPreferencesDate(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        return sharedPreferences.getString(name+"_date", "0");
    }
}