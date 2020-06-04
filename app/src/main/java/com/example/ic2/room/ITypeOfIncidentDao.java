package com.example.ic2.room;

import com.example.ic2.model.TypeOfIncident;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ITypeOfIncidentDao {

    @Insert
    void insert(TypeOfIncident typeOfIncident);

    @Update
    void update(TypeOfIncident typeOfIncident);

    @Delete
    void delete(TypeOfIncident typeOfIncident);

    @Query("select * from type_of_incidents")
    List<TypeOfIncident> getAllTypeOfIncident();

    @Query("select * from type_of_incidents where name=:name")
    TypeOfIncident getTypeOfIncedent(String name);

    @Query("select name from type_of_incidents where status='selected'")
    List<String> getTypeOfIncidentNames();

}
