package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;

public class RouteActivity extends AppCompatActivity {
    private int mode = 1;
    //역정보를 정수형으로 임시 저장하는 변수
    private int tmpStart,tmpEnd;
    private Button btn_search;
    private Button btn_back;
    private boolean check; // 숫자가 입력되었는지 확인해주는 boolean 변수
    private EditText startStation;  // 출발역 EditText
    private EditText endStation;    // 도착역  EditText
    //Button btn_station_info = (Button)findViewById(R.id.btn_station_info);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        btn_search = findViewById(R.id.btn_search);
        startStation = findViewById(R.id.startStation);
        endStation = findViewById(R.id.endStation);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = startStation.getText().toString();
                String end = endStation.getText().toString();
                check = true;
                InputStream inputStream = getResources().openRawResource(R.raw.station);
                Intent intent = new Intent(RouteActivity.this, Route2Activity.class);
                intent.putExtra("start", start);
                intent.putExtra("end", end);

                for (int i = 0; i < start.length(); i++) { // 숫자가 입력됐는지 확인
                    if (!Character.isDigit(start.charAt(i))) check = false;
                }

                for (int i = 0; i < end.length(); i++) {
                    if (!Character.isDigit(end.charAt(i))) check = false;
                }

                if(check) { //숫자만 입력 된 경우
                    try {
                        tmpStart = Integer.parseInt(start);
                        tmpEnd = Integer.parseInt(end);
                        InputStreamReader stream = new InputStreamReader(inputStream, "utf-8");
                        ExecuteWay obj = new ExecuteWay("101", "802", mode, stream);
                        obj.runExecuteWay();
                        if (obj.getCheckStation(tmpStart) && obj.getCheckStation(tmpEnd) && tmpStart != tmpEnd) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "출발역과 도착역이 동일합니다. 다시 입력하여주세요", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        // 유효한 역번호가 아닌 경우에는 Toast메시지를 나타내준다
                        Toast.makeText(getApplicationContext(), "올바른 역번호을 입력하세요", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


                }else{  //숫자가 입력되지 않았을경우 Toast메시지를 나타내준다
                    Toast.makeText(getApplicationContext(), "올바른 역번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RouteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}