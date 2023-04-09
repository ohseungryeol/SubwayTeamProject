package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Route2Activity extends AppCompatActivity {

    private Button btn_back;
    private TextView min_time;
    private TextView min_dis;
    private TextView min_cost;
    private TextView min_transfer;
    private TextView start_end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route2);

        btn_back = findViewById(R.id.btn_back);
        min_time = findViewById(R.id.min_time);
        min_dis = findViewById(R.id.min_dis);
        min_cost = findViewById(R.id.min_cost);
        min_transfer = findViewById(R.id.min_transfer);
        start_end = findViewById(R.id.start_end);

        int mode = 1;
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String startStation = bundle.getString("start");
        String finishStation = bundle.getString("end");
        start_end.setText(startStation+" -> "+finishStation);
        InputStream inputStream = getResources().openRawResource(R.raw.station);

        try{

            InputStreamReader stream = new InputStreamReader(inputStream, "utf-8");
            ExecuteWay obj = new ExecuteWay(startStation, finishStation, mode, stream);
            obj.runExecuteWay();
            min_time.setText(obj.getOutputStn() + obj.getInformation());

            mode = 2;
            inputStream = getResources().openRawResource(R.raw.station);
            stream = new InputStreamReader(inputStream, "utf-8");
            startStation = bundle.getString("start");
            finishStation = bundle.getString("end");
            ExecuteWay obj2 = new ExecuteWay(startStation, finishStation, mode, stream);
            obj2.runExecuteWay();
            min_dis.setText(obj2.getOutputStn() + obj2.getInformation());

            mode = 3;
            inputStream = getResources().openRawResource(R.raw.station);
            stream = new InputStreamReader(inputStream, "utf-8");
            startStation = bundle.getString("start");
            finishStation = bundle.getString("end");
            ExecuteWay obj3 = new ExecuteWay(startStation, finishStation, mode, stream);
            obj3.runExecuteWay();
            min_cost.setText(obj3.getOutputStn() + obj3.getInformation());

            mode = 4;
            inputStream = getResources().openRawResource(R.raw.station);
            stream = new InputStreamReader(inputStream, "utf-8");
            startStation = bundle.getString("start");
            finishStation = bundle.getString("end");
            ExecuteWay obj4 = new ExecuteWay(startStation, finishStation, mode, stream);
            obj4.runExecuteWay();
            min_transfer.setText(obj4.getOutputStn() + obj4.getInformation());
        }catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();
            Log.e("GODK", exceptionAsStrting);
            e.printStackTrace();
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Route2Activity.this, RouteActivity.class);
                startActivity(intent);
            }
        });
    }
}