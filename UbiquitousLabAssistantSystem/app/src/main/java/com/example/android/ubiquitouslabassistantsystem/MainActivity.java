package com.example.android.ubiquitouslabassistantsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button Login;
    private EditText email;
    private EditText password;
    private TextView registerScreen;
    private TextView fp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting default screen to login.xml
        setContentView(R.layout.activity_main);



        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);

        registerScreen = findViewById(R.id.tvRegister);
        fp = findViewById(R.id.tvForgetPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (firebaseAuth.getCurrentUser() != null){
            // profile activity here

            finish();
            startActivity(new Intent(getApplicationContext(), ULA_Home.class));
        }








        progressDialog = new ProgressDialog(this);

        registerScreen.setOnClickListener(this);
        Login.setOnClickListener(this);
        fp.setOnClickListener(this);

    }

    private void userLogin(){
        String emailid = email.getText().toString().trim();
        String pword = password.getText().toString().trim();


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

        progressDialog.setMessage("You waited one year for Game of Thrones, Pls wait for sometime while we sign you in :) ");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emailid,pword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            // start the profile activity
                            checkEmailVerification();


                        }
                        else{
                            Toast.makeText(MainActivity.this, "Incorrect Email ID/Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    private void checkEmailVerification() {
        FirebaseUser nik = firebaseAuth.getInstance().getCurrentUser();
        boolean ev = nik.isEmailVerified();
        if(!ev){
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();


        }
        else {
            finish();
            startActivity(new Intent(MainActivity.this,ULA_Home.class));
        }
    }

    @Override
    public void onClick(View view) {
        if(view == Login){
            userLogin();
        }

        if(view == registerScreen){
            finish();

            startActivity(new Intent(this,RegisterActivity.class));
        }

        if (view == fp){

            finish();

            startActivity(new Intent(this,PasswordActivity.class));
        }
    }





}