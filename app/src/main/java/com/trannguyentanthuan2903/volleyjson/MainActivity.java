package com.trannguyentanthuan2903.volleyjson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnGetJS;
    TextView txShow;
    ListView lv;
    ArrayList<Video> mangVideo;
    Adapter adapter;
    String url = "http://api.androidhive.info/volley/person_object.json";
    String link = "http://api.androidhive.info/volley/person_array.json";
    String API_KEY = "AIzaSyAWsznXl-eBFZt6lODoO9uXfnXZr5gZ6KI";
    String ID = "PLflLyFbqtQN9U5qHI372cA_KLZxCx2rK8";
    String linkYouTube = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + ID + "&key=" + API_KEY + "&maxResults=50";

    // ddổi ID và API_KEY bằng cách +
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txShow = (TextView) findViewById(R.id.textViewShow);
        btnGetJS = (Button) findViewById(R.id.buttonGet);
        lv = (ListView) findViewById(R.id.listView);
        mangVideo = new ArrayList<>();


        btnGetJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                obJectVolley(url);
//                arrayObject(link);
                strinObject(linkYouTube);
            }
        });
    }

    private void strinObject(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arrayItem = response.getJSONArray("items");
                            for (int i = 0; i < arrayItem.length(); i++) {
                                JSONObject item = arrayItem.getJSONObject(i);
                                JSONObject snippet = item.getJSONObject("snippet");
                                String title = snippet.getString("title");
                                String date = snippet.getString("publishedAt");
                                JSONObject thumnail = snippet.getJSONObject("thumbnails");
                                JSONObject defau = thumnail.getJSONObject("default");
                                String url = defau.getString("url");
                                mangVideo.add(new Video(title, date, url));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter = new Adapter(MainActivity.this, R.layout.row_view, mangVideo);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi : " + error, Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObject);
    }

    private void arrayObject(String url) {
        final RequestQueue requetsQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayReQuest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = (JSONObject) response.getJSONObject(i);
                                String name = object.getString("name");
                                String email = object.getString("email");
                                JSONObject phone = object.getJSONObject("phone");
                                String home = phone.getString("home");
                                String mobile = phone.getString("mobile");
                                txShow.setText(name + email + home + mobile);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requetsQueue.add(jsonArrayReQuest);
    }

    private void obJectVolley(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            String email = response.getString("email");
                            JSONObject phone = response.getJSONObject("phone");
                            String home = phone.getString("home");
                            String mobile = phone.getString("mobile");

                            txShow.setText("Name: " + name
                                    + " Email: " + email
                                    + " Phone: "
                                    + " Home: " + home
                                    + " Mobile: " + mobile
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi " + error, Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}
