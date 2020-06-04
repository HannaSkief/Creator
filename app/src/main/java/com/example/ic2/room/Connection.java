package com.example.ic2.room;

import android.content.Context;

import androidx.room.Room;

public class Connection {
    private static Connection instance;
    private AppDatabase database;

    private Connection (Context context){
        database= Room.databaseBuilder(context,AppDatabase.class,"ic2db")
                .allowMainThreadQueries()
                .build();
    }

    public static Connection getInstance(Context context){
        if(instance==null)
            instance=new Connection(context);

        return instance;
    }

    public AppDatabase getDatabase(){
        return database;
    }
}
