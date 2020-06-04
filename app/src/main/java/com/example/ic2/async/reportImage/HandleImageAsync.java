package com.example.ic2.async.reportImage;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.images.ImageHandler;
import com.example.ic2.model.Report;

import java.util.List;

public class HandleImageAsync extends AsyncTask<Void,Void,String> {

    List<ImageHandler> imageHandlerList;
    Context context;
    AsyncTaskCallback<String> callback;
    Report report;
    Exception exception;


    public HandleImageAsync(List<ImageHandler> imageHandlerList,Report report, Context context, AsyncTaskCallback<String> callback) {
        this.imageHandlerList = imageHandlerList;
        this.context = context;
        this.callback = callback;
        this.report=report;
    }

    @Override
    protected String doInBackground(Void... voids) {
        for(ImageHandler imageHandler:imageHandlerList){
            imageHandler.handle(this.context,report);
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null){
            callback.handleResponse("Report saved successfully");
        }
    }


}
