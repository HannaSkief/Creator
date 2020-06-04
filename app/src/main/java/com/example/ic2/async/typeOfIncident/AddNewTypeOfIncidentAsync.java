package com.example.ic2.async.typeOfIncident;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.TypeOfIncident;
import com.example.ic2.room.Connection;

public class AddNewTypeOfIncidentAsync extends AsyncTask<Void,Void,String> {

    TypeOfIncident typeOfIncident;
    Context context;
    AsyncTaskCallback<TypeOfIncident> callback;
    Exception exception;

    public AddNewTypeOfIncidentAsync(TypeOfIncident typeOfIncident, Context context, AsyncTaskCallback<TypeOfIncident> callback) {
        this.typeOfIncident = typeOfIncident;
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(Connection.getInstance(this.context).getDatabase().getTypeOfIncidentDao().getTypeOfIncedent(typeOfIncident.getName())==null){
            Connection.getInstance(this.context).getDatabase().getTypeOfIncidentDao().insert(this.typeOfIncident);
        }else{
            exception=new Exception("Type of incident already exist");
        }

        return " ";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null){
            if(exception==null){
                callback.handleResponse(typeOfIncident);
            }else{
                callback.handleFault(exception);
            }
        }

    }
}
