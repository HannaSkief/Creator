package com.example.ic2.room;

import com.example.ic2.model.Report;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface IReportDao {

    @Insert
    long insert(Report report);

    @Update
    void update(Report report);

    @Delete
    void delete(Report report);

    @Query("select * from reports ")
    List<Report> getSavedReports();
}
