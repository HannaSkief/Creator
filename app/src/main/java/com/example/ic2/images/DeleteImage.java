package com.example.ic2.images;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ic2.model.Report;
import com.example.ic2.model.ReportImage;
import com.example.ic2.room.Connection;

import java.io.File;

public class DeleteImage extends ImageHandler {

    public DeleteImage(ReportImage image) {
        super(image);
    }

    @Override
    public void handle(Context context, Report report) {

        //1.delete image file
        //2. delete image from database

        File file=new File(image.getPath());
        if(file.exists()){
            file.delete();
           // Log.e("delete ","file deleted successfully");
        }
       // Log.e("delete image path",image.getPath());
        Connection.getInstance(context).getDatabase().getReportImageDao().delete(image);

    }
}
