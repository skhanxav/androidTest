package com.xav.country.info.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xav.country.info.R;
import com.xav.country.info.model.CountryModel;
import com.xav.country.info.util.ConstantUtil;

/**
 * Created by skhan4 on 6/13/2017.
 */

public class CountryDetailsActivity extends AppCompatActivity {

    //private ImageView flag;
    private WebView webview;
    private LinearLayout parent;
    private ProgressBar progress;

    private CountryModel data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new Gson().fromJson(getIntent().getExtras().getString(ConstantUtil.COUNTRY), CountryModel.class);
        setContentView(R.layout.activity_country_details);
        try {
            getSupportActionBar().setTitle(data.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initView() {
        parent = (LinearLayout) findViewById(R.id.ll_parent);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        webview = (WebView) findViewById(R.id.webview);
        webview.setWebViewClient(new MyWebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        progress.setVisibility(View.VISIBLE);
        String htmlData = "<div style='width:100%;height:100%;'><img src = '" + data.getFlag() + "' width=100% height=100% /> </div>";
        webview.loadData(htmlData, "text/html", "UTF-8");
        addCurrencyTextView();
    }

    /**
     * Method will add text view dynamically as much
     * as there would be supported type of currency
     * available for this country in response
     */
    private void addCurrencyTextView() {
        for (int i = 0; i < data.getCurrencies().size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("( ");
            sb.append(data.getCurrencies().get(i).getSymbol());
            sb.append(" ) ");
            sb.append(data.getCurrencies().get(i).getName());
            TextView tv = new TextView(this);
            tv.setText(sb);
            tv.setGravity(Gravity.CENTER);
            parent.addView(tv);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

       /* @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, request);

        }*/

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            CountryDetailsActivity.this.progress.setVisibility(View.GONE);;
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            CountryDetailsActivity.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }



    /**
     * Actionbar or Toolbar back button handling
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
