package it.allumina.blescan.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DeviceItem extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private String macAddress;
    private long timestamp;
    private double latitude;
    private double longitude;

    public String getId() { return this.id; }
    public void setId(String value) { this.id = value; }

    public String getName() { return this.name; }
    public void setName(String value) { this.name = value; }

    public String getMacAddress() { return this.macAddress; }
    public void setMacAddress(String value) { this.macAddress = value; }

    public long getTimestamp() { return this.timestamp; }
    public void setTimestamp(long value) { this.timestamp = value; }

    public double getLatitude() { return this.latitude; }
    public void setLatitude(double value) { this.latitude = value; }

    public double getLongitude() { return this.longitude; }
    public void setLongitude(double value) { this.longitude = value; }
}
