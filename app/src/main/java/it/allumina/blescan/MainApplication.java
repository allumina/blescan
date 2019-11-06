package it.allumina.blescan;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;
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
