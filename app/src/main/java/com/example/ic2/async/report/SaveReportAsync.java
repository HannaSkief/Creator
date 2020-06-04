package com.example.ic2.async.report;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.Report;
import com.example.ic2.room.Connection;

public class SaveReportAsync extends AsyncTask<Void,Void,String> {

    Report report;
    Context context;
    AsyncTaskCallback<Report> callback;

    public SaveReportAsync(Report report, Context context, AsyncTaskCallback<Report> callback) {
        this.report = report;
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(report.getId()==0) {
            long id = Connection.getInstance(this.context).getDatabase().getReportDao().insert(this.report);
            this.report.setId(id);
        }else{
            Connection.getInstance(this.context).getDatabase().getReportDao().update(report);
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null){
            callback.handleResponse(this.report);
        }
    }
}
