package com.example.ic2.room;

import com.example.ic2.model.Location;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ILocationDao {

    @Insert
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);

    @Query("select * from locations")
    List<Location> getAllLocations();

    @Query("select name from locations where status='selected' ")
    List<String> getLocationsName();

    @Query("select * from locations where name=:name")
    Location getLocation(String name);

}
