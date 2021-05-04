package com.example.new_mynews.ui.News.Ｈeadline_Three;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.new_mynews.CommonAdapter;
import com.example.new_mynews.DateUtil;
import com.example.new_mynews.R;
import com.example.new_mynews.ViewHolder;
import com.example.new_mynews.Web_Window;
import com.example.new_mynews.ui.News.Headline_One.News_Headline_Model_ONE;
import com.example.new_mynews.ui.News.Headline_Two.Headline_TWO;
import com.example.new_mynews.ui.News.Headline_Two.News_Headline_Model_TWO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Headline_THREE#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Headline_THREE extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Headline_THREE() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Headline_THREE.
     */
    // TODO: Rename and change types and number of parameters
    public static Headline_THREE newInstance(String param1, String param2) {
        Headline_THREE fragment = new Headline_THREE();
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

    static List<News_Headline_Model_THREE> news_headline_model_threeList = new ArrayList<>();
    static News_Headline_Model_THREE news_headline_model_three;
    static Headline_AsyncTask_THREE headline_asyncTask_three;
    CommonAdapter commonAdapter;
    static Handler handler_THREE;
    Intent intent;
    static ListView listView_Headline_THREE;
    static View view_Headline_THREE;
    static ProgressBar progressBar_Headline_THREE;
    static TextView textView_Headline_THREE;
    static boolean Load_three = false;
    static String save_List_THREE;
    static Gson gson_THREE = new Gson();
    static SharedPreferences sharedPreferences_THREE;
    static SharedPreferences.Editor editor_THREE;
    static FragmentManager fragmentManager_Headline_THREE;
    static FragmentActivity fragmentActivity_Headline_THREE;

    public static boolean isLoad_three() {
        return Load_three;
    }

    public static void setLoad_three(boolean load_three) {
        Load_three = load_three;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_Headline_THREE = inflater.inflate(R.layout.fragment_headline_three, container, false);
        listView_Headline_THREE = view_Headline_THREE.findViewById(R.id.listView_Headline_THREE);
        progressBar_Headline_THREE = view_Headline_THREE.findViewById(R.id.progressBar_Headline_THREE);
        textView_Headline_THREE = view_Headline_THREE.findViewById(R.id.textView_Headline_THREE);
        fragmentActivity_Headline_THREE = (FragmentActivity) view_Headline_THREE.getContext();
        fragmentManager_Headline_THREE = fragmentActivity_Headline_THREE.getSupportFragmentManager();
        listView_Headline_THREE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), Web_Window.class);
                intent.putExtra("Load_Url", news_headline_model_threeList.get(position).getUrl());
                startActivity(intent);
            }
        });


        handler_THREE = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 3) {
                    commonAdapter = new CommonAdapter<News_Headline_Model_THREE>(getActivity(), news_headline_model_threeList, R.layout.item_news_headline_three) {
                        @Override
                        public void convert(ViewHolder helper, News_Headline_Model_THREE news_headline_model) {
                            helper.setText(R.id.title_Headline_THREE, news_headline_model.getTitle_Headline());
                            helper.setText(R.id.time_Headline_THREE, news_headline_model.getTime_Headline());
                            helper.setImageUrl_glide(R.id.image_Headline_THREE, getActivity(), news_headline_model.getImage_Headline());
                        }
                    };
                    listView_Headline_THREE.setAdapter(commonAdapter);
                }
            }
        };
        sharedPreferences_THREE = getActivity().getSharedPreferences("Headline_THREE", MODE_PRIVATE);
        editor_THREE = sharedPreferences_THREE.edit();
        save_List_THREE = sharedPreferences_THREE.getString("Headline_THREE_List", "");
        if (!save_List_THREE.equals("")) {
            news_headline_model_threeList = gson_THREE.fromJson(save_List_THREE, new TypeToken<List<News_Headline_Model_THREE>>() {
            }.getType());
            listView_Headline_THREE.setVisibility(View.VISIBLE);
            Message msg = Message.obtain();
            msg.what = 3;
            handler_THREE.sendMessage(msg);
            Load_three = true;
        } else {
            if(news_headline_model_threeList.size()<1){
                headline_asyncTask_three = new Headline_AsyncTask_THREE();
                headline_asyncTask_three.execute();
            }
        }

        return view_Headline_THREE;
    }

    public static class Headline_AsyncTask_THREE extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                while(news_headline_model_threeList.size() == 0){
                    Document document = Jsoup.connect("https://news.google.com/topics/CAAqKggKIiRDQkFTRlFvSUwyMHZNRGx6TVdZU0JYcG9MVlJYR2dKVVZ5Z0FQAQ?hl=zh-TW&gl=TW&ceid=TW%3Azh-Hant").get();
                    Elements elementsItem = document.select("div.xrnccd.F6Welf.R7GTQ.keNKEd.j7vNaf");
                    for (int i = 0; i < elementsItem.size(); i++) {
                        String title = elementsItem.get(i).select("h3.ipQwMb.ekueJc.RD0gLb").text();
                        String img = elementsItem.get(i).select("img").attr("src");
                        String url = elementsItem.get(i).select("a").attr("href");
                        String url_Replace = url.replace("./articles", "https://news.google.com/articles");

                        if (title.length() > 35) {
                            String title_shorten = title.substring(0, 35) + "...";
                            news_headline_model_three = new News_Headline_Model_THREE(title_shorten, img, null, url_Replace);
                            news_headline_model_threeList.add(news_headline_model_three);
                        } else {
                            news_headline_model_three = new News_Headline_Model_THREE(title, img, null, url_Replace);
                            news_headline_model_threeList.add(news_headline_model_three);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            news_headline_model_threeList.clear();
            editor_THREE.clear();
            editor_THREE.commit();
            save_List_THREE = "";
            progressBar_Headline_THREE.setVisibility(View.VISIBLE);
            textView_Headline_THREE.setVisibility(View.VISIBLE);
            listView_Headline_THREE.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Integer integers) {
            super.onPostExecute(integers);
            progressBar_Headline_THREE.setVisibility(View.GONE);
            textView_Headline_THREE.setVisibility(View.GONE);
            Message msg = Message.obtain();
            msg.what = 3;
            handler_THREE.sendMessage(msg);
            listView_Headline_THREE.setVisibility(View.VISIBLE);
            editor_THREE.putString("Headline_THREE_List", gson_THREE.toJson(news_headline_model_threeList));
            editor_THREE.commit();
            while (!Load_three){
                Load_three = true;
            }
        }
    }
}