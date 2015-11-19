package com.example.asynclab;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class LoadDataTask extends AsyncTask<Void, Void, String> {

    private WeakReference<Callback> mCallbackRef;
    private Repository<String> mRepository;

    public LoadDataTask(Callback callback, Repository<String> repository) {
        mCallbackRef = new WeakReference<>(callback);
        mRepository = repository;
    }

    @Override
    protected String doInBackground(Void... params) {
        return mRepository.loadData();
    }

    @Override
    protected void onPostExecute(String s) {
        Callback callback = mCallbackRef.get();
        if (callback != null) {
            callback.onLoadComplete(s);
        }
    }

    public interface Callback {
        void onLoadComplete(String string);
    }
}
