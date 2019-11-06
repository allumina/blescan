package it.allumina.blescan.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SystemItem extends RealmObject {
    @PrimaryKey
    private String id;
    private String event;
    private long timestamp;

    public String getId() { return this.id; }
    public void setId(String value) { this.id = value; }

    public String getEvent() { return this.event; }
    public void setEvent(String value) { this.event = value; }

    public long getTimestamp() { return this.timestamp; }
    public void setTimestamp(long value) { this.timestamp = value; }
}
