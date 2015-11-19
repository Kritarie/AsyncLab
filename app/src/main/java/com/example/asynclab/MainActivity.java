package com.example.asynclab;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoadDataTask.Callback, LoaderManager.LoaderCallbacks<String>, ServiceConnection {

    // UI
    private Repository<String> mRepository;
    private TextView mTextView;
    private ProgressBar mProgress;

    // Service
    private LoadDataService mService;

    // IntentService
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.data);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mRepository = DataRepository.get();

        //new LoadDataTask(this, mRepository).execute();
        //getSupportLoaderManager().initLoader(0, null, this);
        mReceiver = new DataReceiver();

        Intent startServiceIntent = new Intent(this, LoadDataIntentService.class);
        startServiceIntent.putExtra(LoadDataIntentService.ARGS_REQUEST, LoadDataIntentService.REQUEST_LOAD_DATA);
        startService(startServiceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Intent startServiceIntent = new Intent(this, LoadDataService.class);
//        bindService(startServiceIntent, this, BIND_AUTO_CREATE);
        IntentFilter filter = new IntentFilter(LoadDataIntentService.ACTION_DATA_LOADED);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        //unbindService(this);
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    public void onLoadComplete(String string) {
        mProgress.setVisibility(View.INVISIBLE);
        mTextView.setText(string);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new DataLoader(this, mRepository);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        onLoadComplete(data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        //NOP
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mService = ((LoadDataService.ServiceBinder) service).getService();
        mService.setClient(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService.setClient(null);
        mService = null;
    }

    public class DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String results = intent.getStringExtra(LoadDataIntentService.ARGS_RESULTS);
            onLoadComplete(results);
        }
    }
}
