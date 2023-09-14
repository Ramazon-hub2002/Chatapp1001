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

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout inputEmail, inputPassword,inputConfirmPassword;
    Button btnRegister;
    TextView alreadyHaveAccount;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        mLoadingBar= new ProgressDialog(this);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        btnRegister=findViewById(R.id.btnLogin);
        alreadyHaveAccount=findViewById(R.id.alreadyhaveaccount);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtempRegistration();
            }
        });
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new  Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AtempRegistration() {
        String email= inputEmail.getEditText().getText().toString();
        String password= inputPassword.getEditText().getText().toString();
        String confirmPassword=inputConfirmPassword.getEditText().getText().toString();


        if (email.isEmpty()|| email.contains("@gmail"))
        {
            showError(inputEmail,"Email is Valid");
        } else if (password.isEmpty() || password.length()<5)
        {
            showError(inputPassword,"Password must be greater then 5 latter ");
        } else if (!confirmPassword.equals(password))
        {
            showError(inputConfirmPassword,"Password did not Match!");
        }
            else
        {
            mLoadingBar.setTitle("Registration");
            mLoadingBar.setMessage("Place wait, ");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                   {
                       mLoadingBar.dismiss();
                       Toast.makeText(RegisterActivity.this, "Registration is Success", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(RegisterActivity.this,SetupActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                   } else
                    {
                        Toast.makeText(RegisterActivity.this, "Registration is Filed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(TextInputLayout filed, String emailIsValid) {
        filed.setError(emailIsValid);
        filed.requestFocus();

    }
}