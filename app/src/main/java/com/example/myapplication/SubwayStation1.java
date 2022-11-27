package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubwayStation1 extends AppCompatActivity {
    private Button btn_Info ;
    private Button btn_back ;
    public EditText info_search;
    public TextView dbtext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subwaystation1);
        info_search = findViewById(R.id.info_search);
        dbtext = findViewById(R.id.dbtext);
        btn_Info = findViewById(R.id.btn_Info);

        btn_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                String station = info_search.getText().toString();
                if (databaseAccess.IsFacility(station)==true) {

                    String elevator = databaseAccess.getElevator(station); //getElevator 함수 호출
                    String toilet = databaseAccess.getToilet(station);
                    String AirCondition = databaseAccess.getAirCondition(station);
                    String Wheelchair = databaseAccess.getWheelchair(station);

                    databaseAccess.close();
                    Intent intent = new Intent(SubwayStation1.this, SubwayStation2.class);
                    intent.putExtra("station", station);
                    intent.putExtra("elevator", elevator);
                    intent.putExtra("toilet", toilet);
                    intent.putExtra("AirCondition", AirCondition);
                    intent.putExtra("Wheelchair", Wheelchair);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(),"올바른 역을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubwayStation1.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
