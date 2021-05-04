package com.example.new_mynews.ui.News.Headline_One;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.new_mynews.R;
import com.example.new_mynews.ViewHolder;
import com.example.new_mynews.Web_Window;
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
 * Use the {@link Headline_ONE#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Headline_ONE extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Headline_ONE() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Headline_ONE.
     */
    // TODO: Rename and change types and number of parameters
    public static Headline_ONE newInstance(String param1, String param2) {
        Headline_ONE fragment = new Headline_ONE();
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

    static ListView listView_Headline_ONE;
    static List<News_Headline_Model_ONE> news_headline_model_oneList = new ArrayList<>();
    static Fragment_Headline_PagerAdapter fragment_headline_pagerAdapter;
    static Handler handler_ONE;
    Intent intent;
    static News_Headline_Model_ONE news_headline_model_one;
    static Headline_AsyncTask_ONE headline_asyncTask_one;
    static View view_Headline_ONE, image_Page;
    static AutoScrollViewPager image_ViewPager;
    static List<Fragment_Pager_Model> fragmentPager_modelList = new ArrayList<>();
    CommonAdapter commonAdapter;
    static ProgressBar progressBar_Headline_ONE;
    static TextView textView_Headline_ONE;
    static String save_List_ONE, save_Page_ONE;
    static Gson gson_ONE = new Gson();
    static SharedPreferences sharedPreferences_ONE;
    static SharedPreferences.Editor editor_ONE;
    static FragmentManager fragmentManager_Headline_ONE;
    static FragmentActivity fragmentActivity_Headline_ONE;
    static boolean Load_one = false;
    boolean dot = true;

    public boolean isDot() {
        return dot;
    }

    public void setDot(boolean dot) {
        this.dot = dot;
    }

    public static List<Fragment_Pager_Model> getFragmentPager_modelList() {
        return fragmentPager_modelList;
    }

    public static void setFragmentPager_modelList(List<Fragment_Pager_Model> fragmentPager_modelList) {
        Headline_ONE.fragmentPager_modelList = fragmentPager_modelList;
    }

    public static boolean isLoad_one() {
        return Load_one;
    }

    public static void setLoad_one(boolean load_one) {
        Load_one = load_one;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_Headline_ONE = inflater.inflate(R.layout.fragment_headline_one, container, false);
        listView_Headline_ONE = view_Headline_ONE.findViewById(R.id.listView_Headline_ONE);
        progressBar_Headline_ONE = view_Headline_ONE.findViewById(R.id.progressBar_Headline_ONE);
        textView_Headline_ONE = view_Headline_ONE.findViewById(R.id.textView_Headline_ONE);
        image_Page = getLayoutInflater().inflate(R.layout.headline_viewpager, null, false);
        image_ViewPager = image_Page.findViewById(R.id.image_ViewPager);
        fragmentActivity_Headline_ONE = (FragmentActivity) view_Headline_ONE.getContext();
        fragmentManager_Headline_ONE = fragmentActivity_Headline_ONE.getSupportFragmentManager();
        handler_ONE = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    commonAdapter = new CommonAdapter<News_Headline_Model_ONE>(getActivity(), news_headline_model_oneList, R.layout.item_news_headline_one) {
                        @Override
                        public void convert(ViewHolder helper, News_Headline_Model_ONE news_headline_model) {
                            helper.setText(R.id.title_Headline_ONE, news_headline_model.getTitle_Headline());
                            helper.setText(R.id.time_Headline_ONE, news_headline_model.getTime_Headline());
                            helper.setImageUrl_glide(R.id.image_Headline_ONE, getActivity(), news_headline_model.getImage_Headline());
                        }
                    };
                    listView_Headline_ONE.setAdapter(commonAdapter);
                }
                if (msg.what == 8) {
                    image_ViewPager.clearAnimation();
                    fragment_headline_pagerAdapter = new Fragment_Headline_PagerAdapter(getActivity(), fragmentPager_modelList);
                    image_ViewPager.setAdapter(fragment_headline_pagerAdapter);

                }
            }
        };

        listView_Headline_ONE.addHeaderView(image_Page);
        listView_Headline_ONE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), Web_Window.class);
                intent.putExtra("Load_Url", news_headline_model_oneList.get(position-1).getUrl());
                startActivity(intent);
            }
        });


        sharedPreferences_ONE = getActivity().getSharedPreferences("Headline_ONE", MODE_PRIVATE);
        editor_ONE = sharedPreferences_ONE.edit();
        save_List_ONE = sharedPreferences_ONE.getString("Headline_ONE_List", "");
        save_Page_ONE = sharedPreferences_ONE.getString("Headline_ONE_Page", "");
        if(!save_Page_ONE.equals("")){
            fragmentPager_modelList = gson_ONE.fromJson(save_Page_ONE, new TypeToken<List<Fragment_Pager_Model>>() {}.getType());
            Message msg = Message.obtain();
            msg.what = 8;
            handler_ONE.sendMessage(msg);
        }else{
            Json_Page_Clean();
        }
        if (!save_List_ONE.equals("")) {
            news_headline_model_oneList = gson_ONE.fromJson(save_List_ONE, new TypeToken<List<News_Headline_Model_ONE>>() {}.getType());
            listView_Headline_ONE.setVisibility(View.VISIBLE);
            Message msg = Message.obtain();
            msg.what = 1;
            handler_ONE.sendMessage(msg);
            Load_one = true;
        } else {
            headline_asyncTask_one = new Headline_AsyncTask_ONE();
            headline_asyncTask_one.execute();
        }

        return view_Headline_ONE;
    }

    public static void Json_Page_Clean() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("https://gamejilu.com/category/giveaways").get();
                    Elements elementsItem = document.select("div.article-card.box-shadow");
                    Elements elementsUrl = document.select("h1.article-card_title");
                    for (int i = 0; i < elementsItem.size(); i++) {
                        String title = elementsItem.get(i).select("h1.article-card_title").text();
                        String img = elementsItem.get(i).select("img.img-fluid").attr("src");
                        String img_Replace = img.replace("/media", "https://gamejilu.com/media");
                        String url = elementsUrl.get(i).select("a").attr("href");
                        String url_Replace = url.replace("/giveaways", "https://gamejilu.com/giveaways");

                        Fragment_Pager_Model fragmentPager_model = new Fragment_Pager_Model(title, img_Replace, url_Replace);
                        fragmentPager_modelList.add(fragmentPager_model);

                    }
                    editor_ONE.putString("Headline_ONE_Page", gson_ONE.toJson(fragmentPager_modelList));
                    editor_ONE.commit();
                    Message msg = Message.obtain();
                    msg.what = 8;
                    handler_ONE.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public static void Json_Image_Clear() {
        fragmentPager_modelList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("https://gamejilu.com/category/giveaways").get();
                    Elements elementsItem = document.select("div.article-card.box-shadow");
                    Elements elementsUrl = document.select("h1.article-card_title");
                    for (int i = 0; i < elementsItem.size(); i++) {
                        String title = elementsItem.get(i).select("h1.article-card_title").text();
                        String img = elementsItem.get(i).select("img.img-fluid").attr("src");
                        String img_Replace = img.replace("/media", "https://gamejilu.com/media");
                        String url = elementsUrl.get(i).select("a").attr("href");
                        String url_Replace = url.replace("/giveaways", "https://gamejilu.com/giveaways");

                        Fragment_Pager_Model fragmentPager_model = new Fragment_Pager_Model(title, img_Replace, url_Replace);
                        fragmentPager_modelList.add(fragmentPager_model);

                    }
                    editor_ONE.putString("Headline_ONE_Page", gson_ONE.toJson(fragmentPager_modelList));
                    editor_ONE.commit();
                    Message msg = Message.obtain();
                    msg.what = 8;
                    handler_ONE.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static class Headline_AsyncTask_ONE extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... integers) {

            try {
                while (news_headline_model_oneList.size() == 0){
                    Document document = Jsoup.connect("https://news.google.com/topics/CAAqKggKIiRDQkFTRlFvSUwyMHZNRFZxYUdjU0JYcG9MVlJYR2dKVVZ5Z0FQAQ?hl=zh-TW&gl=TW&ceid=TW%3Azh-Hant").get();
                    Elements elementsItem = document.select("div.xrnccd.F6Welf.R7GTQ.keNKEd.j7vNaf");
                    for (int i = 0; i < elementsItem.size(); i++) {
                        String title = elementsItem.get(i).select("h3.ipQwMb.ekueJc.RD0gLb").text();
                        String img = elementsItem.get(i).select("img").attr("src");
                        String url = elementsItem.get(i).select("a").attr("href");
                        String url_Replace = url.replace("./articles", "https://news.google.com/articles");

                        if (title.length() > 35) {
                            String title_shorten = title.substring(0, 35) + "...";
                            news_headline_model_one = new News_Headline_Model_ONE(title_shorten, img, null, url_Replace);
                            news_headline_model_oneList.add(news_headline_model_one);
                        } else {
                            news_headline_model_one = new News_Headline_Model_ONE(title, img, null, url_Replace);
                            news_headline_model_oneList.add(news_headline_model_one);
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... integers) {
            super.onProgressUpdate(integers);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            news_headline_model_oneList.clear();
            editor_ONE.clear();
            editor_ONE.commit();
            progressBar_Headline_ONE.setVisibility(View.VISIBLE);
            textView_Headline_ONE.setVisibility(View.VISIBLE);
            listView_Headline_ONE.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Integer integers) {
            super.onPostExecute(integers);
            progressBar_Headline_ONE.setVisibility(View.GONE);
            textView_Headline_ONE.setVisibility(View.GONE);
            Message msg = Message.obtain();
            msg.what = 1;
            handler_ONE.sendMessage(msg);
            listView_Headline_ONE.setVisibility(View.VISIBLE);
            editor_ONE.putString("Headline_ONE_List", gson_ONE.toJson(news_headline_model_oneList));
            editor_ONE.commit();
            while (!Load_one){
                Load_one = true;
            }

        }
    }
}