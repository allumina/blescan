package it.allumina.blescan.receivers;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;
        import java.util.UUID;
        import io.realm.Realm;
        import it.allumina.blescan.common.Constants;
        import it.allumina.blescan.data.SystemItem;
        import it.allumina.blescan.services.MonitorService;
        import it.allumina.blescan.utils.SystemUtils;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Constants.TAG, "--> BootCompletedReceiver received " + intent.getAction() + " action");

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        SystemItem systemItem = realm.createObject(SystemItem.class, UUID.randomUUID().toString());
        systemItem.setEvent(intent.getAction());
        systemItem.setTimestamp(System.currentTimeMillis());
        realm.commitTransaction();

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            SystemUtils.launchMonitorService(context);
            SystemUtils.enqueueSystemWorkers();
        }
    }
}
