package com.jonathankau.mediumstaffpicks.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.ActionBar.LayoutParams;

import com.google.gson.JsonObject;
import com.jonathankau.mediumstaffpicks.R;
import com.jonathankau.mediumstaffpicks.fragments.FeedFragment;
import com.jonathankau.mediumstaffpicks.fragments.FeedFragment.OnFragmentInteractionListener;
import com.jonathankau.mediumstaffpicks.models.Story;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;


public class MainActivity extends Activity implements OnFragmentInteractionListener {
    private final String API_URL = "https://api.import.io/store/data/ec6e5d6f-64ab-43ad-90ef-99f319a95fa4/_query?input/webpage/url=https%3A%2F%2Fmedium.com%2F&_user=338e4742-9bee-463c-b7df-4ac1eb0e1506&_apikey=";
    private final String API_KEY = "aNsJDq04ENEx1zfQD3DOgaem312%2BBvpoBYNfMfJcOZEoc9GpzYd%2F6gYsnI6WNj29qnYmZZh4OzHFscVl79TmwA%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActionBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ion.with(this)
        .load(API_URL + API_KEY)
        .asJsonObject()
        .setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                ArrayList<Story> stories = new ArrayList<Story>();

                if(e == null) {
                    Log.d("JKAU", result.toString());
                    stories = Story.fromJsonArray(result.get("results").getAsJsonArray());
                } else {
                    Log.d("JKAU", e.toString());
                }

                getFragmentManager().beginTransaction()
                        .add(R.id.container, FeedFragment.newInstance(stories))
                        .commit();
            }
        });
    }

    private void setupActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        View customNav = LayoutInflater.from(this).inflate(R.layout.actionbar, null);

        actionBar.setCustomView(customNav, layoutParams);
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

    @Override
    public void onFragmentInteraction(Story story) {

    }
}
