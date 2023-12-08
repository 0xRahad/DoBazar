package com.androidafe.dobazar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.databinding.ActivitySignupBinding;
import com.androidafe.dobazar.model.UserResponse;
import com.androidafe.dobazar.utils.ApiController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding signupBinding;

    EditText regName, regEmail, regPass;
    Button regBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(signupBinding.getRoot());

        //reference
        regEmail = findViewById(R.id.regMail);
        regPass = findViewById(R.id.regPass);
        regName = findViewById(R.id.regName);
        regBtn = findViewById(R.id.regBtn);


        signupBinding.goToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,MainActivity.class));
            }
        });


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = regName.getText().toString().trim();
                String userMail = regEmail.getText().toString().trim();
                String userPass = regPass.getText().toString().trim();

                registerUser(userName, userMail, userPass);
            }
        });



    }

    public void registerUser(String userName, String userMail, String userPass){

        if (userMail.isEmpty()){
            regEmail.setError("please enter your email");
        } else if (userPass.isEmpty()) {
            regPass.setError("please enter a password");
        } else if (userName.isEmpty()) {
            regName.setError("please enter your username");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(userMail).matches()) {
            regEmail.setError("Enter a valid email");
        } else if (userPass.length()<6) {
            regPass.setError("Password length must be 6 or more");
        }else {

            Call<UserResponse> call = ApiController.getInstance().getApi()
                    .getRegisterResponse("",userName,userMail,userPass,"","");

            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful()){
                        UserResponse userResponse = response.body();
                        Toast.makeText(SignupActivity.this, userResponse.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(SignupActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("error", "onResponse: "+t.getLocalizedMessage());
                }
            });

        }
    }

}