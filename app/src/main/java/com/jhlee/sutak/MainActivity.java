package com.jhlee.sutak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button AddPlaceB = (Button)findViewById(R.id.see_map);
        Button PlaceListB = (Button)findViewById(R.id.see_list);

        AddPlaceB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeeMapActivity.class);
                startActivity(intent);
            }
        });

        PlaceListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaceListActivity.class);
                startActivity(intent);
            }
        });
    }
}