package com.example.android.ubiquitouslabassistantsystem;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button Reset;
    private EditText regmail;
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        Reset = findViewById(R.id.btnReset);
        regmail = findViewById(R.id.etEmail);
        firebaseAuth = FirebaseAuth.getInstance();




        Reset.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        if (view == Reset){
            resetpassword();
        }

    }

    private void resetpassword() {

        String remail = regmail.getText().toString().trim();

        if (TextUtils.isEmpty(remail)){
            // registered email id is blank
            Toast.makeText(this, "Please enter your registered Email ID", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.sendPasswordResetEmail(remail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(PasswordActivity.this, "Password Reset Mail has been sent", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(PasswordActivity.this,MainActivity.class));
                }
                else {
                    Toast.makeText(PasswordActivity.this, "Error in sending Password Reset Mail", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
