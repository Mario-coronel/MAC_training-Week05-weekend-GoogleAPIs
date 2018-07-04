package com.example.admin.geodagger.adapters;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.geodagger.R;
import com.example.admin.geodagger.model.responsegeoplaces.Result;

import java.util.ArrayList;
import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceListHolder> implements View.OnClickListener{

    private ArrayList<Result> resultList;

    public PlaceListAdapter(List<Result> resultList) {
        this.resultList = new ArrayList<>(resultList);
    }

    @Override
    public PlaceListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.places_row_layout, parent,false);
        return new PlaceListHolder(v);
    }

    @Override
    public void onBindViewHolder(final PlaceListHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(resultList.get(position).getIcon()).into(holder.icon);
        holder.placeName.setText(resultList.get(position).getName());
        holder.itemView.setId(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    @Override
    public void onClick(View view) {

        System.out.println(view.getId());

    }

    class PlaceListHolder extends RecyclerView.ViewHolder{
        TextView placeName;
        ImageView icon;

        public PlaceListHolder(View itemView) {
            super(itemView);
            this.placeName = itemView.findViewById(R.id.tvPlaceName);
            this.icon = itemView.findViewById(R.id.ivIcon);

        }
    }

}
