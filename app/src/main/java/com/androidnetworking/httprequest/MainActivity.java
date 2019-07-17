package com.androidnetworking.httprequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import static com.androidnetworking.httprequest.Constants.BASE_URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getLoad(View view) {
        MyAsynTask myAsynTask = new MyAsynTask();
        myAsynTask.execute(
                "http://asian.dotplays.com/wp-json/wp/v2/posts?embed");


    }

    public class MyAsynTask extends AsyncTask<String, Long, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();

                Scanner scanner = new Scanner(inputStream);

                String data = "";


                while (scanner.hasNext()) {

                    data = scanner.nextLine() + data;

                }
                return data;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //cach 1
//            try {
//
//                List<Post> postList = new ArrayList();
//                JSONArray root = new JSONArray(s);
//                Log.e("Root size", String.valueOf(root.length()));
//                for (int i = 0; i < root.length(); i++) {
//                    JSONObject post = root.getJSONObject(i);
//                    int id = post.getInt("id");
//                    Log.e("id", String.valueOf(id));
//                    String date = post.getString("date");
//                    Log.e("date", String.valueOf(date));
//
//                    JSONObject title = post.getJSONObject("title");
//                    String rendered = title.getString("rendered");
//                    Log.e("title", rendered);
//
//                    Post postModel = new Post();
//                    postModel.id = id;
//                    postModel.date = date;
//                    postModel.title = rendered;
//                    postList.add(postModel);
//
//                }
//                Log.e("PostSize", postList.size() + "");
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }



            List<Post> postList = new ArrayList<>();
            JsonParser jsonParser = new JsonParser();

            JsonElement jsonElement = jsonParser.parse(s);

            JsonArray root = jsonElement.getAsJsonArray();

            for (int i = 0; i < root.size(); i++) {
                JsonObject post = root.get(i).getAsJsonObject();

                int id = post.get("id").getAsInt();
                Log.e("id", String.valueOf(id));

                String date = post.get("date").getAsString();
                Log.e("date", String.valueOf(date));

                JsonObject title = post.get("title").getAsJsonObject();
                String rendered = title.get("rendered").getAsString();

                Post postModel = new Post();

                postModel.id = id;
                postModel.date = date;
                postModel.title = rendered;

                postList.add(postModel);


            }
            Log.e("PostSize", postList.size() + "");

        }
    }
}
