package com.automarks.nuzatech.norchok.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.automarks.nuzatech.norchok.R;


/**
 * Created by prakash on 3/14/2018.
 */

public class Google_webimage extends AppCompatActivity {
    WebView webView;
    String url;
    boolean doubleBackToExitPressedOnce = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    webViewGoBack();
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_webimage);

        url = getIntent().getStringExtra("url");

        if (!isOnline()) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
        }


        webView = (WebView) findViewById(R.id.website_google);
        webView.loadUrl(url);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.getJavaScriptEnabled();


        webSettings.setDomStorageEnabled(true);


        webView.setWebViewClient(new WebViewClient() {

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if( URLUtil.isNetworkUrl(url) ) {
//                    return false;
//                }
//                if (appInstalledOrNot(url)) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity( intent );
//                } else {
//                    // do something if app is not installed
//
//                    Toast.makeText(MainActivity.this, "App is not installed in your device", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URLUtil.isNetworkUrl(url)) {
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {

                webView.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('mainNavigationWrapper')[0].style.display='none'; " +
                        "document.getElementsByClassName('breadcrumbContainer')[0].style.display='none'; " +
                        "document.getElementsByClassName('footerWrapper')[0].style.display='none'; " +
                        "document.getElementsByClassName('sideAds')[0].style.display='none'; " +
                        "})()");
            }
        });


        webView.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }

                return false;
            }

        });


    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    private void webViewGoBack() {
        webView.goBack();
    }


}


