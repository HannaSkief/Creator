package com.example.ic2.images;

import android.content.Context;

import com.example.ic2.model.Report;
import com.example.ic2.model.ReportImage;

public abstract class ImageHandler {

    protected  ReportImage image;


    public ImageHandler(ReportImage image) {
        this.image = image;
    }

    public abstract void  handle(Context context, Report report);
}
