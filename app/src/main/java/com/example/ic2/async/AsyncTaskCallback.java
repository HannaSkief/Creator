package com.example.ic2.async;

public interface AsyncTaskCallback<T> {
    void handleResponse(T response);
    void handleFault(Exception e);
}