package com.example.ic2.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations")
public class Location extends DynamicInfo {
    @PrimaryKey
    @NonNull
    private String name;
    private String status; // selected | unselected

    public Location() {
    }

    public Location(String name, String status) {
        this.name = name;
        this.status = status;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getStatus() {
        return status;
    }
    @Override
    public void setStatus(String status) {
        this.status = status;
    }
}
