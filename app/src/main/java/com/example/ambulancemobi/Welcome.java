package com.example.ambulancemobi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    private Button DriverButtton;
    private Button ClientButtton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        DriverButtton = (Button) findViewById(R.id.driverbtn);
        ClientButtton = (Button) findViewById(R.id.clientbtn);

        DriverButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driverloginintent= new Intent(Welcome.this, DriverRegister.class);
                startActivity(driverloginintent);
            }
        });

        ClientButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clientloginintent= new Intent(Welcome.this, ClientRegister.class);
                startActivity(clientloginintent);
            }
        });
    }
}