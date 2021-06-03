package com.example.petcare;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.example.petcare.db.Pet;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.petcare.db.Pet;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class viewPet extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    public final static String Arg_PetID = "Arg_PetID";
    private TextView name, breed, birth, weight, sex, type;
    private String petID;
    private Button editPet_btn, deletePet_btn;
    private ImageView imageView;
    private String imageUrl;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet);
        innitViews();

        petID = getIntent().getStringExtra(Arg_PetID);
        storage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = storage.getReference();
        firebaseFirestore.collection("pets").document(petID).addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    fillUI(value.toObject(Pet.class));
                }
            }
        });

        editPet_btn.setOnClickListener(v -> openActivityEditPet(petID));

    }

    private void innitViews() {
        name = findViewById(R.id.viewPet_name);
        breed = findViewById(R.id.viewPet_breed);
        birth = findViewById(R.id.viewPet_birthday);
        sex = findViewById(R.id.viewPet_sex);
        type = findViewById(R.id.viewPet_type);
        weight = findViewById(R.id.viewPet_weight);
        imageView = findViewById(R.id.viewPet_imageView);
        editPet_btn = findViewById(R.id.viewPet_edit_btn);


    }

    public void openActivityEditPet(String id) {
        Intent intent2 = new Intent (this, editPet.class);
        intent2.putExtra(editPet.Arg_PetID,id);
        startActivity(intent2);
    }


    @SuppressLint("SetTextI18n")
    private void fillUI(Pet pet) {

        name.setText(pet.getName());
        breed.setText("Breed: "+pet.getBreed());
        birth.setText("Age: "+pet.getDateOfBirth());
        weight.setText("Weight: "+pet.getWeight()+" kg");
        if(pet.isIsMale()){
            sex.setText("Sex: male");
        }
        else{
            sex.setText("Sex: female");
        }
        if(pet.isIsDog()){
            type.setText("dog");
        }
        else{
            type.setText("cat");
        }
        Glide.with(this)
                .load(pet.getPhoto())
                .into(imageView);
        imageUrl = pet.getPhoto();


    }












}