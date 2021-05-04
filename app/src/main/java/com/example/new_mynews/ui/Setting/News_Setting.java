package com.example.new_mynews.ui.Setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.new_mynews.R;

import static android.content.Context.MODE_PRIVATE;

public class News_Setting extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    View root;
    Switch setting_switch1;
    boolean youTube_BackgroundPlay;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Boolean getYouTube_BackgroundPlay() {
        return youTube_BackgroundPlay;
    }

    public void setYouTube_BackgroundPlay(Boolean youTube_BackgroundPlay) {
        this.youTube_BackgroundPlay = youTube_BackgroundPlay;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        root = inflater.inflate(R.layout.news_setting, container, false);
        setting_switch1 = root.findViewById(R.id.setting_switch1);
        setting_switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setting_switch1.isChecked()) {
                    youTube_BackgroundPlay = true;
                    setting_switch1.setText("目前開啟");
                    Setting_Save();

                } else {
                    setting_switch1.setText("目前關閉");
                    youTube_BackgroundPlay = false;
                    Setting_Save();
                }
            }
        });
        sharedPreferences = getActivity().getSharedPreferences("Setting", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Setting_Load();

        return root;
    }

    private void Setting_Load() {
        youTube_BackgroundPlay = sharedPreferences.getBoolean("YouTube_BackgroundPlay", false);
        setting_switch1.setChecked(youTube_BackgroundPlay);
        String text = sharedPreferences.getString("YouTube_BackgroundPlay_Text","");
        if(!text.equals("")){
            setting_switch1.setText(text);
        }
    }

    private void Setting_Save() {
        editor.putBoolean("YouTube_BackgroundPlay", youTube_BackgroundPlay);
        editor.putString("YouTube_BackgroundPlay_Text", setting_switch1.getText().toString());
        editor.commit();
    }
}