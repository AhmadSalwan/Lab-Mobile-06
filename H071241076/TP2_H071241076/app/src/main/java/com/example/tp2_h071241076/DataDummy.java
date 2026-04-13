package com.example.tp2_h071241076;

import java.util.ArrayList;

public class DataDummy {

    public static ArrayList<Post> getAllPosts() {
        ArrayList<Post> list = new ArrayList<>();

        String myUser = "akram_dev";
        int myProfile = R.drawable.dennis;

        list.add(new Post(myUser, "Bintang Laut", myProfile, R.drawable.feed_1));
        list.add(new Post(myUser, "Cool Spongebob", myProfile, R.drawable.feed_2));
        list.add(new Post(myUser, "My Bini", myProfile, R.drawable.feed_3));
        list.add(new Post(myUser, "Red Bird", myProfile, R.drawable.feed_4));
        list.add(new Post(myUser, "Black Cat", myProfile, R.drawable.feed_5));
        list.add(new Post(myUser, "Ora Ora Ora", myProfile, R.drawable.feed_6));
        list.add(new Post(myUser, "St. Michael", myProfile, R.drawable.feed_7));
        list.add(new Post(myUser, "Crazy Diamond", myProfile, R.drawable.feed_8));
        list.add(new Post(myUser, "Air JORDAN", myProfile, R.drawable.feed_9));
        list.add(new Post(myUser, "G.O.A.T", myProfile, R.drawable.feed_10));

        return list;
    }

    public static ArrayList<Story> getAllStories() {
        ArrayList<Story> stories = new ArrayList<>();

        stories.add(new Story("RACE", R.drawable.story_1));
        stories.add(new Story("Nongs", R.drawable.story_2));
        stories.add(new Story("Life", R.drawable.story_3));
        stories.add(new Story("Random", R.drawable.story_4));
        stories.add(new Story("Healing", R.drawable.story_5));
        stories.add(new Story("Memory", R.drawable.story_6));
        stories.add(new Story("Love", R.drawable.story_7));

        return stories;
    }
}