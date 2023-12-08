package com.androidafe.dobazar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidafe.dobazar.R;
import com.androidafe.dobazar.databinding.ActivityMainBinding;
import com.androidafe.dobazar.model.UserResponse;
import com.androidafe.dobazar.utils.ApiController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    EditText loginUserName, loginPass;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        //reference
        loginUserName = findViewById(R.id.loginEmail);
        loginPass = findViewById(R.id.loginPass);
        loginBtn = findViewById(R.id.loginBtn);

        checkUserAuthStatus();

        mainBinding.goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignupActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = loginUserName.getText().toString().trim();
                String userPass = loginPass.getText().toString().trim();

                loginUser(userName, userPass);
            }
        });
    }

    private void loginUser(String userName, String userPass) {


        if (userName.isEmpty()){
            loginUserName.setError("please enter your username");
        } else if (userPass.isEmpty()) {
            loginPass.setError("please enter a password");
        }else {

            Call<UserResponse> call = ApiController.getInstance().getApi()
                    .getLoginResponse(userName,userPass);

            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful()){
                        UserResponse userResponse = response.body();
                        Toast.makeText(MainActivity.this, userResponse.getMsg(), Toast.LENGTH_SHORT).show();

                        if (userResponse.getCode() == 200){
                            SharedPreferences sharedPreferences = getSharedPreferences("status", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userName",userName);
                            editor.putBoolean("loginStatus",true);
                            editor.apply();

                            startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                            finish();

                        }

                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("error", "onResponse: "+t.getLocalizedMessage());
                }
            });

        }

    }

    void checkUserAuthStatus(){
        SharedPreferences preferences = getSharedPreferences("status", MODE_PRIVATE);
        boolean status = preferences.getBoolean("loginStatus", false);
        if (status) {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            finish();
        }
    }
}