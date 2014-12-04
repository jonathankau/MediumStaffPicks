package com.jonathankau.mediumstaffpicks.activities;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jonathankau.mediumstaffpicks.R;
import com.jonathankau.mediumstaffpicks.fragments.FeedFragment;
import com.jonathankau.mediumstaffpicks.fragments.FeedFragment.OnFragmentInteractionListener;
import com.jonathankau.mediumstaffpicks.models.Story;


public class MainActivity extends Activity implements OnFragmentInteractionListener {
    private final String STATE_STORIES = "state_stories";
    private final String FEED_FRAGMENT_TAG = "feed_fragment";
    private FeedFragment feedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupActionBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            feedFragment = (FeedFragment) getFragmentManager().findFragmentByTag(FEED_FRAGMENT_TAG);
        } else {
            // Create new fragment and insert into container
            feedFragment = FeedFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .add(R.id.container,feedFragment, FEED_FRAGMENT_TAG)
                    .commit();
        }
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
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
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
        Intent intent = new Intent(this, WebStoryActivity.class);

        Bundle args = new Bundle();
        args.putString("STORY_URL", story.getStoryUrl());
        intent.putExtras(args);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.fall_back_right);
    }

}
