package com.example.ic2.async.location;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.Location;
import com.example.ic2.room.Connection;

public class AddLocationAsync extends AsyncTask<Void, Void, String> {

    Location location;
    Context context;
    AsyncTaskCallback<Location> callback;
    Exception exception;

    public AddLocationAsync(Location location, Context context, AsyncTaskCallback<Location> callback) {
        this.location = location;
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        exception=null;
        if(Connection.getInstance(this.context).getDatabase().getLocationDao().getLocation(this.location.getName())==null){
            Connection.getInstance(this.context).getDatabase().getLocationDao().insert(this.location);
        }else{
            exception=new Exception("Location already exist");
        }

        return " ";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null){
            if(exception==null){
                callback.handleResponse(this.location);
            }else{
                callback.handleFault(exception);
            }

        }
    }
}
