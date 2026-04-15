package com.example.tp2_h071241020;

import java.util.ArrayList;

public class DataSource {
    public static ArrayList<Feed> feeds = new ArrayList<>();
    public static ArrayList<Feed> myProfileFeeds = new ArrayList<>();
    public static ArrayList<Highlight> myHighlights = new ArrayList<>();

    static {
        String[] users = {"hindia", "bernadya", "neckdeep", "oasis", "paramore", "statechamps", "mcr", "simpleplan", "greenday", "perunggu"};
        String[] names = {"Baskara Putra", "Bernadya Ribka", "Ben Barlow", "Liam & Noel", "Hayley Williams", "Derek DiScanio", "Gerard Way", "Pierre Bouvier", "Billie Joe", "Musik Maju Mundur"};
        String[] bios = {"Menari dengan bayangan.", "Sialnya, hidup terus berjalan.", "Life's not out to get you.", "Definitely Maybe.", "Still into you.", "Around the world and back.", "The Black Parade.", "Welcome to my life.", "American Idiot.", "Membasuh."};
        int[] pps = {R.drawable.ppa1, R.drawable.ppa2, R.drawable.ppa3, R.drawable.ppa4, R.drawable.ppa5, R.drawable.ppa6, R.drawable.ppa7, R.drawable.ppa8, R.drawable.ppa9, R.drawable.ppa10};
        int[] posts = {R.drawable.p_a1, R.drawable.p_a2, R.drawable.p_a3, R.drawable.p_a4, R.drawable.p_a5, R.drawable.p_a6, R.drawable.p_a7, R.drawable.p_a8, R.drawable.p_a9, R.drawable.p_a10};
        String[] captions = {"Evaluasi.", "Satu bulan.", "December.", "Wonderwall.", "Misery Business.", "Secrets.", "Helena.", "Perfect.", "Wake me up.", "Pastikan Riuh."};

        for (int i = 0; i < 10; i++) {
            ArrayList<Highlight> dummyHighlight = new ArrayList<>();
            dummyHighlight.add(new Highlight("Album", posts[i]));
            feeds.add(new Feed(users[i], names[i], bios[i], pps[i], posts[i], captions[i], dummyHighlight));
        }

        int[] myFeedResources = {
                R.drawable.p1mai,
                R.drawable.p2mai,
                R.drawable.p3mai,
                R.drawable.p4mai,
                R.drawable.p5mai
        };

        for (int i = 0; i < myFeedResources.length; i++) {
            myProfileFeeds.add(new Feed(
                    "agungallokraeng",
                    "Agung Allo K.",
                    "Sistem Informasi 24\nAir Putih",
                    R.drawable.ppmai,
                    myFeedResources[i],
                    "Postingan ke-" + (i + 1),
                    new ArrayList<>()
            ));
        }

        int[] myHighlightResources = {
                R.drawable.p1mai,
                R.drawable.p2mai,
                R.drawable.p3mai,
                R.drawable.p4mai,
                R.drawable.p5mai,
                R.drawable.p6mai,
                R.drawable.p7mai
        };

        for (int i = 0; i < myHighlightResources.length; i++) {
            myHighlights.add(new Highlight("Sorotan " + (i + 1), myHighlightResources[i]));
        }
    }

    public static void deletePost(Feed feed) {
        myProfileFeeds.remove(feed);
    }
}