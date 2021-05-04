package com.example.new_mynews.ui.Search;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.new_mynews.ui.Search.Search_One.Search_ONE;
import com.example.new_mynews.ui.Search.Search_Two.Search_TWO;

public class Search_ViewPager_Adapter extends FragmentStatePagerAdapter {
    int Tabs;

    public Search_ViewPager_Adapter(FragmentManager fragmentManager, int num) {
        super(fragmentManager);
        this.Tabs = num;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Search_ONE search_one = new Search_ONE();
                return search_one;
            case 1:
                Search_TWO search_two = new Search_TWO();
                return search_two;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Tabs;
    }
}
