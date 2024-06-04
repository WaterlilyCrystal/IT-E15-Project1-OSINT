package Project1.datagetting;

import org.json.JSONObject;

public class UserProfile {
    private String name;
    private int score;
    private String userId;
    private int gender;
    private int comments;
    private int followers;
    private int followings;
    private int createdPosts;
    private String socialAccount;

    // Constructor
    //Name	UserId	Social Account	Gender	Score	Comments count	Followers	Followings	Posts count
    public UserProfile(String name, String userId, String socialAccount,  int gender, int score,  int comments, int followers, int followings, int createdPosts) {
        this.name = name;
        this.score = score;
        this.userId = userId;
        this.gender = gender;
        this.comments = comments;
        this.followers = followers;
        this.followings = followings;
        this.createdPosts = createdPosts;
        this.socialAccount = socialAccount;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getUserId() {
        return userId;
    }

    public int getGender() {
        return gender;
    }

    public int getComments() {
        return comments;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowings() {
        return followings;
    }

    public int getCreatedPosts() {
        return createdPosts;
    }

    public String getSocialAccount() {
        return socialAccount;
    }

    // Convert to JSONObject
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("score", score);
        jsonObject.put("userId", userId);
        jsonObject.put("gender", gender);
        jsonObject.put("comments", comments);
        jsonObject.put("followers", followers);
        jsonObject.put("followings", followings);
        jsonObject.put("createdPosts", createdPosts);
        jsonObject.put("socialAccount", socialAccount);
        return jsonObject;
    }
}