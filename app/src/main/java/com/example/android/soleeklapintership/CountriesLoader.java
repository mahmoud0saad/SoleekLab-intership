package com.example.android.soleeklapintership;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

public class CountriesLoader extends AsyncTaskLoader {
    String mUrl;
    public CountriesLoader(@NonNull Context context, String url) {
        super(context);
        mUrl=url;

    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<String> loadInBackground() {
        Log.i("mano", "loadInBackground: ");
        List<String> list = new UtilsQuery().fetchDataFromURL(mUrl);

        return list;
    }
}
