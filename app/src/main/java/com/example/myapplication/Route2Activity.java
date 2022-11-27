package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Route2Activity extends AppCompatActivity {

    private Button btn_back;
    private TextView shortFee;
    private TextView shortTime;
    private TextView shortDistance;
    private TextView shortChange;
    private TextView middleStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route2);

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Route2Activity.this, RouteActivity.class);
                startActivity(intent);
            }
        });
    }
}