package com.example.logonrm.bound.boundapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.tvTimestamp)
    TextView tvTimeStamp;

    BoundService mBoundService;
    boolean mServiceBound = false;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyBinder myBinder = (BoundService.MyBinder) service;
            mBoundService = myBinder.getBoundService();
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };

    @OnClick(R.id.btnStartService)
    public void StartService(){
        Intent intent = new Intent(this, BoundService.class);
        startService(intent);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnPrintTS)
    public void PrintTimestamp(){
        if(mServiceBound){
            tvTimeStamp.setText(mBoundService.getTimeStamp());
        }
    }

    @OnClick(R.id.btnStopService)
    public  void StopService(){
        if(mServiceBound){
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
        Intent intent = new Intent(MenuActivity.this, BoundService.class);
        stopService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.StartService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mServiceBound){
            unbindService(mServiceConnection);
            mServiceBound = false;
        }
    }
}
