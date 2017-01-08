package com.neuron.genieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button btnSubmit;
    @InjectView(R.id.forgot_input_email) EditText _editTextForgotEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.inject(this);

        btnSubmit = (Button) findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = _editTextForgotEmail.getText().toString();
                Toast.makeText(getApplicationContext(),"Sent change password email.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
