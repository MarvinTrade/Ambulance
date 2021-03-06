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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientRegister extends AppCompatActivity {
    EditText mfullname,memail,mpassword,mphonetext;
    Button mregisterbtn;
    TextView mtextView;
    FirebaseAuth fAuth;
    ProgressBar progressbar;

    FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        mfullname = findViewById(R.id.fullname);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        mphonetext = findViewById(R.id.phonetext);
        mregisterbtn = findViewById(R.id.registerbtn);
        mtextView = findViewById(R.id.textView14);

        mtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientRegister.this, ClientLogIn.class);
                startActivity(intent);
                Toast.makeText(ClientRegister.this, "LogIn", Toast.LENGTH_SHORT).show();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        progressbar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), ClientsMaps.class));
            finish();
        }

        mregisterbtn.setOnClickListener(new View.OnClickListener() {
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

                //Register the User in Firebase.

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String userId = fAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients").child(userId);
                            current_user_db.setValue(true);

                            Toast.makeText(ClientRegister.this, "Client Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ClientLogIn.class));


                        }else{
                            Toast.makeText(ClientRegister.this, "Error !!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
    }

}
