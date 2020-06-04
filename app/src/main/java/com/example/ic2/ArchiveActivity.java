package com.example.ic2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ic2.adapter.ArchiveRecyclerViewAdapter;
import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.async.report.DeleteReportAsync;
import com.example.ic2.async.report.GetAllSavedReportAsync;
import com.example.ic2.async.reportImage.DeleteImagesAsync;
import com.example.ic2.common.Common;
import com.example.ic2.model.Report;
import com.example.ic2.quickReportActivity.QuickReportActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArchiveActivity extends AppCompatActivity implements ArchiveRecyclerViewAdapter.OnReportItemClicked {

    ImageView imgBack;
    RecyclerView rvArchive;
    List<Report> reportList;
    ArchiveRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        imgBack=findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rvArchive=findViewById(R.id.rvArchive);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rvArchive.setLayoutManager(layoutManager);

        new GetAllSavedReportAsync(this, new AsyncTaskCallback<List<Report>>() {
            @Override
            public void handleResponse(List<Report> response) {
               // Toast.makeText(ArchiveActivity.this, ""+response.size(), Toast.LENGTH_SHORT).show();
                reportList=response;
                adapter=new ArchiveRecyclerViewAdapter(reportList,ArchiveActivity.this,(ArchiveRecyclerViewAdapter.OnReportItemClicked)ArchiveActivity.this);
                rvArchive.setAdapter(adapter);
            }

            @Override
            public void handleFault(Exception e) {
                Toast.makeText(ArchiveActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).execute();



    }


    @Override
    public void onItemClick(Report report) {

        Common.selected_report=report;
        startActivity(new Intent(ArchiveActivity.this, QuickReportActivity.class));
    }

    @Override
    public void onImgDeleteClicked(final Report report) {

        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setTitle("Delete this report ?");
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 1.Delete report from database
                new DeleteReportAsync(report, ArchiveActivity.this, new AsyncTaskCallback<Report>() {
                    @Override
                    public void handleResponse(Report response) {
                        // 2.delete report images from database and files
                        new DeleteImagesAsync(report, ArchiveActivity.this, new AsyncTaskCallback<Boolean>() {
                            @Override
                            public void handleResponse(Boolean response) {
                                reportList.remove(report);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void handleFault(Exception e) {

                            }
                        }).execute();
                    }

                    @Override
                    public void handleFault(Exception e) {

                    }
                }).execute();
            }
        });
        dialog.setNegativeButton(getString(R.string.cancel),null);
        dialog.show();
    }
}
