package com.example.asynclab;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Sean on 11/18/2015.
 */
public class DataLoader extends AsyncTaskLoader<String> {

    private Repository<String> mRepository;

    public DataLoader(Context context, Repository<String> repository) {
        super(context);
        mRepository = repository;
    }

    @Override
    public String loadInBackground() {
        return mRepository.loadData();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
