package com.xav.country.info.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xav.country.info.R;
import com.xav.country.info.activity.CountryDetailsActivity;
import com.xav.country.info.database.CountryTable;
import com.xav.country.info.model.CountryModel;
import com.xav.country.info.util.ConstantUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Shadik khan on 6/13/2017.
 */

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {

    private Context context;
    private List<CountryModel> country;
    private HashMap<String, CountryModel> visitedCountry;

    public CountryListAdapter(Context context) {
        this.context = context;
        this.country = new ArrayList<>();
        this.visitedCountry = new HashMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CountryModel value = country.get(position);
        holder.countryName.setText(value.getName());
        if (visitedCountry.containsKey(value.getNumericCode())) {
            holder.isVisited.setVisibility(View.VISIBLE);
        } else {
            holder.isVisited.setVisibility(View.INVISIBLE);
        }
        /** Can also be start from main activity using interface concept */
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!visitedCountry.containsKey(value.getNumericCode())) {
                    visitedCountry.put(value.getNumericCode(), value);
                    CountryTable.insertCountry(value);
                    notifyDataSetChanged();
                }
                Intent goToDetailActivity = new Intent(context, CountryDetailsActivity.class);
                goToDetailActivity.putExtra(ConstantUtil.COUNTRY, new Gson().toJson(value));
                context.startActivity(goToDetailActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return country.size();
    }

    /**
     * Add all api response to array list
     * called from MainActivity
     */
    public void addAll(List<CountryModel> list) {
        if (list.size() > 0) {
            country.clear();
            country.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * Add visited country list to array list
     * called from MainActivity
     */
    public void addAllVisitedCountry(HashMap<String, CountryModel> list) {
        if (list.size() > 0) {
            visitedCountry.clear();
            visitedCountry.putAll(list);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView isVisited;
        private TextView countryName;
        private LinearLayout rootLayout;

        public ViewHolder(View view) {
            super(view);
            isVisited = (ImageView) view.findViewById(R.id.iv_visited);
            countryName = (TextView) view.findViewById(R.id.tv_country_name);
            rootLayout = (LinearLayout) view.findViewById(R.id.parent);
        }
    }
}
