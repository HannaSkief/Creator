package com.example.ic2.room;

import com.example.ic2.model.ReportImage;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface IReportImageDao {

    @Insert
    void insert(ReportImage image);

    @Delete
    void delete(ReportImage image);

    @Query("delete from report_images where reportId=:reportId")
    void deleteByreportId(long reportId);

    @Query("select * from report_images where reportId=:reportId")
    List<ReportImage> getReportImages(long reportId);

}
