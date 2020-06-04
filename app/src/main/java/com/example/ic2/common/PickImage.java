package com.example.ic2.common;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.ic2.R;

public class PickImage {

    private Context context;
    public PickImage(Context context){
        this.context=context;
    }


    public void show(final IPickClick click){
        ImageView imgCamera,imgGallery;
        View view= LayoutInflater.from(this.context).inflate(R.layout.pick_image,null);
        imgCamera=view.findViewById(R.id.imgCamera);
        imgGallery=view.findViewById(R.id.imgGallery);


        final AlertDialog dialog=new AlertDialog.Builder(this.context).create();
        dialog.setTitle("Choose");
        dialog.setView(view);
        dialog.show();
        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onCameraClick();
                dialog.dismiss();
            }
        });
        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onGalleryClick();
                dialog.dismiss();
            }
        });



    }

    public interface IPickClick{
        void onCameraClick();
        void onGalleryClick();
    }
}
