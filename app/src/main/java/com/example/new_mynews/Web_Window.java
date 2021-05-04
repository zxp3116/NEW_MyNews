package com.example.new_mynews;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Web_Window extends AppCompatActivity {

    TitlebarView titlebarView;
    WebView webView;
    String Load_Url;
    ProgressBar progressBar_Web;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_window);
        getSupportActionBar().hide();
        webView = findViewById(R.id.webView);
        titlebarView = findViewById(R.id.titlebarView_News);
        progressBar_Web = findViewById(R.id.progressBar_Web);
        progressBar_Web.setMax(100);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar_Web.setVisibility(View.GONE);
                } else {
                    progressBar_Web.setVisibility(View.VISIBLE);
                    progressBar_Web.setProgress(newProgress);
                }
            }
        });
        Load_Url = getIntent().getStringExtra("Load_Url");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Load_Url);
        titlebarView.setOnViewClick(new TitlebarView.onViewClick() {
            @Override
            public void image_Click() {
                finish();
            }
        });
    }

}
