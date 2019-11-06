package it.allumina.blescan;

import android.app.ActivityManager;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.core.content.ContextCompat;

import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import it.allumina.blescan.receivers.BluetoothBroadcastReceiver;
import it.allumina.blescan.services.MonitorService;
import it.allumina.blescan.utils.SystemUtils;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("blescan.realm").build();
        Realm.setDefaultConfiguration(config);

        SystemUtils.launchMonitorService(getApplicationContext());
        SystemUtils.enqueueSystemWorkers();
    }
}
