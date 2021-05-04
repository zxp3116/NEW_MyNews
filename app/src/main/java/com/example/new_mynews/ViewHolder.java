package com.example.new_mynews;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    RoundedCorners roundedCorners;
    Intent intent;

    private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        // setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public ViewHolder setImageClick_Intent(int viewId, final Context context, final String string) {
        ImageView view = getView(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Web_Window.class);
                intent.putExtra("Load_Url", string);
                context.startActivity(intent);

            }
        });

        return this;
    }

    public ViewHolder setTextClick_Intent(int viewId, final Context context, final String string) {
        TextView view = getView(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Web_Window.class);
                intent.putExtra("Load_Url", string);
                context.startActivity(intent);

            }
        });

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param
     * @return
     */
    public ViewHolder setImageClick_Clear(int viewId, final List<String> stringList, final CommonAdapter commonAdapter, final Context context) {
        ImageView imageView = getView(viewId);
        final Gson gson = new Gson();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringList.remove(mPosition);
                commonAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = context.getSharedPreferences("searchData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String string_Json = gson.toJson(stringList);
                editor.putString("listSearchData", string_Json);
                editor.commit();
            }
        });
        return this;
    }

    public ViewHolder setImageUrl_glide(int Viewid, Context context, String url) {
        roundedCorners = new RoundedCorners(50);
        ImageView imageView = getView(Viewid);
        Glide.with(context).load(url).centerCrop().apply(RequestOptions.bitmapTransform(roundedCorners)).into(imageView);
        return this;
    }

    public ViewHolder setCircle_glide(int Viewid, Context context, String url) {
        ImageView imageView = getView(Viewid);
        Glide.with(context).load(url).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

}
