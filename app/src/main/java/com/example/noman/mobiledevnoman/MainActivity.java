package com.example.noman.mobiledevnoman;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // creating Fields

    private Button scan;
    private Button json;
    private Button motor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Strict Mode
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        // initializing all the fields

        scan = (Button)findViewById(R.id.btnScan);
        json = (Button)findViewById(R.id.btnJson);
        motor = (Button)findViewById(R.id.btnMotor);


        // this method will run when this button is clicked
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // it will create a new Intent and assign it the values
                Intent intent = new Intent(MainActivity.this,ScanActivity.class);
                // this will start the new activity using the given Intent parameter
                startActivity(intent);
            }
        });
        // this method will run when this button is clicked
        json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // it will create a new Intent and assign it the values
                Intent intent = new Intent(MainActivity.this,JsonActivity.class);
                // this will start the new activity using the given Intent parameter
                startActivity(intent);
            }
        });
        // this method will run when this button is clicked
        motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // it will create a new Intent and assign it the values
                Intent intent = new Intent(MainActivity.this,MotorActivity.class);
                // this will start the new activity using the given Intent parameter
                startActivity(intent);
            }
        });






    }
}
