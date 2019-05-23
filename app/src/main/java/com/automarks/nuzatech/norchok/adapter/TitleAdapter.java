package com.automarks.nuzatech.norchok.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.automarks.nuzatech.norchok.Pojo.ServicesList;


import com.automarks.nuzatech.norchok.R;
import com.automarks.nuzatech.norchok.ViewHolder.ListViewHolder;
import com.automarks.nuzatech.norchok.ViewHolder.TitleViewholder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;


/**
 * Created by prakash on 8/16/2017.
 */

public class TitleAdapter extends ExpandableRecyclerViewAdapter<TitleViewholder,ListViewHolder> {
    public TitleAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public TitleViewholder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_title,parent,false);
        return new TitleViewholder(view);    }

    @Override
    public ListViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_services,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ListViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        ServicesList list= (ServicesList) group.getItems().get(childIndex);
        holder.setListname(list.getList());
    }

    @Override
    public void onBindGroupViewHolder(TitleViewholder holder, int flatPosition, ExpandableGroup group) {
holder.setTitlename(group.getTitle());
    }
}
