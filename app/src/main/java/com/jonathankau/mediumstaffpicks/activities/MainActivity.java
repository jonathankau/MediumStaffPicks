package com.jonathankau.mediumstaffpicks.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jonathankau.mediumstaffpicks.R;
import com.jonathankau.mediumstaffpicks.models.Story;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private final String API_URL = "https://api.import.io/store/data/ec6e5d6f-64ab-43ad-90ef-99f319a95fa4/_query?input/webpage/url=https%3A%2F%2Fmedium.com%2F&_user=338e4742-9bee-463c-b7df-4ac1eb0e1506&_apikey=";
    private final String API_KEY = "aNsJDq04ENEx1zfQD3DOgaem312%2BBvpoBYNfMfJcOZEoc9GpzYd%2F6gYsnI6WNj29qnYmZZh4OzHFscVl79TmwA%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ion.with(this)
        .load(API_URL + API_KEY)
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if(e == null) {
                    Log.d("JKAU", result.toString());
                    ArrayList<Story> stories = Story.fromJsonArray(result.get("results").getAsJsonArray());
                    Toast.makeText(MainActivity.this, stories.get(0).getTitle(), Toast.LENGTH_LONG).show();
                } else {
                    Log.d("JKAU", e.toString());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
