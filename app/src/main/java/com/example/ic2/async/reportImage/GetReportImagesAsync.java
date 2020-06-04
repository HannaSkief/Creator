package com.example.ic2.async.reportImage;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.Report;
import com.example.ic2.model.ReportImage;
import com.example.ic2.room.Connection;

import java.util.List;

public class GetReportImagesAsync extends AsyncTask<Void,Void,String> {

    Report report;
    Context context;
    AsyncTaskCallback<List<ReportImage>> callback;
    Exception exception;
    List<ReportImage> reportImageList;
    public GetReportImagesAsync(Report report, Context context, AsyncTaskCallback<List<ReportImage>> callback) {
        this.report = report;
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        exception=null;
        reportImageList= Connection.getInstance(this.context)
                .getDatabase().getReportImageDao()
                .getReportImages(report.getId());
        if(reportImageList==null){
            exception=new Exception("Report has no images");
        }

        return " ";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(callback!=null){
            if(exception==null){
                callback.handleResponse(reportImageList);
            }else{
                callback.handleFault(exception);
            }
        }

    }
}
