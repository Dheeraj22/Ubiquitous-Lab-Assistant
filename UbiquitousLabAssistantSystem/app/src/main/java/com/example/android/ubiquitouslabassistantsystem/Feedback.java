package com.example.android.ubiquitouslabassistantsystem;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

public class Feedback extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private EditText email;
    private EditText ph;
    private EditText msg;
    private Button submit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        ph = findViewById(R.id.etPhone);
        msg = findViewById(R.id.etMsg);
        submit = findViewById(R.id.btnSubmit);

        submit.setOnClickListener(this);


    }

    public void send() {



        if (name.getText().toString().equals(""))
            name.setError("Mandatory Field");
        else if (email.getText().toString().equals("")) {
            email.setError("Mandatory Field");
        } else if (ph.getText().toString().equals("")) {
            ph.setError("Mandatory Field");
        } else if (msg.getText().toString().equals("")) {
            msg.setError("Mandatory Field");
        } else {
            Intent i = new Intent(Intent.ACTION_SENDTO);
            i.setData(Uri.parse("mailto:"));
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ulasystemdev@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT,new String[]{"App Feedback"});
            i.putExtra(Intent.EXTRA_TEXT, " Dear ULA System developer \n"
                    + msg.getText().toString() + "\n Regards \n"
                    + name.getText().toString());



            try {
                startActivity(Intent.createChooser(i, "Send Feedback"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "No mail app found", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "unexpected error" + ex.toString(), Toast.LENGTH_SHORT).show();
            }


        }

    }

    @Override
    public void onClick(View view) {
        if (view == submit) {
            send();

        }
    }
}
