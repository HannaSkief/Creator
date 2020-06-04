package com.example.ic2.presenter;


import android.content.Context;
import android.util.Patterns;

import com.example.ic2.common.Common;
import com.example.ic2.model.User;
import com.example.ic2.retrofit.RetrofitClient;
import com.example.ic2.view.ISignUpView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter implements ISignUpPresenter {

    ISignUpView signUpView;

    public SignUpPresenter(ISignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void onSignUp(String email, String password, Context context) {

        if(email.isEmpty()){
            signUpView.onWrongEmail("Required field");
            return;
        }

        if(password.isEmpty()){
            signUpView.onWrongPassword("Required field");
            return;
        }

        if(!email.matches(Patterns.EMAIL_ADDRESS.pattern())){
            signUpView.onWrongEmail("Invalid email ");
            return;
        }
        if(!Common.isNetworkAvailable(context)){
            signUpView.onNetworkProblem("No internet connection !");
            return;
        }


        User user=new User(email,password);
        RetrofitClient.getAPI().login(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    signUpView.onSignUpSuccess(response.body());
                }else{
                    signUpView.onWrongEmail("wrong email");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                signUpView.onNetworkProblem("Can not Connect !");
            }
        });





    }
}


