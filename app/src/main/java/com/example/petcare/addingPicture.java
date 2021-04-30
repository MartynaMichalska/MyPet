package com.example.petcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petcare.db.Pet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class addingPicture extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ImageView picture;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri storageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_adding_picture);
        Button next = findViewById(R.id.addPicButton);
        picture= findViewById(R.id.yourPetPic);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        next.setOnClickListener(v -> openActivity());
    }


    private void choosePicture() {
        Intent intent = new Intent ();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageUri = data.getData();
            picture.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading...");
        pd.show();
        /*byte[] dataToSend = resizeImage(imageUri);
        if(dataToSend == null){
            pd.cancel();
            Snackbar.make(findViewById(android.R.id.content), "Image resize error", Snackbar.LENGTH_LONG).show();
            return;
        }*/

        final String randomKey = UUID.randomUUID().toString();
        StorageReference petPic = storageReference.child("images/"+randomKey);
        petPic.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        getDownloadUrl(petPic);
                        Snackbar.make(findViewById(android.R.id.content), "Image uploaded", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progresPercent = ( 100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: "+(int) progresPercent + "%");
                    }
                });
    }

    private byte[] resizeImage(Uri imageUri) {
        try {
            Bitmap bitmap = new Compressor(this)
                    .setMaxHeight(800) //Set height and width
                    .setMaxWidth(600)
                    .setQuality(85) // Set Quality
                    .compressToBitmap(new File(imageUri.getPath()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            return baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    private void getDownloadUrl(StorageReference petPic) {
        petPic.getDownloadUrl().addOnCompleteListener(this, new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                storageUri = task.getResult();
            }
        });
    }


    public void openActivity()
    {
        if(storageUri == null){
            Toast.makeText(this, "Photo was not taken.", Toast.LENGTH_SHORT).show();
            return;
        }
        DocumentReference doc = db.collection("pets").document();
        doc.set(Pet.create(getIntent(),doc.getId(),mAuth.getCurrentUser().getUid(), storageUri.toString()))
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