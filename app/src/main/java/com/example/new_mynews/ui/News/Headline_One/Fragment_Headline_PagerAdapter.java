package com.example.new_mynews.ui.News.Headline_One;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.new_mynews.R;
import com.example.new_mynews.Web_Window;

import java.util.List;

public class Fragment_Headline_PagerAdapter extends PagerAdapter {
    private Context context;
    private List<Fragment_Pager_Model> fragmentPager_modelList;
    Intent intent;
    RoundedCorners roundedCorners;

    public Fragment_Headline_PagerAdapter(Context context, List<Fragment_Pager_Model> fragmentPager_modelList) {
        this.context = context;
        this.fragmentPager_modelList = fragmentPager_modelList;
    }
    @Override
    public int getCount() {
        return fragmentPager_modelList.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = View.inflate(context, R.layout.item_viewpager, null);
        ImageView imageView = view.findViewById(R.id.item_Pager_Image);

        TextView textView = view.findViewById(R.id.item_Pager_Text);
        roundedCorners = new RoundedCorners(50);
        Glide.with(context).load(fragmentPager_modelList.get(position).getUrlToImage()).centerCrop().apply(RequestOptions.bitmapTransform(roundedCorners)).into(imageView);
        textView.setText(fragmentPager_modelList.get(position).getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Web_Window.class);
                intent.putExtra("Load_Url", fragmentPager_modelList.get(position).getUrl());
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
