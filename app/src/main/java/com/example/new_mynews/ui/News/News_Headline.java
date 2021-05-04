package com.example.new_mynews.ui.News;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.new_mynews.R;
import com.example.new_mynews.ui.News.Headline_One.AutoScrollViewPager;
import com.example.new_mynews.ui.News.Headline_One.Headline_ONE;
import com.example.new_mynews.ui.News.Headline_Two.Headline_TWO;

import com.example.new_mynews.ui.News.Ｈeadline_Three.Headline_THREE;
import com.google.android.material.tabs.TabLayout;

import static android.content.Context.MODE_PRIVATE;


public class News_Headline extends Fragment {


    private HomeViewModel homeViewModel;
    View root;
    TabLayout tabLayout_Headline;
    ViewPager viewPager_Headline;
    Headline_ViewPager_Adapter headline_viewPager_adapter;
    Toolbar toolBar_Headline;
    LinearLayout linearLayout_Refresh;
    Headline_ONE.Headline_AsyncTask_ONE headline_asyncTask_one;
    Headline_TWO.Headline_AsyncTask_TWO headline_asyncTask_two;
    Headline_THREE.Headline_AsyncTask_THREE headline_asyncTask_three;
    SharedPreferences sharedPreferences_one, sharedPreferences_two, sharedPreferences_three;
    SharedPreferences.Editor editor_one, editor_two, editor_three;
    Headline_ONE headline_one = new Headline_ONE();
    Headline_TWO headline_two = new Headline_TWO();
    Headline_THREE headline_three = new Headline_THREE();
    boolean asyncTack;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.news_headline, container, false);
        tabLayout_Headline = root.findViewById(R.id.tabLayout_Headline);
        viewPager_Headline = root.findViewById(R.id.viewPager_Headline);
        toolBar_Headline = root.findViewById(R.id.toolBar_Headline);
        linearLayout_Refresh = root.findViewById(R.id.linearLayout_Refresh);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        tabLayout_Headline.addTab(tabLayout_Headline.newTab().setText("頭條"));
        tabLayout_Headline.addTab(tabLayout_Headline.newTab().setText("國際"));
        tabLayout_Headline.addTab(tabLayout_Headline.newTab().setText("商業"));
        tabLayout_Headline.setTabGravity(TabLayout.GRAVITY_FILL);
        linearLayout_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data_Clear();
                if(headline_one.isLoad_one()&&headline_two.isLoad_two()&&headline_three.isLoad_three()){
                    headline_one.setLoad_one(false);
                    headline_two.setLoad_two(false);
                    headline_three.setLoad_three(false);
                    headline_asyncTask_one = new Headline_ONE.Headline_AsyncTask_ONE();
                    headline_asyncTask_one.execute();
                    headline_asyncTask_two = new Headline_TWO.Headline_AsyncTask_TWO();
                    headline_asyncTask_two.execute();
                    headline_asyncTask_three = new Headline_THREE.Headline_AsyncTask_THREE();
                    headline_asyncTask_three.execute();
                    if (headline_one.getFragmentPager_modelList().size() > 0) {
                        headline_one.Json_Image_Clear();
                    }
                }else{
                    Toast.makeText(getActivity(), "請稍後，正在加載中", Toast.LENGTH_SHORT).show();
                }
            }
        });
        headline_viewPager_adapter = new Headline_ViewPager_Adapter(getFragmentManager(), tabLayout_Headline.getTabCount());
        viewPager_Headline.setAdapter(headline_viewPager_adapter);
        viewPager_Headline.setOffscreenPageLimit(tabLayout_Headline.getTabCount() - 1);
        viewPager_Headline.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_Headline));
        tabLayout_Headline.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager_Headline.setCurrentItem(tab.getPosition());
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

    private void Data_Clear() {
        sharedPreferences_one = getActivity().getSharedPreferences("Headline_ONE", MODE_PRIVATE);
        sharedPreferences_two = getActivity().getSharedPreferences("Headline_TWO", MODE_PRIVATE);
        sharedPreferences_three = getActivity().getSharedPreferences("Headline_THREE", MODE_PRIVATE);
        editor_one = sharedPreferences_one.edit();
        editor_two = sharedPreferences_two.edit();
        editor_three = sharedPreferences_three.edit();
        editor_one.clear();
        editor_two.clear();
        editor_three.clear();
        editor_one.commit();
        editor_two.commit();
        editor_three.commit();
    }


}