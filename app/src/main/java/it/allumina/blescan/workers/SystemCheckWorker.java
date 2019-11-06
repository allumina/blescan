package it.allumina.blescan.workers;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import it.allumina.blescan.common.Constants;
import it.allumina.blescan.utils.SystemUtils;

public class SystemCheckWorker extends Worker {

    public SystemCheckWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        Log.d(Constants.TAG, "--> SystemCheckWorker");
        SystemUtils.launchMonitorService(getApplicationContext());
        return Result.success();
    }
}
