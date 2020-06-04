package com.example.ic2.async.location;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.room.Connection;


import java.util.List;

public class GetLocationsNameAsync extends AsyncTask<Void,Void,String> {

    Context context;
    AsyncTaskCallback<List<String>> callback;
    List<String> locationList;
    Exception exception;

    public GetLocationsNameAsync(Context context, AsyncTaskCallback<List<String>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        exception=null;
        locationList= Connection.getInstance(context).getDatabase().getLocationDao().getLocationsName();
        if(locationList==null){
            exception=new Exception();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null){
            if(exception==null){
                callback.handleResponse(locationList);
            }else{
                callback.handleFault(exception);
            }
        }
    }
}
