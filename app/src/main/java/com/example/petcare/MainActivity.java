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

    private Button logi;
    private Button signu;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        EditText email = findViewById(R.id.registration_email_input);
        EditText password = findViewById(R.id.registration_password_input);
        EditText L_password = findViewById(R.id.login_password);
        EditText L_email = findViewById(R.id.login_email);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        logi = (Button) findViewById(R.id.log_in);
        signu = (Button) findViewById(R.id.sign_up);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        EditText email_input = findViewById(R.id.login_email);
        EditText password_input = findViewById(R.id.login_password);
        Button login_button = findViewById(R.id.log_in);
        Button signup_button = findViewById(R.id.sign_up);


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });

        login_button.setOnClickListener((v) -> loginUser(email_input.getText().toString(), password_input.getText().toString()));
    }

    private void loginUser(String L_email, String L_password)
    {
        if (L_email.isEmpty() || L_password.isEmpty() )
        {
            Toast.makeText(this, "No blank space allowed!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (L_password.length()<7 )
        {
            Toast.makeText(this, "Password must be at least 7 characters long ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if ( !L_email.contains("@"))
        {
            Toast.makeText(this, "Wrong email format", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(L_email, L_password)
                .addOnCompleteListener(this, this::handleLogin);

    }
    private void handleLogin(Task<AuthResult> authResultTask) {
        if (authResultTask.isSuccessful()) {
            Intent intent = new Intent (this, Your_account2.class);
            startActivity(intent);

        }
        else {
            Toast.makeText(this, "Invalid e-mail or password.", Toast.LENGTH_SHORT).show();

        }
    }

    public void openActivity3 ()
    {

        Intent intent = new Intent (this, registration.class);
        startActivity(intent);
    }



}