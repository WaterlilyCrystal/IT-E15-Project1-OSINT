package Project1.datagetting;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

import static Project1.datagetting.UsernameCollecting.collectUsernames;

public class InforCollecting {
    public static void main(String[] args) throws IOException {
        Set<String> usernames = collectUsernames();
        JSONArray profileList = new JSONArray();
        for (String profileName : usernames) {
            try {
                // Encode profile name to handle special characters
                String encodedProfileName = URLEncoder.encode(profileName, "UTF-8");

                String apiUrl = "https://spiderum.com/api/v2/user/getUserNumberDataInfo?profile_name=" + encodedProfileName;
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("accept", "application/json, text/plain, */*");
                connection.setRequestProperty("accept-language", "en-US,en;q=0.9,vi;q=0.8");
                connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36");


                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    JSONObject jsonResponse = new JSONObject(new java.util.Scanner(connection.getInputStream()).useDelimiter("\\A").next());

                    JSONObject profile = jsonResponse.getJSONObject("profile");
                    JSONObject count = jsonResponse.getJSONObject("count");
                    JSONObject externalInfo = count.getJSONObject("getExternalInfo");


                    int gender = externalInfo.optInt("gender"); // Use optInt to handle missing fields
                    String socialAccount = "";
                    if (externalInfo.has("social_account")) {
                        JSONArray socialAccounts = externalInfo.getJSONArray("social_account");
                        if (socialAccounts.length() > 0) {
                            socialAccount = socialAccounts.getJSONObject(0).optString("url", "");
                        }
                    }

                    String name = profile.getString("name");
                    int score = profile.getInt("score");
                    String userId = profile.getString("_id");
                    int comments = count.isNull("comments") ? 0 : count.getInt("comments"); // Handle null case
                    int followers = count.isNull("followers") ? 0 : count.getInt("followers");
                    int followings = count.isNull("followings") ? 0 : count.getInt("followings");
                    int createdPosts = count.isNull("createdPosts") ? 0 : count.getInt("createdPosts");

                    // Write UserProfile to JSON file
                    //Name	UserId	Social Account	Gender	Score	Comments count	Followers	Followings	Posts count
                    UserProfile userProfile = new UserProfile(name, userId, socialAccount, gender, score, comments, followers, followings, createdPosts);
                    System.out.println(userProfile.getName()); // Just to verify,

                    profileList.put(userProfile.toJSON());

                    connection.disconnect();}
            } catch(IOException e){
                e.printStackTrace();
            }
        }

        try (FileWriter file = new FileWriter("output.json")) {
            file.write(profileList.toString(4));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



