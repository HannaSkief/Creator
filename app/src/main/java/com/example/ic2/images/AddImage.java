package com.example.ic2.images;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.ic2.common.FileUtils;
import com.example.ic2.model.Report;
import com.example.ic2.model.ReportImage;
import com.example.ic2.room.Connection;

import java.io.File;

public class AddImage extends ImageHandler {


    public AddImage(ReportImage image) {
        super(image);
    }

    @Override
    public void handle(Context context, Report report) {

        //1. move image path from temp folder
        //2. insert image to database

        String inPath=new File(image.getPath()).getParent()+"/";
        String inFile=new File(image.getPath()).getName();
        String outPath=context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();
        FileUtils.moveFile(inPath,inFile,outPath);
        image.setPath(outPath+"/"+inFile);
        image.setReportId(report.getId());

        Log.e("Add image path",image.getPath());
        Connection.getInstance(context).getDatabase().getReportImageDao().insert(image);
    }
}
