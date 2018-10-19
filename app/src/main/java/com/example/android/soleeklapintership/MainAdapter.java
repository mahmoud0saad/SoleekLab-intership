package com.example.android.soleeklapintership;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainHolder> {
    private List<String> mList;
    private Context mContext;
    public MainAdapter(Context context,List<String>list){
        mList=list;
        mContext=context;
    }
    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MainHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder mainHolder, int i) {
        mainHolder.onBind(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
