package com.example.ic2.async.location;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.Location;
import com.example.ic2.room.Connection;

import java.util.List;

public class GetLocationAsync extends AsyncTask<Void,Void,String> {
    Context context;
    AsyncTaskCallback<List<Location>> callback;
    List<Location> locationList;
    Exception exception;

    public GetLocationAsync(Context context, AsyncTaskCallback<List<Location>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        exception=null;

        locationList= Connection.getInstance(this.context).getDatabase().getLocationDao().getAllLocations();
        if(locationList==null || locationList.isEmpty()){
            exception=new Exception("No location exist");
        }

        return " ";
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
