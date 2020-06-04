package com.example.ic2.view;

import com.example.ic2.model.User;

public interface ISignUpView {

    void onSignUpSuccess(User user);
    void onWrongEmail(String message);
    void onWrongPassword(String message);
    void onNetworkProblem(String message);
}
