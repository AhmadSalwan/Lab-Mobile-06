package com.fadhilmulyadi.praktikum1;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private String name;
    private String username;
    private String bio;
    private String profilePictureUrl;
    private int postsCount;
    private int followersCount;
    private int followingCount;

    public UserProfile(String name, String username, String bio, String profilePictureUrl, int postsCount, int followersCount, int followingCount) {
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.profilePictureUrl = profilePictureUrl;
        this.postsCount = postsCount;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public int getPostsCount() {
        return postsCount;
    }
    public int getFollowersCount() {
        return followersCount;
    }
    public int getFollowingCount() {
        return followingCount;
    }
}
