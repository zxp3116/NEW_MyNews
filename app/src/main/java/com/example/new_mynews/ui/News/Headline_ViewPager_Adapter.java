package com.example.new_mynews.ui.News;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.new_mynews.ui.News.Headline_One.Headline_ONE;
import com.example.new_mynews.ui.News.Headline_Two.Headline_TWO;
import com.example.new_mynews.ui.News.Ｈeadline_Three.Headline_THREE;

public class Headline_ViewPager_Adapter extends FragmentStatePagerAdapter {
    int Tabs;

    public Headline_ViewPager_Adapter(FragmentManager fragmentManager, int num) {
        super(fragmentManager);
        this.Tabs = num;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Headline_ONE headline_one = new Headline_ONE();
                return headline_one;
            case 1:
                Headline_TWO headline_two = new Headline_TWO();
                return headline_two;
            case 2:
                Headline_THREE headline_three = new Headline_THREE();
                return headline_three;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Tabs;
    }
}
