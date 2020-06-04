package com.example.ic2.async.location;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.model.Location;
import com.example.ic2.room.Connection;

public class UpdateLocationAsync extends AsyncTask<Void,Void,String> {
    Location location;
    Context context;

    public UpdateLocationAsync(Location location, Context context) {
        this.location = location;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Connection.getInstance(this.context).getDatabase().getLocationDao().update(this.location);
        return " ";
    }


}
