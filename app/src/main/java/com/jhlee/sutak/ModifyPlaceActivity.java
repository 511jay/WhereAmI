package com.jhlee.sutak;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ModifyPlaceActivity extends AppCompatActivity {
    String List = "List";
    String JList = "JSON_List";
    String name;
    String content;
    String latitude;
    String longitude;
    public ArrayList<String> temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_place);
        name = ((PlaceListActivity)PlaceListActivity.PlaceList).checked_item;
        temp= new ArrayList<String>();
        temp=getStringArrayPref(JList);
        final EditText edit_name = (EditText)findViewById(R.id.modify_place_name);
        final EditText edit_content = (EditText)findViewById(R.id.modify_place_content);
        Button checkB = (Button)findViewById(R.id.modify_place_check);
        Button delB = (Button)findViewById(R.id.modify_place_delete);

        latitude = getPreferencesLatitude();
        longitude = getPreferencesLongitude();

        checkB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edit_name.getText().toString();
                content = edit_content.getText().toString();
                temp.add(name);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ModifyPlaceActivity.this);
                builder.setTitle("추가확인");
                builder.setMessage(name+"\n"+content+ "  로 수정할까요?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savePreferencesPlaceContent();
                        savePreferencesPlaceLatitude();
                        savePreferencesPlaceLongitude();
                        saveArrayPreferences(JList, temp);
                        int temp1 = ((PlaceListActivity)PlaceListActivity.PlaceList).checked;
                        ((PlaceListActivity)PlaceListActivity.PlaceList).items.remove(temp1);
                        ((PlaceListActivity)PlaceListActivity.PlaceList).items.add(name);
                        ((PlaceListActivity)PlaceListActivity.PlaceList).listView.clearChoices();
                        ((PlaceListActivity)PlaceListActivity.PlaceList).adapter.notifyDataSetChanged();
                        finish();
                    }
                });
                builder.setNegativeButton("아니요", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        delB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ModifyPlaceActivity.this);
                builder.setTitle("추가확인");
                builder.setMessage(name+"을 정말 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int temp1 = ((PlaceListActivity)PlaceListActivity.PlaceList).checked;
                        ((PlaceListActivity)PlaceListActivity.PlaceList).items.remove(temp1);
                        ((PlaceListActivity)PlaceListActivity.PlaceList).listView.clearChoices();
                        ((PlaceListActivity)PlaceListActivity.PlaceList).adapter.notifyDataSetChanged();
                        finish();
                    }
                });
                builder.setNegativeButton("아니요", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public String getPreferencesLatitude(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        return sharedPreferences.getString(name+"_Lat", "0");
    }

    public String getPreferencesLongitude(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        return sharedPreferences.getString(name+"_Long", "0");
    }

    public void savePreferencesPlaceLatitude(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name+"_Lat", String.valueOf(latitude));
        editor.apply();
    }

    public void savePreferencesPlaceLongitude(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name+"_Long", String.valueOf(longitude));
        editor.apply();
    }

    public void savePreferencesPlaceContent(){
        SharedPreferences sharedPreferences = getSharedPreferences(List, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name+"_cont", content);
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
}