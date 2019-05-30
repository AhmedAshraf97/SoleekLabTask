package com.example.soleektask;

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

public class SignUp extends AppCompatActivity {

    Button Confirm,SignIn;
    EditText Email,Password,RePassword;
     FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        Confirm = findViewById(R.id.Button_SignUp_ConfirmButton);
        SignIn = findViewById(R.id.Button_SignUp_SignInButton);
        Email = findViewById(R.id.EditText_SignUp_Email);
        Password =  findViewById(R.id.EditText_SignUp_Password);
        RePassword =  findViewById(R.id.EditText_SignUp_ReEnterPassword);




        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SignUp.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });



        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, password,repassword;
                email = Email.getText().toString();
                password = Password.getText().toString();
                repassword = RePassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length()<8) {
                    Toast.makeText(getApplicationContext(), "Password size should be minimum 8 characters", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!(password.equalsIgnoreCase(repassword) )){
                    Toast.makeText(getApplicationContext(), "Please enter compatible passwords!", Toast.LENGTH_LONG).show();
                    return;
                }


                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(SignUp.this, SignIn.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}

