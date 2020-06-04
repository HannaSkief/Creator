package com.example.ic2.view;

import com.example.ic2.model.Report;

public interface IQuickReportView {
    void onSendSuccess();
    void onSaveSuccess(Report report);
    void OnError(String message);

}
