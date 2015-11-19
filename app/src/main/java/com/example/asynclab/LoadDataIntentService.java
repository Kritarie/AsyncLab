package com.example.asynclab;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Sean on 11/18/2015.
 */
public class LoadDataIntentService extends IntentService {

    public static final String ARGS_REQUEST = "request";
    public static final int REQUEST_LOAD_DATA = 0x200;

    public static final String ARGS_RESULTS = "results";
    public static final String ACTION_DATA_LOADED = "action_data_loaded";

    private Repository<String> mRepository;

    public LoadDataIntentService() {
        this("LoadDataIntentService");
    }

    public LoadDataIntentService(String name) {
        super(name);
        mRepository = DataRepository.get();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int request = intent.getIntExtra(ARGS_REQUEST, -1);
        switch (request) {
            case REQUEST_LOAD_DATA:
                String results = mRepository.loadData();
                publishResults(results);
                break;
        }
    }

    private void publishResults(String results) {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_DATA_LOADED);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(ARGS_RESULTS, results);
        sendBroadcast(broadcastIntent);
    }
}
