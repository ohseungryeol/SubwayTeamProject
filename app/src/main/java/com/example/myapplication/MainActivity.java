package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_path_finding;
    private Button btn_alarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_path_finding = findViewById(R.id.btn_path_finding);
        btn_path_finding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RouteActivity.class);
                startActivity(intent);
            }
        });

        /*btn_alarm = findViewById(R.id.btn_alarm);
        btn_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });*/
        Button btn_station_info = (Button) findViewById(R.id.btn_station_info);

        btn_station_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubwayStation1.class);
                startActivity(intent);
            }
        });


    }
}