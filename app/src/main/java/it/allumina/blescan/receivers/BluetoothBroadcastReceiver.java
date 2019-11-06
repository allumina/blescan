package it.allumina.blescan.receivers;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.UUID;

import io.realm.Realm;
import it.allumina.blescan.common.Constants;
import it.allumina.blescan.data.SystemItem;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(Constants.TAG, "--> BluetoothBroadcastReceiver received " + intent.getAction() + " action");

        String event = intent.getAction();
        final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
        switch(state) {
            case BluetoothAdapter.STATE_OFF:
                event += " - STATE_OFF";
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                event += " - STATE_TURNING_OFF";
                break;
            case BluetoothAdapter.STATE_ON:
                event += " - STATE_ON";
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                event += " - STATE_TURNING_ON";
                break;
        }

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        SystemItem systemItem = realm.createObject(SystemItem.class, UUID.randomUUID().toString());
        systemItem.setEvent(event);
        systemItem.setTimestamp(System.currentTimeMillis());
        realm.commitTransaction();
    }
}
