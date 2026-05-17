package com.example.instagramclone.model;

import android.content.Context;
import com.example.instagramclone.R;
import com.example.instagramclone.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataDummy {

    private static DataDummy instance;
    private User currentUser;
    private List<Post> homeFeedPosts;
    private Map<String, User> userMap;
    private Map<String, List<Post>> userPostsMap;
    private List<Story> highlightStories;
    private List<Story> homeStories;

    public static final int[] PROFILE_RES = {
        R.drawable.profile1, R.drawable.profile2, R.drawable.profile3,
        R.drawable.profile4, R.drawable.profile5, R.drawable.profile6,
        R.drawable.profile7, R.drawable.profile8, R.drawable.profile9,
        R.drawable.profile10,
    };

    private DataDummy(Context context) {
        initData(context);
    }

    public static DataDummy getInstance(Context context) {
        if (instance == null) instance = new DataDummy(context);
        return instance;
    }

    public static DataDummy getInstance() {
        return instance;
    }

    private void initData(Context context) {
        SharedPrefManager prefManager = new SharedPrefManager(context);
        String currentUsername = prefManager.getCurrentUsername();
        currentUser = prefManager.getUser(currentUsername);

        userMap = new HashMap<>();
        userPostsMap = new HashMap<>();
        homeFeedPosts = new ArrayList<>();
        highlightStories = new ArrayList<>();
        homeStories = new ArrayList<>();

        if (currentUsername != null && currentUsername.equals("erchiveess")) {
            setupErchiveessData();
        } else if (currentUser != null) {
            setupNewUserData();
        }
    }

    private void setupErchiveessData() {
        // Data lengkap untuk erchiveess
        homeStories.add(new Story("hs0", "Cerita Anda", PROFILE_RES[0], "erchiveess"));
        homeStories.add(new Story("hs1", "lngshot4sho", PROFILE_RES[1], "lngshot4sho"));
        
        // Tambahkan post dummy agar feed tidak kosong
        homeFeedPosts.add(new Post("h1","lngshot4sho", PROFILE_RES[1], R.drawable.post_other1, "Performing live! 🔥", 1245, 89, "2 jam lalu"));
    }

    private void setupNewUserData() {
        userPostsMap.put(currentUser.getUsername(), new ArrayList<>());
        // FIX: Gunakan avatar asli user (ic_default_avatar jika baru daftar)
        homeStories.add(new Story("hs_me", "Cerita Anda", currentUser.getProfileImageRes(), currentUser.getUsername()));
        
        // Beranda tetap kosong sesuai permintaan Anda
    }

    public List<Post>  getHomeFeedPosts()  { return homeFeedPosts; }
    public List<Story> getHomeStories()    { return homeStories; }
    public List<Story> getHighlightStories() { return highlightStories; }
    public User        getCurrentUser()    { return currentUser; }

    public List<Post> getPostsForUser(String username) {
        return userPostsMap.containsKey(username) ? userPostsMap.get(username) : new ArrayList<>();
    }

    public User getUserByUsername(String username) {
        if (userMap.containsKey(username)) return userMap.get(username);
        if (currentUser != null && username.equals(currentUser.getUsername())) return currentUser;
        return currentUser;
    }

    public Post findPostById(String postId) {
        if (postId == null) return null;
        for (Post post : homeFeedPosts) {
            if (postId.equals(post.getId())) return post;
        }
        for (List<Post> posts : userPostsMap.values()) {
            for (Post post : posts) {
                if (postId.equals(post.getId())) return post;
            }
        }
        return null;
    }

    public void addProfilePost(Post post) {
        if (currentUser == null) return;
        List<Post> userPosts = userPostsMap.get(currentUser.getUsername());
        if (userPosts == null) {
            userPosts = new ArrayList<>();
            userPostsMap.put(currentUser.getUsername(), userPosts);
        }
        userPosts.add(0, post);
    }

    public static void resetInstance() {
        instance = null;
    }
}
