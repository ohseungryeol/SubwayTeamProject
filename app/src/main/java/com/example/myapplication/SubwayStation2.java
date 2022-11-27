package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubwayStation2 extends AppCompatActivity {
    private Button btn_back;
    private TextView textElevator;
    private TextView textWheelchair;
    private TextView textToilet;
    private TextView textAirCondition;
    private TextView StationName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subwaystation2);
        StationName = findViewById(R.id.StationName);
        textElevator = findViewById(R.id.textElevator);
        textWheelchair = findViewById(R.id.textWheelchair);
        textToilet = findViewById(R.id.textToilet);
        textAirCondition = findViewById(R.id.textAirCondition);
        Intent intent = getIntent();

        String station = intent.getStringExtra("station");
        String elevator = intent.getStringExtra("elevator");
        String toilet = intent.getStringExtra("toilet");
        String AirCondition = intent.getStringExtra("AirCondition");
        String Wheelchair = intent.getStringExtra("Wheelchair");
        StationName.setText(station);
        textElevator.setText("엘리베이터는 "+elevator+"칸 앞입니다.");
        textWheelchair.setText("휠체어칸은 "+Wheelchair+"입니다.");
        textToilet.setText("화장실은 개찰구 "+toilet+"에 있습니다.");
        textAirCondition.setText("약냉방칸은 "+AirCondition+"입니다.");


        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubwayStation2.this, SubwayStation1.class);
                startActivity(intent);
            }
        });
    }

}
