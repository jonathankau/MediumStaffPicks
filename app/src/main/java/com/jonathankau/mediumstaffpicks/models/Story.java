package com.jonathankau.mediumstaffpicks.models;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by jkau on 12/1/14.
 */
public class Story {
    private String title;
    private String subtitle;
    private JsonArray author;
    private String image_url;
    private String story_url;

    // Getters
    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public JsonArray getAuthor() {
        return author;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getStory_url() {
        return story_url;
    }

    // Decodes single story json into json model object
    public static Story fromJson(JsonObject jsonObject) {
        Story s = new Story();

        s.title = jsonObject.get("title").getAsString();
        s.subtitle = jsonObject.get("subtitle").getAsString();
        s.author = jsonObject.get("author").getAsJsonArray();
        s.image_url = jsonObject.get("image").getAsString();
        s.story_url = jsonObject.get("article_link").getAsString();

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
}
