package com.jonathankau.mediumstaffpicks.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.jonathankau.mediumstaffpicks.adapters.StoriesAdapter;
import com.jonathankau.mediumstaffpicks.models.Story;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class FeedFragment extends ListFragment {

    private static final String STATE_STORIES = "stories";
    private ArrayList<Story> stories;
    private StoriesAdapter storiesAdapter;

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static FeedFragment newInstance(ArrayList<Story> stories) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STATE_STORIES, stories);
        fragment.setArguments(args);
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

        if (getArguments() != null) {
            stories = getArguments().getParcelableArrayList(STATE_STORIES);
        } else if (savedInstanceState != null) {
            stories = savedInstanceState.getParcelableArrayList(STATE_STORIES);
        }

        // Display content through adapter
        storiesAdapter = new StoriesAdapter(getActivity(), stories);
        setListAdapter(storiesAdapter);
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

    public void refreshStories(ArrayList<Story> stories) {
        storiesAdapter.clear();
        storiesAdapter.addAll(stories);
        Toast.makeText(getActivity(), "Refreshed", Toast.LENGTH_SHORT).show();
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
