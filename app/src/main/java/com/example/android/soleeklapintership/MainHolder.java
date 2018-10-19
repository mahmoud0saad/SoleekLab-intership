package com.example.android.soleeklapintership;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainHolder extends RecyclerView.ViewHolder {
    private TextView mTextViewContries;
    public MainHolder(@NonNull View itemView) {
        super(itemView);
        Log.i("mano", "MainHolder: ");
        mTextViewContries=itemView.findViewById(R.id.text_view_country);

    }
    public void onBind(String text){
        mTextViewContries.setText(text);
    }
}
