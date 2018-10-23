package com.example.noman.mobiledevnoman;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


// This is Scan Activity Class
public class ScanActivity extends AppCompatActivity {


    // All fields that will be used in this class
    private EditText url;
    private Button scanButton;
    private TextView sensorName;
    private TextView sensorValue;
    private TextView sensorNameData;
    private TextView sensorValueData;
    private TextView status;
    StringBuilder sb = new StringBuilder();
    ArrayList<String> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_scan);

        // Assigning the fields to their value
        url = (EditText) findViewById(R.id.editSliderUrl);
        scanButton = (Button) findViewById(R.id.btnScanData);
        sensorName = (TextView) findViewById(R.id.textDataName);
        sensorValue = (TextView) findViewById(R.id.textDataValue);
        sensorNameData = (TextView) findViewById(R.id.textNameData);
        sensorValueData = (TextView) findViewById(R.id.textValueData);
        status = (TextView) findViewById(R.id.textDataStatus);





// this method will run when you click on the button
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL url1 = null;
                // this will get assign the text of the input field to a string
                String inputField = url.getText().toString();
                try {
                    // an if statement to check if the input field is not empty
                    if (inputField != null)
                        // this will call the AysncClass with the given parameter which we got form input field
                        new AsyncClass().execute(inputField);
                    //this will disable the button so the user does not click more than once
                    scanButton.setEnabled(false);
                    {
                        // if the input field is empty this default url will be used to call the Aysnc Class
                        new AsyncClass().execute("http://192.168.0.15:8081/MobileAssignment/Scan");
                        // again it will disable the button
                        scanButton.setEnabled(false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }


    ////////////////////////////////////  ASYNC CLASS ///////////////////////////////////////

    public class AsyncClass extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;

            try {
                // will assign first parameter to url field

                URL url = new URL(params[0]);
                // this will open up the url connection
                connection = (HttpURLConnection) url.openConnection();
                // this will connect to the url
                connection.connect();
                // this will set the request method to GET
                connection.setRequestMethod("GET");
                // this reader will be used to retrieve the content from the url
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                int i = 0;
                // a while to go thorough all the lines from the Web
                while ((line = br.readLine()) != null) {
                    // insert data into the ArrayList
                    data.add(i,line);

                }
                // close the buffer Reader
                br.close();

                //connection.getResponseCode();


            }
            catch (Exception e){
                e.getMessage();
            }


            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // check if the ArrayList is not empty
            if(!data.isEmpty()) {
                // will change the value of status input field
                status.setText("Status : Data Retrieved");
                // will change the value of sensor Value input field to ArrayList position 0
                sensorValueData.setText(data.get(0));
                // will change the value of status input field
                sensorNameData.setText(data.get(1));
                // will enable the button again, which was disabled earlier
                scanButton.setEnabled(true);

            }
            else{
                // if data not received it will give a message
                status.setText("Status : Data not Retrieved Retrieved");
                // and will enable the button
                scanButton.setEnabled(true);
            }




        }
    }}