package com.example.ic2.async.reportImage;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.images.DeleteImage;
import com.example.ic2.model.Report;
import com.example.ic2.model.ReportImage;
import com.example.ic2.room.Connection;

import java.util.List;

public class DeleteImagesAsync extends AsyncTask<Void,Void,String> {

    Report report;
    Context context;
    AsyncTaskCallback<Boolean> callback;
    List<ReportImage> imageList;

    public DeleteImagesAsync(Report report, Context context, AsyncTaskCallback<Boolean> callback) {
        this.report = report;
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
      imageList= Connection.getInstance(this.context).getDatabase().getReportImageDao().getReportImages(report.getId());
      if(imageList!=null){
          for (ReportImage image:imageList){
              new DeleteImage(image).handle(context,report);
          }
      }
        return " ";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(callback!=null)
            callback.handleResponse(true);
    }
}
