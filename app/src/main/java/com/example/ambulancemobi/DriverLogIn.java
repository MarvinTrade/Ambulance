package com.example.ambulancemobi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLogIn extends AppCompatActivity {
    TextView mtextView15;
    EditText memail,mpassword;
    Button mloginbtn;
    FirebaseAuth fAuth;
    ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_log_in);

        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        mloginbtn = findViewById(R.id.driverloginbtn);
        fAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressBar);
        mtextView15 = (TextView) findViewById(R.id.textView15);

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail.setError("Email is Empty");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is Empty");
                    return;
                }

                if(password.length()<8){
                    mpassword.setError("Password Must Have >=8 Characters ");
                    return;
                }

                progressbar.setVisibility(View.VISIBLE);

                //Authenticate The User.
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(DriverLogIn.this, "LogIn Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), DriverMaps.class));
                        }else {
                            Toast.makeText(DriverLogIn.this, "Error !!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        mtextView15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverLogIn.this, DriverRegister.class);
                startActivity(intent);
                Toast.makeText(DriverLogIn.this, "Create Account", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
