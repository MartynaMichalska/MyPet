package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petcare.db.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class registration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        EditText email_input=findViewById(R.id.registration_email_input);
        EditText password_input=findViewById(R.id.registration_password_input);
        Button register_button=findViewById(R.id.registration_register_bt);
        register_button.setOnClickListener((v)->registerUser(email_input.getText().toString(),password_input.getText().toString()));

    }
    private void registerUser(String email, String password)
    {
        if (email.isEmpty() || password.isEmpty() )
        {
            Toast.makeText(this, "No blank space allowed!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password.length()<7 )
        {
            Toast.makeText(this, "Password must be at least 7 characters long ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if ( !email.contains("@"))
        {
            Toast.makeText(this, "Wrong email format", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, this::handleRegistration);

    }

    private void handleRegistration(Task<AuthResult> authResultTask) {
        if (authResultTask.isSuccessful()) {

            addUserToDb(mAuth.getCurrentUser().getEmail(),mAuth.getCurrentUser().getUid());
        }
        else {
            Toast.makeText(this, "Error:"+authResultTask.getException(), Toast.LENGTH_SHORT).show();

        }
    }

    private void addUserToDb(String email, String uid) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").add(new User(uid, email))
                .addOnCompleteListener(this,this::handleDbResult);

    }

    private void handleDbResult(Task<DocumentReference> documentReferenceTask) {
        if(documentReferenceTask.isSuccessful())
        {
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            mAuth.getCurrentUser().delete();
            Toast.makeText(this, "Error:"+documentReferenceTask.getException(), Toast.LENGTH_SHORT).show();
        }
    }
}