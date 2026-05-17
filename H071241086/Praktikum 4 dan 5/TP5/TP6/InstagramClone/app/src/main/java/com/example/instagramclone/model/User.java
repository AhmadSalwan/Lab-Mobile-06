package com.example.instagramclone.model;

public class User {
    private String username;
    private String name;
    private int profileImageRes;
    private String bio;
    private int postCount;
    private int followerCount;
    private int followingCount;

    private String pronoun;
    private String website;
    private String music;
    private String gender;

    public User(String username, String name, int profileImageRes, String bio,
                int postCount, int followerCount, int followingCount) {
        this.username = username;
        this.name = name;
        this.profileImageRes = profileImageRes;
        this.bio = bio;
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.pronoun = "";
        this.website = "";
        this.music = "";
        this.gender = "";
    }

    public String getUsername()     { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getName()         { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getProfileImageRes() { return profileImageRes; }
    public void setProfileImageRes(int res) { this.profileImageRes = res; }
    
    public String getBio()          { return bio; }
    public void setBio(String bio)  { this.bio = bio; }
    
    public int getPostCount()       { return postCount; }
    public void setPostCount(int c) { this.postCount = c; }
    
    public int getFollowerCount()   { return followerCount; }
    public int getFollowingCount()  { return followingCount; }

    public String getPronoun() { return pronoun; }
    public void setPronoun(String pronoun) { this.pronoun = pronoun; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getMusic() { return music; }
    public void setMusic(String music) { this.music = music; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}
