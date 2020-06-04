package com.example.ic2.presenter;

import android.content.Context;

import com.example.ic2.async.AsyncTaskCallback;
import com.example.ic2.async.report.SaveReportAsync;
import com.example.ic2.model.Report;
import com.example.ic2.model.User;
import com.example.ic2.retrofit.RetrofitClient;
import com.example.ic2.view.IQuickReportView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuickReportPresenter implements IReportPresenter {
    IQuickReportView quickReportView;
    Context context;

    public QuickReportPresenter(IQuickReportView quickReportView,Context context) {
        this.quickReportView = quickReportView;
        this.context=context;
    }

    @Override
    public void save(Report report) {
        new SaveReportAsync(report, this.context, new AsyncTaskCallback<Report>() {
            @Override
            public void handleResponse(Report response) {
                quickReportView.onSaveSuccess(response);
            }

            @Override
            public void handleFault(Exception e) {

            }
        }).execute();
    }

    @Override
    public void send(Report report) {
        RetrofitClient.getAPI().sendReport(User.getCurrentUser().getToken(),report).enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
                if (response.isSuccessful()){
                    quickReportView.onSendSuccess();
                }else{
                    quickReportView.OnError("Error");
                }
            }

            @Override
            public void onFailure(Call<Report> call, Throwable t) {
                quickReportView.OnError("Error");
            }
        });

    }



}
