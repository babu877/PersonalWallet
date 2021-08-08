package com.example.personalwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Form extends AppCompatActivity {

    private EditText txtEmail,txtPassword,txtConfirmPass;
    private ProgressBar progressBar;
    private Button btnRegister;
    private FirebaseAuth objFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up__form);
        getSupportActionBar().setTitle("Signup Form");

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPass = findViewById(R.id.txtConfirmPass);
        progressBar = findViewById(R.id.progressBar);
        btnRegister = findViewById(R.id.btnRegister);

        objFirebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confirmPass = txtConfirmPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp_Form.this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp_Form.this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confirmPass)){
                    Toast.makeText(SignUp_Form.this,"Please Enter Your Confirm Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length() < 6){
                    Toast.makeText(SignUp_Form.this,"Your Password To Short",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.VISIBLE);

                if(password.equals(confirmPass)){

                    objFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp_Form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(),Login_Form.class));
                                        Toast.makeText(SignUp_Form.this, "Registration Completed",Toast.LENGTH_SHORT).show();
                                    } else {

                                        Toast.makeText(SignUp_Form.this, "Authentication Failed",Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }
}
