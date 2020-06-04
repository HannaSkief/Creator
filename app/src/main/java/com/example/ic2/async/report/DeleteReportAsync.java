package com.example.ic2.async.report;

import android.content.Context;
import android.os.AsyncTask;
import android.telecom.Call;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.Report;
import com.example.ic2.room.Connection;

public class DeleteReportAsync extends AsyncTask<Void,Void,String> {

    Report report;
    Context context;
    AsyncTaskCallback<Report> callback;

    public DeleteReportAsync(Report report, Context context, AsyncTaskCallback<Report> callback) {
        this.report = report;
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {

        Connection.getInstance(this.context).getDatabase().getReportDao().delete(report);

        return " ";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null)
            callback.handleResponse(report);
    }

}
