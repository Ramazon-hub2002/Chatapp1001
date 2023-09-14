package com.cat.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout inputEmail;
    TextInputLayout inputPassword;
    Button btnLogin;
    TextView forgotPassword, createNewAccount;
    ProgressDialog mLoadingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnLogin);
        forgotPassword=findViewById(R.id.forgotPassword);
        createNewAccount=findViewById(R.id.alreadyhaveaccount);
        mLoadingBar= new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new  Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtamptLogin();
            }
        });

    }

    private void AtamptLogin() {
        String email = inputEmail.getEditText().getText().toString().trim();
        String password =inputPassword.getEditText().getText().toString().trim();


        if (email.isEmpty() || email.contains("@gmail")) {
            showError(inputEmail, "Email is Valid");
        } else if (password.isEmpty() || password.length() < 5) {
            showError(inputPassword, "Password must be greater then 5 latter ");
        } else {
            mLoadingBar.setTitle("Login");
            mLoadingBar.setMessage("Place wait, ");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Login is Success", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(LoginActivity.this,SetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


                private void showError(TextInputLayout filed, String text) {

                    filed.setError(text);
                    filed.requestFocus();

    }
}
