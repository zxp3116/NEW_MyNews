package com.example.new_mynews.ui.Search.Search_Two;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.new_mynews.CommonAdapter;
import com.example.new_mynews.DateUtil;
import com.example.new_mynews.R;
import com.example.new_mynews.ViewHolder;
import com.example.new_mynews.YouTubeData.YouTube_Web;
import com.example.new_mynews.ui.Search.News_Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search_TWO#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_TWO extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Search_TWO() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_TWO.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_TWO newInstance(String param1, String param2) {
        Search_TWO fragment = new Search_TWO();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    static FragmentActivity fragmentActivity ;
    static FragmentManager fragmentManager;
    static View view;
    static ListView listView_Search_TWO;
    static ProgressBar progressBar_Search_TWO;
    static TextView textView_Search_TWO;
    static News_Search news_search = new News_Search();
    static News_Search_Model_TWO news_search_model_two;
    static String API_KEY;
    static List<News_Search_Model_TWO> news_search_model_twoList = new ArrayList<>();
    static Handler handler;
    static CommonAdapter commonAdapter;
    Intent intent;
    static JSONObject snippet;
    static String title;
    static String title_Replace,title_Replace_4;
    static JSONObject id;
    public static List<News_Search_Model_TWO> getNews_search_model_twoList() {
        return news_search_model_twoList;
    }

    public static void setNews_search_model_twoList(List<News_Search_Model_TWO> news_search_model_twoList) {
        Search_TWO.news_search_model_twoList = news_search_model_twoList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_two, container, false);
        fragmentActivity = (FragmentActivity)view.getContext();
        fragmentManager = fragmentActivity.getSupportFragmentManager();
        listView_Search_TWO = view.findViewById(R.id.listView_Search_TWO);
        progressBar_Search_TWO = view.findViewById(R.id.progressBar_Search_TWO);
        textView_Search_TWO = view.findViewById(R.id.textView_Search_TWO);
        API_KEY = "AIzaSyAIRU_kiok7A3LZe6aGJcNZRzXJWWy48cQ";
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 4){
                    commonAdapter = new CommonAdapter<News_Search_Model_TWO>(getActivity(),news_search_model_twoList,R.layout.item_news_search_two) {
                        @Override
                        public void convert(ViewHolder viewHolder, News_Search_Model_TWO news_search_model_two) {
                            viewHolder.setText(R.id.title_Search_TWO,news_search_model_two.getTitile());
                            viewHolder.setText(R.id.time_Search_TWO,news_search_model_two.getTime());
                            viewHolder.setImageUrl_glide(R.id.image_Search_TWO,getActivity(),news_search_model_two.getImage_URL());
                        }
                    };
                    listView_Search_TWO.setAdapter(commonAdapter);
                }
            }
        };
        listView_Search_TWO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), YouTube_Web.class);
                intent.putExtra("youtube_VideoID",news_search_model_twoList.get(position).getId());
                startActivity(intent);
            }
        });

        return view;
    }

    public static class Search_AsyncTask_TWO extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            try{
                String  url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q="+news_search.getTextTitle()+"&key="+API_KEY+"&type=video&maxResults=100000";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try{
                            JSONArray items = jsonObject.getJSONArray("items");
                            for(int i=0;i<items.length();i++){
                                JSONObject itemsJSONObject = items.getJSONObject(i);
                                id = itemsJSONObject.getJSONObject("id");
                                snippet = itemsJSONObject.getJSONObject("snippet");
                                title = snippet.getString("title");
                                title_Replace = title.replace("&quot;","＂");
                                String title_Replace_1 = title.replace("&quot;","＂");
                                String title_Replace_2 = title_Replace_1.replace("&amp;","&");
                                String title_Replace_3 = title_Replace_2.replace("&lt;","<");
                                title_Replace_4 = title_Replace_3.replace("&gt;",">");
                                if(title_Replace_4.length()>40){
                                    String title_shorten = title_Replace_4.substring(0,40)+"...";
                                    ListAdd_Load(title_shorten);
                                }else{
                                    ListAdd_Load(title_Replace_4);
                                }

                            }
                            Collections.sort(news_search_model_twoList, new Comparator<News_Search_Model_TWO>() {
                                @Override
                                public int compare(News_Search_Model_TWO o1, News_Search_Model_TWO o2) {
                                    Date date01 = DateUtil.stringToDate(o1.getChangeDate());
                                    Date date02 = DateUtil.stringToDate(o2.getChangeDate());
                                    if (date01.before(date02)) {
                                        return 1;
                                    }
                                    return -1;
                                }
                            });
                            commonAdapter.notifyDataSetChanged();

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(fragmentActivity);
                requestQueue.add(jsonObjectRequest);

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        private static void ListAdd_Load(String string) throws JSONException {
            String publishedAt = snippet.getString("publishedAt");
            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
            JSONObject default_Json = thumbnails.getJSONObject("high");
            String videoId = id.getString("videoId");
            String high_Url = default_Json.getString("url");
            String date = publishedAt.substring(0, 10);
            String time = publishedAt.substring(11,19);
            String dateData = date+" "+time;
            String Date = DateUtil.getYouTubeTime(dateData);
            news_search_model_two = new News_Search_Model_TWO(dateData,string,high_Url,videoId,Date);
            news_search_model_twoList.add(news_search_model_two);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar_Search_TWO.setVisibility(View.VISIBLE);
            textView_Search_TWO.setVisibility(View.VISIBLE);
            listView_Search_TWO.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Message msg = Message.obtain();
            msg.what=4;
            handler.sendMessage(msg);

            progressBar_Search_TWO.setVisibility(View.GONE);
            textView_Search_TWO.setVisibility(View.GONE);
            listView_Search_TWO.setVisibility(View.VISIBLE);

        }
    }
}