package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcare.db.Pet;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class addingPicture extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_adding_picture);
        Button next = findViewById(R.id.addPicButton);
        next.setOnClickListener(v -> openActivity());
    }
    public void openActivity()
    {
        DocumentReference doc = db.collection("pets").document();
        doc.set(Pet.create(getIntent(),doc.getId(),mAuth.getCurrentUser().getUid(), ""))
                .addOnCompleteListener(this, this::handleAdd);
        Intent intent= new Intent(this,addPetFinish.class);
        startActivity(intent);
    }

    private void handleAdd(Task<Void> voidTask) {
        if(voidTask.isSuccessful()){
            Intent intent= new Intent(this,addPetFinish.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(this, "Error occurred"+ voidTask.getException(), Toast.LENGTH_SHORT).show();
        }
    }
}