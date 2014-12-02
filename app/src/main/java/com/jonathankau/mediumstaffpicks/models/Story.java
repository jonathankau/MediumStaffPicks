package com.jonathankau.mediumstaffpicks.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by jkau on 12/1/14.
 */
public class Story implements Parcelable {
    private String title;
    private String subtitle;
    private String author;
    private String imageUrl;
    private String storyUrl;

    public Story() {
    }

    private Story(Parcel in) {
        this.title = in.readString();
        this.subtitle = in.readString();
        this.imageUrl = in.readString();
        this.storyUrl = in.readString();
        this.author = in.readString();
    }

    @Override
    public String toString() {
        return "[" + title + ", " + subtitle + ", " + author + ", " + imageUrl + ", " + storyUrl + "]";
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    // Decodes single story json into json model object
    public static Story fromJson(JsonObject jsonObject) {
        Story s = new Story();

        if (jsonObject.get("title") != null) {
            s.title = jsonObject.get("title").getAsString();
        }
        if (jsonObject.get("subtitle") != null) {
            s.subtitle = jsonObject.get("subtitle").getAsString();
        }
        if (jsonObject.get("image") != null) {
            s.imageUrl = jsonObject.get("image").getAsString();
        }
        if (jsonObject.get("article_link") != null) {
            s.storyUrl = jsonObject.get("article_link").getAsString();
        }

        if (jsonObject.get("author").isJsonArray()) {
            JsonArray authors = jsonObject.get("author").getAsJsonArray();
            if (authors.size() > 1) {
                s.author = authors.get(1).getAsString();
            } else {
                s.author = authors.get(0).getAsString();
            }
        } else {
            s.author = jsonObject.get("author").getAsString();
        }

        return s;
    }

    // Decodes array of story json results into business model objects
    public static ArrayList<Story> fromJsonArray(JsonArray jsonArray) {
        ArrayList<Story> stories = new ArrayList<Story>(jsonArray.size());

        for(int i = 0; i < jsonArray.size(); i++) {
            JsonObject storyJson = null;
            storyJson = jsonArray.get(i).getAsJsonObject();

            Story story = Story.fromJson(storyJson);
            if (story != null) {
                stories.add(story);
            }
        }

        return stories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.subtitle);
        dest.writeString(this.imageUrl);
        dest.writeString(this.storyUrl);
        dest.writeString(this.author);
    }

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}
