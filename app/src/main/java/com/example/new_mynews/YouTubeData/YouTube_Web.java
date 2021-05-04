package com.example.new_mynews.YouTubeData;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_mynews.CommonAdapter;
import com.example.new_mynews.DateUtil;
import com.example.new_mynews.R;
import com.example.new_mynews.TitlebarView;
import com.example.new_mynews.ViewHolder;
import com.example.new_mynews.ui.Setting.News_Setting;
import com.google.gson.Gson;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import iammert.com.expandablelib.ExpandableLayout;

public class YouTube_Web extends AppCompatActivity {
    static YouTubePlayerView pierfrancescosoffritti;
    String Video_ID, API_KEY;
    TitlebarView titlebarView;
    YouTube_AsyncTask youTube_asyncTask;
    List<YouTube_Model> youTube_modelList = new ArrayList<>();
    YouTube_Model youTube_model;
    CommonAdapter commonAdapter;
    Handler handler;
    ListView listView_YouTube;
    ProgressBar progressBar_YouTube;
    TextView textView_YouTube;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    News_Setting news_setting = new News_Setting();
    ExpandableLayout expandableLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_web);
        getSupportActionBar().hide();
        titlebarView = findViewById(R.id.titlebarView_YouTube);
        listView_YouTube = findViewById(R.id.listView_YouTube);
        progressBar_YouTube = findViewById(R.id.progressBar_YouTube);
        textView_YouTube = findViewById(R.id.textView_YouTube);
        pierfrancescosoffritti = findViewById(R.id.pierfrancescosoffritti);
        //getLifecycle().addObserver(pierfrancescosoffritti);
        API_KEY = "AIzaSyAIRU_kiok7A3LZe6aGJcNZRzXJWWy48cQ";
        Video_ID = getIntent().getStringExtra("youtube_VideoID");
        sharedPreferences = getSharedPreferences("Setting", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("YouTube_BackgroundPlay", false)) {
            pierfrancescosoffritti.enableBackgroundPlayback(true);
        } else {
            pierfrancescosoffritti.enableBackgroundPlayback(false);
        }

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 5) {
                    commonAdapter = new CommonAdapter<YouTube_Model>(YouTube_Web.this, youTube_modelList, R.layout.item_youtube_message) {
                        @Override
                        public void convert(ViewHolder viewHolder, YouTube_Model youTube_model) {
                            viewHolder.setCircle_glide(R.id.image_message, YouTube_Web.this, youTube_model.getImage_message());
                            viewHolder.setText(R.id.name_message, youTube_model.getName_message());
                            viewHolder.setText(R.id.text_message, youTube_model.getText_message());
                            viewHolder.setText(R.id.thumb_Num_message, youTube_model.getThumb_Num_message());
                            viewHolder.setText(R.id.time_message, youTube_model.getTime_message());
                            viewHolder.setImageClick_Intent(R.id.image_message, YouTube_Web.this, youTube_model.getName_link());
                            viewHolder.setTextClick_Intent(R.id.name_message, YouTube_Web.this, youTube_model.getName_link());
                        }
                    };
                    listView_YouTube.setAdapter(commonAdapter);
                }
            }
        };
        pierfrancescosoffritti.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {

                super.onReady(youTubePlayer);
                youTubePlayer.loadVideo(Video_ID, 0);
            }
        });
        titlebarView.setOnViewClick(new TitlebarView.onViewClick() {
            @Override
            public void image_Click() {
                finish();
            }
        });
        youTube_asyncTask = new YouTube_AsyncTask();
        youTube_asyncTask.execute();
    }

    public class YouTube_AsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                String url = "https://www.googleapis.com/youtube/v3/commentThreads?part=snippet,replies&videoId=" + Video_ID + "&key=" + API_KEY;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray items = jsonObject.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject itemsJSONObject = items.getJSONObject(i);
                                JSONObject snippet = itemsJSONObject.getJSONObject("snippet");
                                JSONObject topLevelComment = snippet.getJSONObject("topLevelComment");
                                JSONObject snippet_1 = topLevelComment.getJSONObject("snippet");
                                String textOriginal = snippet_1.getString("textOriginal");
                                String authorProfileImageUrl = snippet_1.getString("authorProfileImageUrl");
                                String authorDisplayName = snippet_1.getString("authorDisplayName");
                                String authorChannelUrl = snippet_1.getString("authorChannelUrl");
                                String publishedAt = snippet_1.getString("publishedAt");
                                String likeCount = snippet_1.getString("likeCount");
                                String date = publishedAt.substring(0, 10);
                                String time = publishedAt.substring(11, 19);
                                String dateData = date + " " + time;
                                String Date = DateUtil.getShortTime(dateData);
                                youTube_model = new YouTube_Model(authorProfileImageUrl, authorDisplayName, textOriginal, Date, authorChannelUrl, likeCount);
                                youTube_modelList.add(youTube_model);
                                commonAdapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(YouTube_Web.this);
                requestQueue.add(jsonObjectRequest);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Message msg = Message.obtain();
            msg.what = 5;
            handler.sendMessage(msg);
            listView_YouTube.setVisibility(View.VISIBLE);
        }
    }
}
