package com.xav.country.info.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.xav.country.info.R;
import com.xav.country.info.adapter.CountryListAdapter;
import com.xav.country.info.api.CountryService;
import com.xav.country.info.model.CountryModel;
import com.xav.country.info.util.ConnectionUtil;
import com.xav.country.info.util.DividerItemDecoration;
import com.xav.country.info.util.RetrofitUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private View layoutProgress;
    private CountryListAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_main));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        initView();
    }

    private void initView() {
        layoutProgress = (View) findViewById(R.id.layout_progress);
        RecyclerView countryListRecycler = (RecyclerView) findViewById(R.id.rv_country_list);
        countryListRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        countryListRecycler.setLayoutManager(layoutManager);
        countryAdapter = new CountryListAdapter(this);
        countryListRecycler.setAdapter(countryAdapter);
        try {
            loadCountryListFromNetwork();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Call adapter method addAll()
     * Update recycler view using response data
     */
    private void onUpdateCountryList(List<CountryModel> data) {
        countryAdapter.addAll(data);
        countryAdapter.notifyDataSetChanged();
    }

    /**
     * Api hit for getting the country info response     *
     */
    private void loadCountryListFromNetwork() {
        if (ConnectionUtil.isConnected(this)) {
            layoutProgress.setVisibility(View.VISIBLE);
            CountryService countryService = RetrofitUtil.createRetrofitService(
                    CountryService.class);
            Call<List<CountryModel>> call = countryService.getCountries();
            call.enqueue(new CountryCallback());
        } else {
            Log.e("Connection Error", "No Internet");
        }
    }

    /**
     * API callback
     * onResponse, onFailure
     */
    private class CountryCallback implements Callback<List<CountryModel>> {

        @Override
        public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
            layoutProgress.setVisibility(View.GONE);
            if (response.body() != null) {
                try {
                    onUpdateCountryList(response.body());
                } catch (ClassCastException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<List<CountryModel>> call, Throwable t) {
            layoutProgress.setVisibility(View.GONE);
            t.printStackTrace();
        }
    }
}
