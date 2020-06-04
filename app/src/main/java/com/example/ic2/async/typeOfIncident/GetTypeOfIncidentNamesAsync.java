package com.example.ic2.async.typeOfIncident;

import android.content.Context;
import android.os.AsyncTask;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.room.Connection;

import java.util.List;

public class GetTypeOfIncidentNamesAsync extends AsyncTask<Void,Void,String> {
    Context context;
    AsyncTaskCallback<List<String>> callback;
    List<String> nameList;
    Exception exception;

    public GetTypeOfIncidentNamesAsync(Context context, AsyncTaskCallback<List<String>> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        exception=null;
        nameList= Connection.getInstance(this.context).getDatabase().getTypeOfIncidentDao().getTypeOfIncidentNames();
        if(nameList==null){
            exception=new Exception();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(callback!=null){
            if(exception==null){
                callback.handleResponse(nameList);
            }else{
                callback.handleFault(exception);
            }
        }
    }
}
