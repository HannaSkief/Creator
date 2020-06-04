package com.example.ic2.common;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ic2.R;

public class CustomToast {

    public static void show(String message, Context context,int duration){

        View view= LayoutInflater.from(context).inflate(R.layout.custom_toast,null);
        TextView tvMessage=view.findViewById(R.id.tvMessage);
        tvMessage.setText(message);
        Toast toast=new Toast(context);
        toast.setDuration(duration);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM,0,20);
        toast.show();

    }
}
