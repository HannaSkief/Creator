package com.example.ic2.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.ic2.R;
import com.example.ic2.model.ReportImage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<ImagesRecyclerViewAdapter.ViewHolder> {

    List<ReportImage> imagesList;
    Context context;
    private OnImageClicked activity;

    public ImagesRecyclerViewAdapter(List<ReportImage> imagesList, Context context,OnImageClicked activity) {
        this.imagesList = imagesList;
        this.context = context;
        this.activity=activity;
    }
    public interface OnImageClicked{
        void showImage(ReportImage reportImage);
        void deleteImage(ReportImage reportImage);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopUpMenuOnImageClicked(image,(ReportImage)itemView.getTag());
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.image_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(imagesList.get(position));
        Picasso.get().load(new File(imagesList.get(position).getPath())).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return imagesList ==null?0: imagesList.size();
    }

    private void showPopUpMenuOnImageClicked(ImageView more, final ReportImage image){
        PopupMenu popupMenu=new PopupMenu(context,more);
        try {
            // to show icons
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popupMenu.getMenuInflater().inflate(R.menu.image_popup_menu,popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.showImage:activity.showImage(image);return true;
                    case R.id.deleteImage:activity.deleteImage(image);return true;
                }

                return false;
            }
        });
    }

}
