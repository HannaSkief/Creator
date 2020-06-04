package com.example.ic2.signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ic2.MainActivity;
import com.example.ic2.R;
import com.example.ic2.model.User;
import com.example.ic2.presenter.SignUpPresenter;
import com.example.ic2.view.ISignUpView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity implements ISignUpView {

    Button btnSignUp;
    TextInputLayout tfEmail,tfPassword;
    TextInputEditText etEmail,etPassword;
    ProgressBar progressBar;
    MaterialCardView cardView;
    SharedPreferences sharedPreferences;

    SignUpPresenter signUpPresenter;

    boolean isLoggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        isLoggedIn=sharedPreferences.getBoolean("isLoggedIn",false);
        initView();
        signUpPresenter=new SignUpPresenter(this);

        if(isLoggedIn){
            User.initCurrentUser(sharedPreferences);
            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            finish();
        }
    }

    private void initView() {
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        tfPassword=findViewById(R.id.tfPassword);
        tfEmail=findViewById(R.id.tfEmail);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        cardView=findViewById(R.id.card);

        btnSignUp=findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp(){
        tfEmail.setError(null);
        tfPassword.setError(null);
        showProgressBar(true);

        signUpPresenter.onSignUp(etEmail.getText().toString(),etPassword.getText().toString(),this);
    }

    @Override
    public void onSignUpSuccess(User user) {
        User.saveCurrentUserInfo(user,sharedPreferences);
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
    }

    @Override
    public void onWrongEmail(String message) {
        showProgressBar(false);
        tfEmail.setError(message);
    }

    @Override
    public void onWrongPassword(String message) {
        showProgressBar(false);
        tfPassword.setError(message);
    }

    @Override
    public void onNetworkProblem(String message) {
        showProgressBar(false);

        Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), message, BaseTransientBottomBar.LENGTH_INDEFINITE);
        snackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        snackbar.show();

    }

    private void showProgressBar(boolean show){
        if(show){
            progressBar.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);

        }else{
            progressBar.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);

        }
    }




}
