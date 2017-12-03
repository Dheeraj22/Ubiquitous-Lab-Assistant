package com.example.android.ubiquitouslabassistantsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button register;
    private EditText email;
    private EditText name;
    private EditText password;
    private TextView signin;
    private ProgressDialog progressDialog;
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();




        if (user != null){
            // profile activity here

            finish();
            startActivity(new Intent(getApplicationContext(), ULA_Home.class));
        }



        register = findViewById(R.id.btnRegister);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signin = findViewById(R.id.tvSignin);

        register.setOnClickListener(this);
        signin.setOnClickListener(this);



    }

    private void registerUser() {
        String fullname = name.getText().toString().trim();
        String emailid = email.getText().toString().trim();
        String pword = password.getText().toString().trim();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        if(TextUtils.isEmpty(fullname)){
            //fullname is blank
            Toast.makeText(this, "A man has no name ? Please enter your Full Name",Toast.LENGTH_SHORT).show();;
            return;
        }

        if (TextUtils.isEmpty(emailid)){
            // email id is blank
            Toast.makeText(this,"Please enter your Email ID",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pword)){
            // password is empty
            Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();
        }

        // if all validations are okay, a progress bar wil be displayed

        progressDialog.setMessage("You waited one year for Game of Thrones, Pls wait for sometime while we register you :) ");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailid,pword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()){



                            emailVerification();







                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Oops, could'nt register, Winter is coming",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void emailVerification() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Successfully registered,Verification mail has been sent", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Verification mail hasn't been sent", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    @Override
    public void onClick(View view) {

        if(view == register ){
            registerUser();
        }

        if(view == signin){
            // will open sign in activity
            startActivity(new Intent(this, MainActivity.class));

        }

    }


}


