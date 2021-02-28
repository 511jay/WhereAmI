package com.jhlee.sutak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PlaceListActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ListView listView ;
    String List = "List";
    String JList = "JSON_List";
    public ArrayList<String> items;
    public static Context PlaceList;
    int checked;
    String checked_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        PlaceList = this;
        items= new ArrayList<String>();
        items=getStringArrayPref(JList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, items);
        listView =  (ListView)findViewById(R.id.ResListView) ;
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Button onMap = (Button)findViewById(R.id.see_on_map);
        Button searchB = (Button)findViewById(R.id.search_place);
        Button manageB = (Button)findViewById(R.id.manage_place);

        onMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count =0;
                count =adapter.getCount();
                if (count > 0) {
                    checked =listView.getCheckedItemPosition();
                    if((checked>-1) && (checked<count)){
                        checked_item =items.get(checked);
                        Intent intent = new Intent(getApplicationContext(), SeePlaceActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count =0;
                count =adapter.getCount();
                if (count > 0) {
                    checked =listView.getCheckedItemPosition();
                    if((checked>-1) && (checked<count)){
                        checked =listView.getCheckedItemPosition();
                        checked_item =items.get(checked);
                        Intent intent = new Intent(getApplicationContext(), SearchPlaceActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        manageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count =0;
                count =adapter.getCount();
                if (count > 0) {
                    checked =listView.getCheckedItemPosition();
                    if((checked>-1) && (checked<count)){
                        checked =listView.getCheckedItemPosition();
                        checked_item =items.get(checked);
                        Intent intent = new Intent(getApplicationContext(), ModifyPlaceActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private ArrayList<String> getStringArrayPref(String key) {
        SharedPreferences prefs = getSharedPreferences(List, Context.MODE_PRIVATE);
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
        SharedPreferences preferences = getSharedPreferences(List, Context.MODE_PRIVATE);
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

    public void onStop(){
        super.onStop();
        saveArrayPreferences(JList, items);
    }
}