package it.allumina.blescan.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import it.allumina.blescan.R;
import it.allumina.blescan.adapters.SystemItemsAdapter;
import it.allumina.blescan.data.SystemItem;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_COARSE_LOCATION = 200;

    Realm realm;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        checkLocationPermission();
    }

    @Override
    public void onResume(){
        super.onResume();
        realm = Realm.getDefaultInstance();
        loadData();
        loadAdapter();
    }

    @Override
    public void onPause(){
        super.onPause();
        realm.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    //TODO re-request
                }
                break;
            }
        }
    }

    private void loadAdapter() {
        SystemItemsAdapter systemItemsAdapter = new SystemItemsAdapter(loadData());
        listView.setAdapter(systemItemsAdapter);
    }

    private RealmResults<SystemItem> loadData() {
        return realm.where(SystemItem.class).findAll().sort("timestamp", Sort.DESCENDING);
    }

    protected void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_COARSE_LOCATION);
        }
    }


}
