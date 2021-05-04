package com.example.new_mynews.ui.Search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.new_mynews.DateUtil;
import com.example.new_mynews.R;
import com.example.new_mynews.ui.Search.Search_One.Search_ONE;
import com.example.new_mynews.ui.Search.Search_Two.Search_TWO;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class News_Search extends Fragment {

    private DashboardViewModel dashboardViewModel;

    View root;
    TabLayout tabLayout_Search;
    ViewPager viewPager_Search;
    Search_ViewPager_Adapter search_viewPager_adapter;
    Toolbar toolBar_Search;
    static EditText editText_Search;
    ImageView imageView_Search, imageView_Voice;
    Search_ONE.Search_AsyncTask_ONE search_asyncTask_one;
    Search_TWO.Search_AsyncTask_TWO search_asyncTask_two;
    static String textTitle;
    Search_ONE search_one = new Search_ONE();
    Search_TWO search_two = new Search_TWO();
    Intent intent;
    static final int voice = 3000;

    public static EditText getEditText_Search() {
        return editText_Search;
    }

    public static void setEditText_Search(EditText editText_Search) {
        News_Search.editText_Search = editText_Search;
    }

    public static String getTextTitle() {
        return textTitle;
    }

    public static void setTextTitle(String textTitle) {
        News_Search.textTitle = textTitle;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        root = inflater.inflate(R.layout.news_search, container, false);
        tabLayout_Search = root.findViewById(R.id.tabLayout_Search);
        viewPager_Search = root.findViewById(R.id.viewPager_Search);
        toolBar_Search = root.findViewById(R.id.toolBar_Search);
        editText_Search = root.findViewById(R.id.editText_Search);
        imageView_Search = root.findViewById(R.id.imageView_Search);
        imageView_Voice = root.findViewById(R.id.imageView_Voice);
        imageView_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText_Search.getText().toString().trim().equals("")) {
                    search_one.getSearch_Recording_List().remove(editText_Search.getText().toString().trim());
                    search_one.getSearch_Recording_List().add(editText_Search.getText().toString().trim());
                    setTextTitle(editText_Search.getText().toString().trim());
                    search_one.getNews_search_model_oneList().clear();
                    search_two.getNews_search_model_twoList().clear();
                    search_asyncTask_one = new Search_ONE.Search_AsyncTask_ONE();
                    search_asyncTask_one.execute();
                    search_asyncTask_two = new Search_TWO.Search_AsyncTask_TWO();
                    search_asyncTask_two.execute();
                }
            }
        });

        imageView_Voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");
                try {
                    startActivityForResult(intent, voice);
                } catch (Exception e) {

                }
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        tabLayout_Search.addTab(tabLayout_Search.newTab().setText("新聞"));
        tabLayout_Search.addTab(tabLayout_Search.newTab().setText("影音"));
        tabLayout_Search.setTabGravity(TabLayout.GRAVITY_FILL);

        search_viewPager_adapter = new Search_ViewPager_Adapter(getFragmentManager(), tabLayout_Search.getTabCount());
        viewPager_Search.setAdapter(search_viewPager_adapter);
        //viewPager_Search.setOffscreenPageLimit(tabLayout_Search.getTabCount()-1);
        viewPager_Search.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_Search));
        tabLayout_Search.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_Search.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case voice:
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editText_Search.setText(result.get(0));

                break;
        }
    }
}