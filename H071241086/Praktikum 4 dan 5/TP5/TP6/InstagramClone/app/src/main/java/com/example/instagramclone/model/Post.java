package com.example.instagramclone.model;

public class Post {
    private String id;
    private String username;
    private int userProfileImage;
    private int imageResId;
    private String imageUriString;
    private String caption;
    private int likeCount;
    private int commentCount;
    private String timeAgo;
    private boolean isLiked;
    private boolean isBookmarked;

    public Post(String id, String username, int userProfileImage, int imageResId,
                String caption, int likeCount, int commentCount, String timeAgo) {
        this.id = id;
        this.username = username;
        this.userProfileImage = userProfileImage;
        this.imageResId = imageResId;
        this.imageUriString = null;
        this.caption = caption;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.timeAgo = timeAgo;
    }

    public Post(String id, String username, int userProfileImage, String imageUriString,
                String caption, int likeCount, int commentCount, String timeAgo) {
        this.id = id;
        this.username = username;
        this.userProfileImage = userProfileImage;
        this.imageResId = 0;
        this.imageUriString = imageUriString;
        this.caption = caption;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.timeAgo = timeAgo;
    }

    public String getId()              { return id; }
    public String getUsername()        { return username; }
    public int getUserProfileImage()   { return userProfileImage; }
    public int getImageResId()         { return imageResId; }
    public String getImageUriString()  { return imageUriString; }
    
    public boolean isUploaded()        { return imageUriString != null; }
    
    public String getCaption()         { return caption; }
    public int getLikeCount()          { return likeCount; }
    public int getCommentCount()       { return commentCount; }
    public String getTimeAgo()         { return timeAgo; }
    public boolean isLiked()           { return isLiked; }
    public boolean isBookmarked()      { return isBookmarked; }

    public void setLiked(boolean liked)         { isLiked = liked; }
    public void setBookmarked(boolean b)        { isBookmarked = b; }
    public void setLikeCount(int likeCount)     { this.likeCount = likeCount; }
    public void setCaption(String caption)      { this.caption = caption; }
}
