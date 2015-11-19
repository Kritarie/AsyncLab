package com.example.asynclab;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Sean on 11/18/2015.
 */
public class LoadDataService extends Service implements LoadDataTask.Callback {

    private IBinder mBinder = new ServiceBinder();
    private LoadDataTask.Callback mClient;
    private String mResult;

    @Override
    public void onCreate() {
        super.onCreate();
        new LoadDataTask(this, DataRepository.get()).execute();
    }

    public void setClient(LoadDataTask.Callback client) {
        mClient = client;
        if (mClient != null && mResult != null) {
            mClient.onLoadComplete(mResult);
        }
    }

    @Override
    public void onLoadComplete(String string) {
        mResult = string;
        if (mClient != null && mResult != null) {
            mClient.onLoadComplete(mResult);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class ServiceBinder extends Binder {
        LoadDataService getService() {
            return LoadDataService.this;
        }
    }
}
