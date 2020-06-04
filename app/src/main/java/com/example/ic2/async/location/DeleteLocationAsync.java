package com.example.ic2.async.location;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.Location;
import com.example.ic2.room.Connection;

public class DeleteLocationAsync extends AsyncTask<Void,Void,String> {
    Location location;
    Context context;
    AsyncTaskCallback<Location> callback;

    public DeleteLocationAsync(Location location, Context context, AsyncTaskCallback<Location> callback) {
        this.location = location;
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Connection.getInstance(this.context).getDatabase().getLocationDao().delete(location);

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (callback!=null){
            callback.handleResponse(location);
        }
    }
}
