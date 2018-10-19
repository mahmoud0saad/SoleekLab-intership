package com.example.android.soleeklapintership;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.os.Build.VERSION_CODES.O;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextEmail,editTextPassword;
    Button buttonRegister;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //associate with xml
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonRegister = findViewById(R.id.button_register);

        progressDialog=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password;
                email=editTextEmail.getText().toString();
                password=editTextPassword.getText().toString();
                if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, R.string.message_error_register, Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setMessage("Registering Please wait ....");
                    progressDialog.show();
               firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()){
                                    finish();
                                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                }else {
                                    Toast.makeText(RegisterActivity.this, R.string.message_retry_enter_password, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            }
        });
    }

}
