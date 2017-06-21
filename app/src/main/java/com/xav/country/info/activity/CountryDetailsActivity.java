package com.xav.country.info.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.guardanis.imageloader.ImageRequest;
import com.xav.country.info.R;
import com.xav.country.info.model.CountryModel;
import com.xav.country.info.util.ConstantUtil;

/**
 * Created by skhan4 on 6/13/2017.
 */

public class CountryDetailsActivity extends AppCompatActivity {

    private AppCompatImageView flag;
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
        flag = (AppCompatImageView) findViewById(R.id.flag);
        /**SVG request configuration*/
        loadSvg();
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

    /**
     * Load SVG image in image view with caching
     */
    private void loadSvg() {
        ImageRequest.create(flag)
                .setTargetUrl(data.getFlag())
                .execute();
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
