package com.example.ic2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ic2.R;
import com.example.ic2.model.DynamicInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DynamicInfoRecyclerViewAdapter extends RecyclerView.Adapter<DynamicInfoRecyclerViewAdapter.ViewHolder> {

    List<DynamicInfo> infoList;
    Context context;
    DynamicItemInterface activity;


    public DynamicInfoRecyclerViewAdapter(List<DynamicInfo> infoList, Context context, DynamicItemInterface activity) {
        this.infoList = infoList;
        this.context = context;
        this.activity=activity;
    }

    public interface DynamicItemInterface {
        void onDeleteLocation(DynamicInfo info);
        void onCheckedLocation(DynamicInfo info);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDelete;
        TextView tvLocation;
        CheckBox cbSelected;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvLocation=itemView.findViewById(R.id.tvLocation);
            cbSelected=itemView.findViewById(R.id.cbSelected);
            imgDelete=itemView.findViewById(R.id.imgDelete);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onDeleteLocation((DynamicInfo)itemView.getTag());
                }
            });
            cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    DynamicInfo info=(DynamicInfo)itemView.getTag();

                    if(b){
                        info.setStatus("selected");
                    }else{
                        info.setStatus("unselected");
                    }

                    activity.onCheckedLocation(info);
                }
            });
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.dynamic_info_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(infoList.get(position));
        holder.tvLocation.setText(infoList.get(position).getName());
        holder.cbSelected.setChecked(infoList.get(position).getStatus().equals("selected"));
    }

    @Override
    public int getItemCount() {
        return infoList ==null?0: infoList.size();
    }

}
