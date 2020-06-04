package com.example.ic2.async.typeOfIncident;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.model.TypeOfIncident;
import com.example.ic2.room.Connection;

public class UpdateTypeOfIncidentAsync extends AsyncTask<Void,Void,String> {

    TypeOfIncident typeOfIncident;
    Context context;

    public UpdateTypeOfIncidentAsync(TypeOfIncident typeOfIncident, Context context) {
        this.typeOfIncident = typeOfIncident;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Connection.getInstance(this.context).getDatabase().getTypeOfIncidentDao().update(this.typeOfIncident);
        return "";
    }
}
