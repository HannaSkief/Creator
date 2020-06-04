package com.example.ic2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ic2.R;
import com.example.ic2.model.Report;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ArchiveRecyclerViewAdapter extends RecyclerView.Adapter<ArchiveRecyclerViewAdapter.ViewHolder> {

    List<Report> reportList;
    Context context;
    OnReportItemClicked activity;

    public ArchiveRecyclerViewAdapter(List<Report> reportList, Context context, OnReportItemClicked activity) {
        this.reportList = reportList;
        this.context = context;
        this.activity = activity;
    }


    public interface OnReportItemClicked{
        void onItemClick(Report report);
        void onImgDeleteClicked(Report report);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgReportIcon,imgDelete;
        TextView tvReportType,tvDate;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgReportIcon=itemView.findViewById(R.id.imgReportIcon);
            tvReportType=itemView.findViewById(R.id.tvReportType);
            tvDate=itemView.findViewById(R.id.tvDate);
            imgDelete=itemView.findViewById(R.id.imgDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onItemClick((Report)itemView.getTag());
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onImgDeleteClicked((Report)itemView.getTag());
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.report_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(reportList.get(position));
        holder.tvReportType.setText(reportList.get(position).getType());
        holder.tvDate.setText(reportList.get(position).getCreatedAt());
        setReportIcon(reportList.get(position).getType(),holder.imgReportIcon);

    }
    private void setReportIcon(String type,ImageView imageView){
        if(type.equals(context.getString(R.string.accident))){
            imageView.setImageResource(R.drawable.ic_accident);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorAccident),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(type.equals(context.getString(R.string.non_conformance))){
            imageView.setImageResource(R.drawable.ic_non_conformance);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorNonConformance),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(type.equals(context.getString(R.string.improvement))){
            imageView.setImageResource(R.drawable.ic_improvement);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorImprovement),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(type.equals(context.getString(R.string.near_miss))){
            imageView.setImageResource(R.drawable.ic_near_miss);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorNearMiss),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(type.equals(context.getString(R.string.prevention))){
            imageView.setImageResource(R.drawable.ic_prevention);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorPrevention),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }else if(type.equals(context.getString(R.string.observation))){
            imageView.setImageResource(R.drawable.ic_observ);
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.colorObservation),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public int getItemCount() {
        return reportList==null?0:reportList.size();
    }


}
