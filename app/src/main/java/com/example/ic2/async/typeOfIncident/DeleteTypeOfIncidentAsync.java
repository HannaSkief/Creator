package com.example.ic2.async.typeOfIncident;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.TypeOfIncident;
import com.example.ic2.room.Connection;

public class DeleteTypeOfIncidentAsync extends AsyncTask<Void,Void,String> {

    TypeOfIncident typeOfIncident;
    Context context;
    AsyncTaskCallback<TypeOfIncident> callback;

    public DeleteTypeOfIncidentAsync(TypeOfIncident typeOfIncident, Context context, AsyncTaskCallback<TypeOfIncident> callback) {
        this.typeOfIncident = typeOfIncident;
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {

        Connection.getInstance(this.context)
                .getDatabase()
                .getTypeOfIncidentDao()
                .delete(this.typeOfIncident);

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null){
            callback.handleResponse(typeOfIncident);
        }
    }
}
