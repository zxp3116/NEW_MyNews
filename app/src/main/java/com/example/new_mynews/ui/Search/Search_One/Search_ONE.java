package com.example.new_mynews.ui.Search.Search_One;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.new_mynews.CommonAdapter;
import com.example.new_mynews.DateUtil;
import com.example.new_mynews.R;
import com.example.new_mynews.ViewHolder;
import com.example.new_mynews.Web_Window;
import com.example.new_mynews.ui.Search.News_Search;
import com.github.hymanme.tagflowlayout.OnTagClickListener;
import com.github.hymanme.tagflowlayout.TagAdapter;
import com.github.hymanme.tagflowlayout.TagFlowLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Search_ONE#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_ONE extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Search_ONE() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_ONE.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_ONE newInstance(String param1, String param2) {
        Search_ONE fragment = new Search_ONE();
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

    static View view;
    static ListView listView_Search_ONE;
    static FragmentActivity fragmentActivity;
    static FragmentManager fragmentManager;
    static News_Search news_search = new News_Search();
    static News_Search_Model_ONE news_search_model_one;
    static List<News_Search_Model_ONE> news_search_model_oneList = new ArrayList<>();
    static Handler handler;
    static CommonAdapter commonAdapter;
    static ProgressBar progressBar_Search_ONE;
    static TextView textView_Search_ONE;
    static List<TagFlow_Model> search_Popular_List = new ArrayList<>();
    static List<String> search_Recording_List = new ArrayList<>();
    static ListView listView_Recording;
    TagFlowLayout tagFlowLayout;
    Intent intent;
    LayoutInflater layoutInflater;
    TextView text_TagFlow;
    ImageView image_Recording_Clear;
    static TagFlow_Model tagFlowModel;
    MyTagAdapter tagAdapter;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    static Gson gson = new Gson();

    static RelativeLayout relativeLayout_Search;

    public static List<String> getSearch_Recording_List() {
        return search_Recording_List;
    }

    public static void setSearch_Recording_List(List<String> search_Recording_List) {
        Search_ONE.search_Recording_List = search_Recording_List;
    }

    public static List<News_Search_Model_ONE> getNews_search_model_oneList() {
        return news_search_model_oneList;
    }

    public static void setNews_search_model_oneList(List<News_Search_Model_ONE> news_search_model_oneList) {
        Search_ONE.news_search_model_oneList = news_search_model_oneList;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_one, container, false);
        listView_Search_ONE = view.findViewById(R.id.listView_Search_ONE);
        progressBar_Search_ONE = view.findViewById(R.id.progressBar_Search_ONE);
        textView_Search_ONE = view.findViewById(R.id.textView_Search_ONE);
        listView_Recording = view.findViewById(R.id.listView_Recording);
        tagFlowLayout = view.findViewById(R.id.tagFlowLayout);

        tagFlowLayout.setTitle("熱門新聞");
        tagFlowLayout.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        tagFlowLayout.setTitleTextSize(20);
        tagFlowLayout.setMinVisibleHeight(100);
        tagFlowLayout.setMaxVisibleHeight(400);
        tagFlowLayout.setAnimationDuration(600);
        tagFlowLayout.setBackGroundColor(getResources().getColor(R.color.primary_text));

        relativeLayout_Search = view.findViewById(R.id.relativeLayout_Search);
        image_Recording_Clear = view.findViewById(R.id.image_Recording_Clear);
        fragmentActivity = (FragmentActivity) view.getContext();
        layoutInflater = LayoutInflater.from(getActivity());
        fragmentManager = fragmentActivity.getSupportFragmentManager();


        sharedPreferences = getActivity().getSharedPreferences("searchData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String listJson = sharedPreferences.getString("listSearchData", "");
        String Popular_Data = sharedPreferences.getString("Popular_List", "");
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 3) {
                    commonAdapter = new CommonAdapter<News_Search_Model_ONE>(getActivity(), news_search_model_oneList, R.layout.item_news_search_one) {
                        @Override
                        public void convert(ViewHolder helper, News_Search_Model_ONE news_search_model_one) {
                            helper.setText(R.id.searchTitle_Search, news_search_model_one.getSearchTitle());
                            helper.setText(R.id.pubDate_Search, news_search_model_one.getPubDate());
                            //helper.setImageUrl_glide(R.id.source_search,getActivity(),news_search_model_one.getSource());

                        }
                    };
                    listView_Search_ONE.setAdapter(commonAdapter);

                }
                if (msg.what == 9) {
                    commonAdapter = new CommonAdapter<String>(getActivity(), search_Recording_List, R.layout.item_recording) {
                        @Override
                        public void convert(ViewHolder helper, String string) {
                            helper.setText(R.id.item_Text_Recording, string);
                            helper.setImageClick_Clear(R.id.item_Image_Recording, search_Recording_List, commonAdapter, getActivity());

                        }
                    };
                    listView_Recording.setAdapter(commonAdapter);
                }
                if (msg.what == 10) {
                    tagAdapter = new MyTagAdapter();
                    tagFlowLayout.setTagAdapter(tagAdapter);
                    tagAdapter.addAllTags(search_Popular_List);
                }
            }
        };
        listView_Search_ONE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), Web_Window.class);
                intent.putExtra("Load_Url", news_search_model_oneList.get(position).getLink());
                startActivity(intent);
            }
        });
        listView_Recording.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                news_search.getEditText_Search().setText(search_Recording_List.get(position));
            }
        });
        if (!listJson.equals("")) {
            search_Recording_List = gson.fromJson(listJson, new TypeToken<List<String>>() {
            }.getType());
            Message msg = Message.obtain();
            msg.what = 9;
            handler.sendMessage(msg);
        }
        if (!Popular_Data.equals("")) {
            search_Popular_List = gson.fromJson(Popular_Data, new TypeToken<List<TagFlow_Model>>() {
            }.getType());
            Message msg = Message.obtain();
            msg.what = 10;
            handler.sendMessage(msg);
        } else {
            Popular_Search();
        }

        image_Recording_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_Recording_List.clear();
                editor.clear();
                editor.commit();
                Message msg = Message.obtain();
                msg.what = 9;
                handler.sendMessage(msg);
            }
        });

        tagFlowLayout.setTagListener(new OnTagClickListener() {
            @Override
            public void onClick(View view, int position) {
                news_search.getEditText_Search().setText(search_Popular_List.get(position).getText());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        });

        return view;
    }

    private static void Popular_Search() {
        search_Popular_List.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect("https://news.google.com/topstories?hl=zh-TW&gl=TW&ceid=TW%3Azh-Hant").get();
                    Document document_Rss = Jsoup.connect("https://trends.google.com.tw/trends/trendingsearches/daily/rss?geo=TW").get();

                    Elements elements_boy4he = document.select("a.boy4he");
                    Elements elements_Item = document_Rss.select("item");
                    for (int i = 0; i < elements_boy4he.size(); i++) {
                        String popular_Title_1 = elements_boy4he.get(i).select("a").attr("aria-label");

                        tagFlowModel = new TagFlow_Model(popular_Title_1);
                        search_Popular_List.add(tagFlowModel);
                    }
                    for (int i = 0; i < elements_Item.size(); i++) {
                        String popular_Title_2 = elements_Item.get(i).select("title").text();
                        tagFlowModel = new TagFlow_Model(popular_Title_2);
                        search_Popular_List.add(tagFlowModel);
                    }
                    Message msg = Message.obtain();
                    msg.what = 10;
                    handler.sendMessage(msg);
                    editor.putString("Popular_List", gson.toJson(search_Popular_List)); //存入json串
                    editor.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    public static class Search_AsyncTask_ONE extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Document document = Jsoup.connect("https://news.google.com/rss/search?q=" + news_search.getTextTitle() + "&hl=zh-TW&gl=TW&ceid=TW:zh-Hant").get();
                Elements elementsItem = document.select("item");
                for (int i = 0; i < elementsItem.size(); i++) {
                    String Searchtitle = elementsItem.get(i).select("title").text();
                    String PubDate = elementsItem.get(i).select("pubDate").text();
                    String Link = elementsItem.get(i).select("link").text();
                    String source = elementsItem.get(i).select("source").attr("url");
                    String year = PubDate.substring(12, 16);
                    String month = PubDate.substring(8, 11);
                    String day = PubDate.substring(5, 7);
                    String time = PubDate.substring(17, 25);
                    switch (month) {
                        case "Jan":
                            month = "01";
                            break;
                        case "Feb":
                            month = "02";
                            break;
                        case "Mar":
                            month = "03";
                            break;
                        case "Apr":
                            month = "04";
                            break;
                        case "May":
                            month = "05";
                            break;
                        case "Jun":
                            month = "06";
                            break;
                        case "Jul":
                            month = "07";
                            break;
                        case "Aug":
                            month = "08";
                            break;
                        case "Sep":
                            month = "09";
                            break;
                        case "Oct":
                            month = "10";
                            break;
                        case "Nov":
                            month = "11";
                            break;
                        case "Dec":
                            month = "12";
                            break;
                    }
                    String ChangeDate = year + "-" + month + "-" + day + " " + time;
                    String date = DateUtil.getShortTime(ChangeDate);
                    news_search_model_one = new News_Search_Model_ONE(ChangeDate, Searchtitle, date, Link, null);
                    news_search_model_oneList.add(news_search_model_one);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar_Search_ONE.setVisibility(View.VISIBLE);
            textView_Search_ONE.setVisibility(View.VISIBLE);
            relativeLayout_Search.setVisibility(View.GONE);
            listView_Search_ONE.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Message msg = Message.obtain();
            msg.what = 3;
            handler.sendMessage(msg);
            Collections.sort(news_search_model_oneList, new Comparator<News_Search_Model_ONE>() {
                @Override
                public int compare(News_Search_Model_ONE o1, News_Search_Model_ONE o2) {
                    Date date01 = DateUtil.stringToDate(o1.getChangeDate());
                    Date date02 = DateUtil.stringToDate(o2.getChangeDate());
                    if (date01.before(date02)) {
                        return 1;
                    }
                    return -1;
                }
            });
            progressBar_Search_ONE.setVisibility(View.GONE);
            textView_Search_ONE.setVisibility(View.GONE);
            listView_Search_ONE.setVisibility(View.VISIBLE);

            String strJson = gson.toJson(search_Recording_List);
            editor.putString("listSearchData", strJson);
            editor.commit();
            Popular_Search();
        }
    }

    class MyTagAdapter extends TagAdapter<TagFlow_Model> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            text_TagFlow = (TextView) layoutInflater.inflate(R.layout.text_tagflow, parent, false);
            text_TagFlow.setText(search_Popular_List.get(position).getText());
            return text_TagFlow;
        }
    }
}