package com.xav.country.info.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xav.country.info.R;
import com.xav.country.info.model.CountryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shadik khan on 6/13/2017.
 */

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.ViewHolder> {

    private Context context;
    private List<CountryModel> country;

    public CountryListAdapter(Context context) {
        this.context = context;
        this.country = new ArrayList<>();
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
    }

    @Override
    public int getItemCount() {
        return country.size();
    }

    public void addAll(List<CountryModel> list) {
        if (list.size() > 0) {
            country.clear();
            country.addAll(list);
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
