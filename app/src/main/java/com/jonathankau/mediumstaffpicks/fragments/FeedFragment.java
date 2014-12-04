package com.jonathankau.mediumstaffpicks.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jonathankau.mediumstaffpicks.adapters.StoriesAdapter;
import com.jonathankau.mediumstaffpicks.models.Story;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;


/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class FeedFragment extends ListFragment {
    private final String API_URL = "https://api.import.io/store/data/ec6e5d6f-64ab-43ad-90ef-99f319a95fa4/_query?input/webpage/url=https%3A%2F%2Fmedium.com%2F&_user=338e4742-9bee-463c-b7df-4ac1eb0e1506&_apikey=";
    private final String API_KEY = "aNsJDq04ENEx1zfQD3DOgaem312%2BBvpoBYNfMfJcOZEoc9GpzYd%2F6gYsnI6WNj29qnYmZZh4OzHFscVl79TmwA%3D%3D";

    private static final String STATE_STORIES = "stories";
    private ArrayList<Story> stories;
    private StoriesAdapter storiesAdapter;

    private OnFragmentInteractionListener mListener;
    private PullToRefreshLayout mPullToRefreshLayout;

    // TODO: Rename and change types of parameters
    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            stories = savedInstanceState.getParcelableArrayList(STATE_STORIES);
        } else {
            stories = new ArrayList<Story>();
            updateStories();
        }

        // Display content through adapter
        storiesAdapter = new StoriesAdapter(getActivity(), stories);
        setListAdapter(storiesAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        // This is the View which is created by ListFragment
        ViewGroup viewGroup = (ViewGroup) view;

        // We need to create a PullToRefreshLayout manually
        mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

        // We can now setup the PullToRefreshLayout
        ActionBarPullToRefresh.from(getActivity())
                .insertLayoutInto(viewGroup)
                .theseChildrenArePullable(getListView(), getListView().getEmptyView())
                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        updateStories();
                    }
                })
        .setup(mPullToRefreshLayout);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(stories.get(position));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save necessary data
        savedInstanceState.putParcelableArrayList(STATE_STORIES, stories);
    }

    private void updateStories() {
        Ion.with(this)
                .load(API_URL + API_KEY)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null) {
                            stories = Story.fromJsonArray(result.get("results").getAsJsonArray());
                        } else {
                            Log.d("JKAU", e.toString());
                        }

                        refreshStories(stories);
                    }
                });
    }

    public void refreshStories(ArrayList<Story> stories) {
        storiesAdapter.clear();
        storiesAdapter.addAll(stories);
        Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
        mPullToRefreshLayout.setRefreshComplete();
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Story story);
    }

}
