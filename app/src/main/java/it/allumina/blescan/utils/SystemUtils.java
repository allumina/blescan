package it.allumina.blescan.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import it.allumina.blescan.common.Constants;
import it.allumina.blescan.services.MonitorService;
import it.allumina.blescan.workers.SystemCheckWorker;

public class SystemUtils {

    public static boolean isServiceRunning(Context context, String serviceName){
        boolean serviceRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> l = am.getRunningServices(50);
        Iterator<ActivityManager.RunningServiceInfo> i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningServiceInfo runningServiceInfo = i.next();
            if(runningServiceInfo.service.getClassName().equals(serviceName)){
                serviceRunning = true;
                if(runningServiceInfo.foreground)
                {
                    //service run in foreground
                }
            }
        }
        return serviceRunning;
    }

    public static void enqueueSystemWorkers() {
        PeriodicWorkRequest.Builder systemCheckWorkerBuilder =
                new PeriodicWorkRequest.Builder(SystemCheckWorker.class, 15, TimeUnit.MINUTES);
        PeriodicWorkRequest request = systemCheckWorkerBuilder.build();
        WorkManager.getInstance().enqueueUniquePeriodicWork("system", ExistingPeriodicWorkPolicy.KEEP , request);
    }

    public static void launchMonitorService(Context context) {
        if (!SystemUtils.isServiceRunning(context, "MonitorService")) {
            Intent monitorServiceIntent = new Intent(context, MonitorService.class);
            monitorServiceIntent.putExtra("inputExtra", Constants.MONITOR_SERVICE_DESCRIPTION);
            ContextCompat.startForegroundService(context, monitorServiceIntent);
        }
    }
}
