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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private EditText password;
    private TextView signUp;
    private Button login;
    private TextView newPassword;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    private void setupUIViews(){
        email = (EditText)findViewById(R.id.etEmail);
        password = (EditText)findViewById(R.id.etPassword);
        signUp = (TextView)findViewById(R.id.tvRegister);
        login = (Button)findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        newPassword = findViewById(R.id.tvNewPassword);
    }

    @Override
    public void onClick(View view) {
        if(view == signUp){
            signUpUser();
        }

        if(view == login){
            Intent intent = new Intent(MainActivity.this, ULA_Home.class);
            startActivity(intent);
//            if(Validate()){
//                registerUser();
//            }else{
//                return;
//            }
        }
    }

    private void signUpUser(){

    }

    private boolean Validate(){
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        boolean valid = true;

        if (emailText.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else{
            email.setError(null);
        }

        if (passwordText.isEmpty() || passwordText.length() < 4 || passwordText.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;

    }

    private void registerUser(){

        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        progressDialog.setMessage("Registering User");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

        Intent intent = new Intent(MainActivity.this, ULA_Home.class);
        startActivity(intent);


    }

}
