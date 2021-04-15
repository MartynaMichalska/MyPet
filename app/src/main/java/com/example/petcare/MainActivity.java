package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;


public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button signu;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        EditText email = findViewById(R.id.login_email);
        EditText password = findViewById(R.id.login_haslo);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.log_in);
        signu = (Button) findViewById(R.id.sign_up);
        //mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString());

        button.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                openActivity2();
            }
        });


        signu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

    }

    public void openActivity2 ()
    {

        Intent intent = new Intent (this, Your_account2.class);
        startActivity(intent);
    }

    public void openActivity3 ()
    {

        Intent intent = new Intent (this, registration.class);
        startActivity(intent);
    }



}