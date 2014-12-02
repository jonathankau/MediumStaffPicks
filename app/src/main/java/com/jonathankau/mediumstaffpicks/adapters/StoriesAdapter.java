package com.jonathankau.mediumstaffpicks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonathankau.mediumstaffpicks.R;
import com.jonathankau.mediumstaffpicks.models.Story;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jkau on 12/2/14.
 */
public class StoriesAdapter extends ArrayAdapter<Story> {
    private final LayoutInflater inflater;

    public StoriesAdapter(Context context, ArrayList<Story> stories) {
        super(context, 0, stories);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.list_item_story, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        // Load text and image
        Story entry = getItem(position);

        holder.title.setText(entry.getTitle());
        holder.subtitle.setText(entry.getSubtitle());
        holder.author.setText(entry.getAuthor());

        Ion.with(holder.image)
                .load(entry.getImageUrl());

        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.title) TextView title;
        @InjectView(R.id.subtitle) TextView subtitle;
        @InjectView(R.id.author) TextView author;
        @InjectView(R.id.image) ImageView image;

        public ViewHolder(View view) { ButterKnife.inject(this, view); }
    }
}
