package com.business_idea.business_ideas_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class BusinessIdeasActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressDialog pDialog;
    private ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_ideas);
        imgback=findViewById(R.id._btnback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView=findViewById(R.id.webView);
        WebSettings webSettings=webView.getSettings();
         webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
          webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

         webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
           webSettings.setEnableSmoothTransition(true);





        webView.loadUrl("https://www.entrepreneur.com/article/292277");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                pDialog = new ProgressDialog(BusinessIdeasActivity.this);
                pDialog.setTitle("Page Loading");
                pDialog.setIcon(R.drawable.ic_access_alarm_black_24dp);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(true);
                pDialog.show();
                super.onPageStarted(view, url, favicon);
            }
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                //if page loaded successfully then show print button

                if (pDialog.isShowing())
                    pDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (pDialog.isShowing())
            pDialog.dismiss();
        finish();
    }
}
