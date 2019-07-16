package com.example.shopwise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileSettingsActivity extends AppCompatActivity  {
    private Button loginButton,registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginButton=(Button) findViewById(R.id.loginButton);

        registerButton=(Button) findViewById(R.id.signupButton);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null) {
            startActivity(new Intent(ProfileSettingsActivity.this, ProfileActivity.class));
//            loginButton.setVisibility(View.GONE);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileSettingsActivity.this, UserLogin.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileSettingsActivity.this, SignupActivity.class));
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//
//        Intent i;
//
//        switch (view.getId()){
//            case R.id.loginButton :
//                i = new Intent(this, UserLogin.class);
//                startActivity(i);
//                break;
//            case R.id.signupButton:
//                i = new Intent(this, SignupActivity.class);
//                startActivity(i);
//                break;
//            default:break;
//        }
//
//    }


}
