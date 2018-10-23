package com.example.noman.mobiledevnoman;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
// Json Activity Class
public class JsonActivity extends AppCompatActivity {

    // creating fields
    private String[] text;
    private EditText jsonUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

       //Assigning values to the fields
        jsonUrl = (EditText) findViewById(R.id.editJsonUrl);
        Button jsonButton = (Button) findViewById(R.id.btnJsonData);


        // this method will run when the button is clicked
        jsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this will get the value of  url input field and assign it to  the string
                String url = jsonUrl.getText().toString();
                // if statement to check if the string is not empty
                if(url != ""){
                    // this will call the async class with the given url
                    new AsyncClass().execute(url);
                }
                else{
                    // this will call the async class with the default parameter if the url input is empty
                    new AsyncClass().execute("http://192.168.0.15:8081/MobileAssignment/ShowJson")
                    ;
                }




            }
        });
    }


    //



public void setList() {
    // initializing the list View variable and assigning the value to it
    ListView eListView = (ListView) findViewById(R.id.jsonListview);
    // Inserting Global field String array to Array Adapter
    ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, text);
    // insert Array data to list view
    eListView.setAdapter(arrayAdapter);
}



////////////////////////////////////  ASYNC CLASS ///////////////////////////////////////

    public class AsyncClass extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            // initializing the HTTPurl and bufferReader
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                // will assign first parameter to url field
                URL url = new URL(params[0]);
                // this will open up the url connection
                connection = (HttpURLConnection) url.openConnection();
                // this will connect to the url
                connection.connect();

                // it will get Input stream of the http request
                InputStream stream = connection.getInputStream();
                // assign input stream to bufferReader
                reader = new BufferedReader(new InputStreamReader(stream));
                // will read the data
                StringBuffer sbuffer = new StringBuffer();
                String data = "";
                // this loop will append all the data to StringBuffer
                while ((data = reader.readLine()) != null) {
                    sbuffer.append(data);
                }
                // will assign Stringbuffer value to string
                String json = sbuffer.toString();

                // will add all the data to a string and then put it into JSON Array
                JSONArray jArray = new JSONArray(json);


                StringBuffer ssbuffer = new StringBuffer();
                // will assign the String[] Arrays
                text = new String[jArray.length()];
                for( int i = 0 ; i < jArray.length(); i++){
                    JSONObject jObject = jArray.getJSONObject(i);
                    // will get the values and put it into the fields neatly
                    String id = jObject.getString("entryId");
                    String name = jObject.getString("sensorName");
                    String value = jObject.getString("sensorValue");
                    String time = jObject.getString("timeInserted");

                    // will append data to String Buffer... Just for returning purposes later on
                    ssbuffer.append(id + "    " +  name + "    " +  value + "    " +  time + "\n");
                     // this String array will help us display the data in List View
                    text[i] = id + "    " +  name + "    " +  value + "    " +  time + "\n" ;
                    System.out.print(text);



                }




                return null;

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            }

                try {
                    if (reader != null) {
                        // this will close the Buffer Reader.
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // will simply call the function
            setList();
        }
    }
}


