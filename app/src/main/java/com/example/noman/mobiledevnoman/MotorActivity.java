package com.example.noman.mobiledevnoman;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.net.HttpURLConnection;
import java.net.URL;
// Motr Activity Class
public class MotorActivity extends AppCompatActivity {
    // Creating fields
    private RadioGroup radioGroup;
    private Button motorButton;
    private EditText motorUrl;
    private RadioButton radioButton;
    private TextView textMotorStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);
        // Assigning values to the fields
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        motorButton = (Button)findViewById(R.id.btnMoveMotor);
        motorUrl  = (EditText)findViewById(R.id.editMotorUrl);
        textMotorStatus  = (TextView) findViewById(R.id.textViewMotorStatus);
        // this method will run when the button is clicked
        motorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // whatever radio button is selected, we will get the Id of that radio button from radio group and assign it to the integer
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    // we will get the specific radio button which was selected in the radio group and use integer "radioID" to get the id
                    radioButton = (RadioButton) findViewById(radioId);
                    // get value of that specific radio button and assign it to the string for later uses
                    String position = radioButton.getText().toString();

                    // an if statement to check if the url field is not empty
                    if(motorUrl.getText().toString() != ""){
                        // will call the Async class with the given url and selected radio group as a get parameter
                        new AsyncClass().execute(motorUrl.getText().toString()+"?position="+position);
                        // this will change the text of status
                        textMotorStatus.setText("Request Sent to: Servo Motor to move in the "+ position + " angle.");
                    }
                    else{
                        // this is a default parameter in case the input field is empty
                        new AsyncClass().execute("http://192.168.0.15:8081/MobileAssignment/MoveServoMotor?position="+position);
                        // this will change the text of status
                        textMotorStatus.setText("Request Sent to: Servo Motor to move in the "+ position + " angle.");

                    }

                }
                catch(Exception e){
                    e.getMessage();

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
                // this is just to get the response code of the HTTP request
                connection.getResponseCode();


            }
           catch (Exception e){
              e.getMessage();
               }


          return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // this will set the text of status to Motor Successfully Moved
            textMotorStatus.setText("Motor successfully moved");



        }
    }



}
