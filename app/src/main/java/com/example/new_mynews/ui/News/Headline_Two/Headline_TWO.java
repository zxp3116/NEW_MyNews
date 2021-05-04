package com.example.new_mynews.ui.News.Headline_Two;

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
 * Use the {@link Headline_TWO#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Headline_TWO extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Headline_TWO() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Headline_TWO.
     */
    // TODO: Rename and change types and number of parameters
    public static Headline_TWO newInstance(String param1, String param2) {
        Headline_TWO fragment = new Headline_TWO();
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
    static View view_Headline_TWO;
    static List<News_Headline_Model_TWO> news_headline_model_twoList = new ArrayList<>();
    static News_Headline_Model_TWO news_headline_model_two;
    static Headline_AsyncTask_TWO headline_asyncTask_two;
    CommonAdapter commonAdapter;
    static Handler handler_TWO;
    Intent intent;
    static ListView listView_Headline_TWO;
    static ProgressBar progressBar_Headline_TWO;
    static TextView textView_Headline_TWO;
    static boolean Load_two = false;
    static String save_List_TWO;
    static Gson gson_TWO = new Gson();
    static SharedPreferences sharedPreferences_TWO;
    static SharedPreferences.Editor editor_TWO;
    static FragmentManager fragmentManager_Headline_TWO;
    static FragmentActivity fragmentActivity_Headline_TWO;

    public static boolean isLoad_two() {
        return Load_two;
    }

    public static void setLoad_two(boolean load_two) {
        Load_two = load_two;
    }

    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_Headline_TWO = inflater.inflate(R.layout.fragment_headline_two, container, false);
        listView_Headline_TWO = view_Headline_TWO.findViewById(R.id.listView_Headline_TWO);
        progressBar_Headline_TWO = view_Headline_TWO.findViewById(R.id.progressBar_Headline_TWO);
        textView_Headline_TWO = view_Headline_TWO.findViewById(R.id.textView_Headline_TWO);
        fragmentActivity_Headline_TWO = (FragmentActivity)view_Headline_TWO.getContext();
        fragmentManager_Headline_TWO = fragmentActivity_Headline_TWO.getSupportFragmentManager();
        listView_Headline_TWO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), Web_Window.class);
                intent.putExtra("Load_Url",news_headline_model_twoList.get(position).getUrl());
                startActivity(intent);
            }
        });
        handler_TWO = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if(msg.what == 2){
                    commonAdapter = new CommonAdapter<News_Headline_Model_TWO>(getActivity(),news_headline_model_twoList,R.layout.item_news_headline_two) {
                        @Override
                        public void convert(ViewHolder helper, News_Headline_Model_TWO news_headline_model) {
                            helper.setText(R.id.title_Headline_TWO,news_headline_model.getTitle_Headline());
                            helper.setText(R.id.time_Headline_TWO,news_headline_model.getTime_Headline());
                            helper.setImageUrl_glide(R.id.image_Headline_TWO,getActivity(),news_headline_model.getImage_Headline());
                        }
                    };
                    listView_Headline_TWO.setAdapter(commonAdapter);

                }
            }
        };
        sharedPreferences_TWO = getActivity().getSharedPreferences("Headline_TWO",MODE_PRIVATE);
        editor_TWO = sharedPreferences_TWO.edit();
        save_List_TWO = sharedPreferences_TWO.getString("Headline_TWO_List","");
        if (!save_List_TWO.equals("")){
            news_headline_model_twoList = gson_TWO.fromJson(save_List_TWO, new TypeToken<List<News_Headline_Model_TWO>>() {}.getType());
            listView_Headline_TWO.setVisibility(View.VISIBLE);
            Message msg = Message.obtain();
            msg.what=2;
            handler_TWO.sendMessage(msg);
            Load_two = true;
        }else{
            if(news_headline_model_twoList.size()<1){
                headline_asyncTask_two  = new Headline_AsyncTask_TWO();
                headline_asyncTask_two.execute();
            }
        }

        return view_Headline_TWO;
    }


    public static class Headline_AsyncTask_TWO extends AsyncTask<Integer,Integer,Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            try{
                while(news_headline_model_twoList.size() == 0){
                    Document document = Jsoup.connect("https://news.google.com/topics/CAAqKggKIiRDQkFTRlFvSUwyMHZNRGx1YlY4U0JYcG9MVlJYR2dKVVZ5Z0FQAQ?hl=zh-TW&gl=TW&ceid=TW%3Azh-Hant").get();
                    Elements elementsItem = document.select("div.xrnccd.F6Welf.R7GTQ.keNKEd.j7vNaf");
                    for(int i=0;i<elementsItem.size();i++){
                        String title = elementsItem.get(i).select("h3.ipQwMb.ekueJc.RD0gLb").text();
                        String img = elementsItem.get(i).select("img").attr("src");
                        String url = elementsItem.get(i).select("a").attr("href");
                        String url_Replace = url.replace("./articles","https://news.google.com/articles");

                        if(title.length()>35){
                            String title_shorten = title.substring(0,35)+"...";
                            news_headline_model_two= new News_Headline_Model_TWO(title_shorten, img,null,url_Replace);
                            news_headline_model_twoList.add(news_headline_model_two);
                        }else{
                            news_headline_model_two = new News_Headline_Model_TWO(title, img, null, url_Replace);
                            news_headline_model_twoList.add(news_headline_model_two);
                        }
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            news_headline_model_twoList.clear();
            editor_TWO.clear();
            editor_TWO.commit();
            save_List_TWO = "";
            progressBar_Headline_TWO.setVisibility(View.VISIBLE);
            textView_Headline_TWO.setVisibility(View.VISIBLE);
            listView_Headline_TWO.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Integer integers) {
            super.onPostExecute(integers);
            progressBar_Headline_TWO.setVisibility(View.GONE);
            textView_Headline_TWO.setVisibility(View.GONE);
            Message msg = Message.obtain();
            msg.what=2;
            handler_TWO.sendMessage(msg);
            listView_Headline_TWO.setVisibility(View.VISIBLE);
            if(news_headline_model_twoList.size()>0){
                editor_TWO.putString("Headline_TWO_List",gson_TWO.toJson(news_headline_model_twoList));
                editor_TWO.commit();

            }
            while (!Load_two){
                Load_two = true;
            }
        }
    }
}