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

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail,editTextPassword;
    Button buttonLogin;
    TextView textViewRegister;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //associate with xml
        editTextEmail=findViewById(R.id.edit_text_email);
        editTextPassword=findViewById(R.id.edit_text_password);
        buttonLogin=findViewById(R.id.button_login);
        textViewRegister=findViewById(R.id.text_view_register);

        progressDialog=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            //if the user don't logout
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
        //click listener for button login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isEditTextEmpty()){
                    Toast.makeText(LoginActivity.this, R.string.message_retry_enter_password, Toast.LENGTH_SHORT).show();

                }else {
                    String email=editTextEmail.getText().toString(),
                            password=editTextPassword.getText().toString();
                    progressDialog.setMessage("login  Please wait ....");
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()){
                                            finish();
                                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    }else{
                                        Toast.makeText(LoginActivity.this, R.string.message_retry_enter_password, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isEditTextEmpty() {
        String email=editTextEmail.getText().toString();
        String password=editTextPassword.getText().toString();
        if (TextUtils.isEmpty(email)){
            return true;
        }
        if (TextUtils.isEmpty(password)){
            return true;
        }
        return false;
    }

}
