package it.allumina.blescan.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import it.allumina.blescan.R;
import it.allumina.blescan.activities.MainActivity;
import it.allumina.blescan.common.Constants;
import it.allumina.blescan.receivers.BluetoothBroadcastReceiver;

public class MonitorService extends Service {

    public static final String CHANNEL_ID = "MonitorServiceChannel";

    public int counter = 0;

    private Timer timer;
    private TimerTask timerTask;

    public MonitorService() {

    }

    public MonitorService(Context applicationContext) {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BluetoothBroadcastReceiver bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
        registerReceiver(bluetoothBroadcastReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));

        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Monitor Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_stat_bluetooth)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        startTimer();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Monitor Service Active",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, Constants.TIMER_DELAY, Constants.TIMER_INTERVAL); //
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i(Constants.TAG, "Service timer " + (counter++));
            }
        };
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}