package com.example.ic2.room;

import com.example.ic2.model.Location;
import com.example.ic2.model.Report;
import com.example.ic2.model.ReportImage;
import com.example.ic2.model.TypeOfIncident;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Report.class, ReportImage.class, Location.class, TypeOfIncident.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IReportDao getReportDao();
    public abstract IReportImageDao getReportImageDao();
    public abstract ILocationDao getLocationDao();
    public abstract ITypeOfIncidentDao getTypeOfIncidentDao();
}
