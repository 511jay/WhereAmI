package com.jhlee.sutak;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.prefs.Preferences;

public class AddPlaceActivity extends AppCompatActivity {

    String List = "List";
    String JList = "JSON_List";
    String name;
    String content;
    String date_data;
    ArrayList<String> temp;
    double add_latitude;
    double add_longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        add_latitude = ((SeeMapActivity)SeeMapActivity.context_map).latitude;
        add_longitude = ((SeeMapActivity)SeeMapActivity.context_map).longitude;
        final EditText edit_name = (EditText)findViewById(R.id.add_place_name);
        final EditText edit_content = (EditText)findViewById(R.id.add_place_content);
        TextView date_txt = (TextView)findViewById(R.id.add_place_date);
        date_data = getDayData();
        date_txt.setText(date_data);
        temp = getStringArrayPref(JList);

        Button add_checkB = (Button)findViewById(R.id.add_place_check);
        add_checkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edit_name.getText().toString();
                content = edit_content.getText().toString();
                temp.add(name);
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddPlaceActivity.this);
                builder.setTitle("추가확인");
                builder.setMessage(date_data+"\n"+ name+"\n"+content+ "  이 맞나요?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savePreferencesPlaceDate();
                        savePreferencesPlaceContent();
                        savePreferencesPlaceLatitude();
                        savePreferencesPlaceLongitude();
                        saveArrayPreferences(JList, temp);
                        finish();
                    }
                });
                builder.setNegativeButton("아니요", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void savePreferencesPlaceLatitude(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name+"_Lat", String.valueOf(add_latitude));
        editor.apply();
    }

    public void savePreferencesPlaceLongitude(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name+"_Long", String.valueOf(add_longitude));
        editor.apply();
    }

    public void savePreferencesPlaceContent(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name+"_cont", content);
        editor.apply();
    }

    public void savePreferencesPlaceDate(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name+"_date", date_data);
        editor.apply();
    }

    private void saveArrayPreferences(String key, ArrayList<String> values){
        SharedPreferences preferences = getSharedPreferences(List, 0);
        SharedPreferences.Editor editor = preferences.edit();
        JSONArray json = new JSONArray();

        for(int i = 0; i<values.size(); i++){
            json.put(values.get(i));
        }
        if(!values.isEmpty()){
            editor.putString(key, json.toString());
        }
        else{
            editor.putString(key, null);
        }
        editor.apply();
    }

    private ArrayList<String> getStringArrayPref(String key) {
        SharedPreferences prefs = getSharedPreferences(List, 0);
        String json = prefs.getString(key, null);

        ArrayList<String> items2 = new ArrayList<String>();

        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    items2.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return items2;
    }

    public String getDayData(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        simpleDateFormat.format(date);
        return simpleDateFormat.format(date);
    }
}