package Project1.datagetting;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AirtableWriter {

    private static final String BASE_ID = "appj8cVoBZvYlfByc";
    private static final String API_KEY = "patI7Z8UsynySOGcO.bb37b8da1f9f0c8ae20bb8f78710824fcf80bc0e4131493a86728e8a34972ecd";
    private static final String TABLE_NAME = "SpiderumUsers";

    public static void main(String[] args) throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get("C:\\Users\\Hello\\IdeaProjects\\OSINT\\output.json")));
        createTable();
        insertRecords(jsonData);
    }

    private static void createTable() throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody body = RequestBody.create(mediaType, "{\"fields\":{\"Name\":\"Name\",\"Score\":\"Score\",\"Comments\":\"Comments\",\"Followers\":\"Followers\",\"Gender\":\"Gender\",\"Followings\":\"Followings\",\"CreatedPosts\":\"CreatedPosts\",\"SocialAccount\":\"SocialAccount\",\"UserId\":\"UserId\"}}");

        Request request = new Request.Builder()
                .url("https://api.airtable.com/v0/" + BASE_ID + "/" + TABLE_NAME)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        //System.out.println(response.body().string());
    }

    private static void insertRecords(String jsonData) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            int score = jsonObject.getInt("score");
            int comments = jsonObject.getInt("comments");
            int followers = jsonObject.getInt("followers");
            int gender = jsonObject.getInt("gender");
            int followings = jsonObject.getInt("followings");
            int createdPosts = jsonObject.getInt("createdPosts");
            String socialAccount = jsonObject.getString("socialAccount");
            String userId = jsonObject.getString("userId");

            JSONObject fields = new JSONObject();
            fields.put("Name", name);
            fields.put("Score", score);
            fields.put("Comments", comments);
            fields.put("Followers", followers);
            fields.put("Gender", gender);
            fields.put("Followings", followings);
            fields.put("CreatedPosts", createdPosts);
            fields.put("SocialAccount", socialAccount);
            fields.put("UserId", userId);

            JSONObject requestBody = new JSONObject();
            requestBody.put("fields", fields);

            RequestBody requestBodyObject = RequestBody.create(mediaType, requestBody.toString());

            Request request = new Request.Builder()
                    .url("https://api.airtable.com/v0/" + BASE_ID + "/" + TABLE_NAME)
                    .post(requestBodyObject)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();
            //System.out.println(response.body().string());
        }
    }
}
