package com.example.exampractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {

    private EditText name, email, pass, confirmPass;
    private Button signupB;
    private ImageView backB;

    private FirebaseAuth mAuth;
    private String emailStr, passStr, confirmPassStr, nameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.username);
        email = findViewById(R.id.emailID);
        pass = findViewById(R.id.pword);
        confirmPass = findViewById(R.id.confirm_pass);

        signupB = findViewById(R.id.signUpB);

        backB = findViewById(R.id.backB);



        mAuth = FirebaseAuth.getInstance();


        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate()){
                    signupNewUser();
                }


            }
        });



    }


    private boolean validate()
    {

        nameStr = name.getText().toString().trim();
        emailStr = email.getText().toString().trim();
        passStr = pass.getText().toString().trim();
        confirmPassStr = confirmPass.getText().toString().trim();


        if (nameStr.isEmpty())
        {
            name.setError("Enter Name");
            return false;
        }
        if (emailStr.isEmpty())
        {
            email.setError("Enter E-Mail");
            return false;
        }

        if (passStr.isEmpty())
        {
            pass.setError("Enter Password");
            return false;
        }
        if (confirmPassStr.isEmpty())
        {
            confirmPass.setError("Enter Password Again");
            return false;
        }

        if (passStr.compareTo(confirmPassStr) != 0)
        {
            Toast.makeText(SignUpActivity.this,"Passwords do no match", Toast.LENGTH_SHORT);
            pass.setError("Passwords do no match");
            confirmPass.setError("Passwords do no match");

            return false;
        }



        return true;
    }

    private void signupNewUser()
    {
        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(SignUpActivity.this, "Sign Up Successful",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            SignUpActivity.this.finish();

                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}