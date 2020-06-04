package com.example.ic2.presenter;

import com.example.ic2.model.Report;

public interface IReportPresenter {
     void save(Report report);
     void send(Report report);
}
