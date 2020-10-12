package com.example.qrtagbarcode;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class WebView extends AppCompatActivity {

    TextView textView;
    android.webkit.WebView webView;
    SwipeRefreshLayout swipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.web_view);

        webView = (android.webkit.WebView) findViewById(R.id.webView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        final android.webkit.WebView web_view = findViewById(R.id.webView);
        webView.requestFocus();

        webView.getSettings().setJavaScriptEnabled(true);

        String st =getIntent().getStringExtra("Listviewclickvalue");
        webView.getSettings().setJavaScriptEnabled(true);

        final String url = "https://docs.google.com/gview?embedded=true&url=http://58.27.255.29/Reports/Report_PackingTagDetails.aspx?spId="+st;

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,Color.YELLOW,Color.GREEN);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

        webView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (webView.getScrollY() == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        if(savedInstanceState !=null){
            webView.restoreState(savedInstanceState);
        }
        else
        {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setLoadsImagesAutomatically(true);

//            checkConnection();
        }

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(android.webkit.WebView view, int progress) {

                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });

    }
}
