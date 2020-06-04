package com.example.ic2.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "report_images")
public class ReportImage {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private long reportId;
    private String path;

    public ReportImage(){
    }
    public ReportImage(String path){
        this.path=path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
