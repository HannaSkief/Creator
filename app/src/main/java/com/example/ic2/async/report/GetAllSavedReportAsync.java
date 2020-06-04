package com.example.ic2.async.report;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.Report;
import com.example.ic2.room.Connection;

import java.util.List;

public class GetAllSavedReportAsync extends AsyncTask<Void,Void,String> {

    Context context;
    AsyncTaskCallback<List<Report>> callback;
    Exception exception;
    List<Report> reportList;

    public GetAllSavedReportAsync(Context context, AsyncTaskCallback<List<Report>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        exception=null;
        reportList= Connection.getInstance(this.context).getDatabase().getReportDao().getSavedReports();
        if(reportList==null){
            exception=new Exception("No report saved ");
        }

        return " ";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null){
            if(exception==null){
                callback.handleResponse(reportList);
            }else{
                callback.handleFault(exception);
            }
        }
    }
}
