package com.example.asynclab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private DataRepository mRepository;
    private TextView mTextView;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.data);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mRepository = DataRepository.get();

        //TODO asynchronously load data from mRepository and display it in mTextView
    }
}
