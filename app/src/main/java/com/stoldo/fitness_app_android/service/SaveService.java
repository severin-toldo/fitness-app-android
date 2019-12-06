package com.stoldo.fitness_app_android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

// TODO refactor to DataService (save and load)
public class SaveService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MYDEBUG", "In Service doing someshit");
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void testMethod() {
        Log.d("MYDEBUG", "In Service doing someshit");
    }
}
