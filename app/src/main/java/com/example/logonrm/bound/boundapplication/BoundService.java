package com.example.logonrm.bound.boundapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Chronometer;

/**
 * Created by logonrm on 12/06/2017.
 */

public class BoundService extends Service {
    private String Log_Tag = "BoundService";
    private IBinder mBinder = new MyBinder();
    private Chronometer mChronometer;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.v(Log_Tag, "In Create");
        mChronometer = new Chronometer(this);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(Log_Tag, "In Bind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent){
        Log.v(Log_Tag, "In Rebind");
        super.onRebind(intent);
    }

    public boolean onUnbind(Intent intent)
    {
        Log.v(Log_Tag, "In unbind");
        return  true;
    }

    public void onDestroy(){
        super.onDestroy();
        Log.v(Log_Tag, "In destroy");
        mChronometer.stop();
    }

    public String getTimeStamp() {
        long elapsedMillis = SystemClock.elapsedRealtime() - mChronometer.getBase();
        int hours = (int) (elapsedMillis / 3600000);
        int minutes = (int) (elapsedMillis - hours * 3600000) / 60000;
        int seconds = (int) (elapsedMillis - hours * 3600000 - minutes * 60000) / 1000;
        int millis = (int) (elapsedMillis - hours * 3600000 - minutes * 60000 - seconds * 1000);
        return hours + ":" + minutes + ":" + seconds + ":" + millis;
    }

    public class MyBinder extends Binder{
        BoundService getBoundService() {

            return  BoundService.this;
        }
    }
}
