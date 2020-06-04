package com.example.ic2.async.typeOfIncident;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.model.TypeOfIncident;
import com.example.ic2.room.Connection;

import java.util.List;

public class GetTypeOfIncidentAsync extends AsyncTask<Void,Void,String> {

    Context context;
    AsyncTaskCallback<List<TypeOfIncident>> callback;
    Exception exception;
    List<TypeOfIncident> typeOfIncidentList;

    public GetTypeOfIncidentAsync(Context context, AsyncTaskCallback<List<TypeOfIncident>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        exception=null;
        typeOfIncidentList= Connection.getInstance(this.context).getDatabase().getTypeOfIncidentDao().getAllTypeOfIncident();
        if(typeOfIncidentList==null||typeOfIncidentList.isEmpty()){
            exception=new Exception("No type of incident exist");
        }

        return " ";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null){
            if(exception==null){
                callback.handleResponse(typeOfIncidentList);
            }else{
                callback.handleFault(exception);
            }
        }
    }
}
