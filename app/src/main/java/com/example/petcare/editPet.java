package com.example.petcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class editPet extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    public final static String Arg_PetID = "Arg_PetID";
    private EditText nameEt,  weightEt;
    private SwitchCompat sterilisedBTN, medsBTN, maleBTN, dogBTN;
    private Spinner spinner;
    private Spinner day;
    private Spinner mont;
    private Spinner yr;
    private Spinner breed;
    private String petID;
    private String datka;
    private ImageView imageView;
    private String imageUrl;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);
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

    }

    private void innitViews() {
        nameEt = findViewById(R.id.editPet_name_txt);
        breed = findViewById(R.id.editPetBreed);
        day = findViewById(R.id.editDateDay);
        mont = findViewById(R.id.editDateMonth);
        yr = findViewById(R.id.editDateYear);
        weightEt = findViewById(R.id.editPet_weight_txt);
        spinner = findViewById(R.id.editPet_spinner_view);
        medsBTN = findViewById(R.id.editPet_meds_btn);
        dogBTN = findViewById(R.id.editPet_dog_btn);
        maleBTN = findViewById(R.id.editPet_sex_btn);
        sterilisedBTN = findViewById(R.id.editPet_sterilized_btn);
        imageView = findViewById(R.id.editPet_imageView);
        findViewById(R.id.editPet_save_btn).setOnClickListener(v -> {
            if(imageUri != null){
                uploadPicture();
            }
            else{
                updatePet(imageUrl);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
    }


    private void fillUI(Pet pet) {
        Log.d("pet:","isMale:"+pet.isIsMale());
        nameEt.setText(pet.getName());
        weightEt.setText(pet.getWeight()+"");
        breed.setSelection(findBreedIndex(pet.getBreed()));
        spinner.setSelection(findMedsIndex(pet.getMedsHowOften()));
        sterilisedBTN.setChecked(pet.isIsSterlilised());
        medsBTN.setChecked(pet.isIsMeds());
        dogBTN.setChecked(pet.isIsDog());
        maleBTN.setChecked(pet.isIsMale());
        Glide.with(this)
                .load(pet.getPhoto())
                .into(imageView);
        imageUrl = pet.getPhoto();


    }

    private void updatePet(String name,
                           String breed,
                           String birth,
                           int weight,
                           boolean meds,
                           boolean sex,
                           boolean isDog,
                           boolean sterilised,
                           String medsHowOften,
                           String ownerID,
                           String petID,
                           String photo){
        //public Pet(String id, String ownerId, boolean isSterlilised, boolean isMeds, boolean isMale,
        //boolean isDog, String name, String breed, String dateOfBirth, int weight, String medsHowOften, String photo)
        if(dataValidation()) {
            firebaseFirestore.collection("pets").document(petID).set(new Pet(petID,
                    ownerID,
                    sterilised,
                    meds,
                    sex,
                    isDog,
                    name,
                    breed,
                    birth,
                    weight,
                    medsHowOften,
                    photo
            )).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    onBackPressed();
                }
            });
        }
    }



    private boolean dataValidation() {
        Integer weightt = Integer.parseInt(weightEt.getText().toString());
        if(nameEt.getText().toString().isEmpty() || weightEt.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please fill the blank spaces ! ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!(nameEt.getText().toString().length()>0))
        {
            Toast.makeText(this, "Name is too short!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(weightt>40  || weightt<0 )
        {
            Toast.makeText(this, "Wrong weight value!",Toast.LENGTH_SHORT).show();
            return false;
        }
return true;
    }

    private Integer findMedsIndex(String text){
        String [] items = getResources().getStringArray(R.array.medsTimes);
        for(int i = 0; i<items.length; i++){
            if(items[i].equals(text)){
                return i;
            }
        }
        return 0;

    }
    private Integer findBreedIndex (String text)
    {
        String [] items = getResources().getStringArray(R.array.Strain);
        for(int i = 0; i<items.length; i++){
            if(items[i].equals(text)){
                return i;
            }
        }
        return 0;


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
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading...");
        pd.show();
        byte[] dataToSend = getFromImageView(imageView);
        if(dataToSend == null){
            pd.cancel();
            Snackbar.make(findViewById(android.R.id.content), "Image resize error", Snackbar.LENGTH_LONG).show();
            return;
        }

        final String randomKey = UUID.randomUUID().toString();
        StorageReference petPic = storageReference.child("images/"+randomKey);
        petPic.putBytes(dataToSend)
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

    private byte [] getFromImageView(ImageView imageView)
    {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;

    }

    private void getDownloadUrl(StorageReference petPic) {
        petPic.getDownloadUrl().addOnCompleteListener(this, new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri storageUri = task.getResult();
                updatePet(storageUri.toString());
            }
        });
    }
    private void updatePet(String imageUrl) {
        datka = day.getItemAtPosition(day.getSelectedItemPosition())+"."+mont.getItemAtPosition(mont.getSelectedItemPosition())+"."+yr.getItemAtPosition(yr.getSelectedItemPosition());
        String name = nameEt.getText().toString();
        Integer weight = Integer.parseInt(weightEt.getText().toString());
        boolean meds = medsBTN.isChecked();
        boolean sex = maleBTN.isChecked();
        boolean type = dogBTN.isChecked();
        boolean sterilised = sterilisedBTN.isChecked();
        String breed2 = getResources().getStringArray(R.array.Strain)[breed.getSelectedItemPosition()];
        String medsHowOften = getResources().getStringArray(R.array.medsTimes)[spinner.getSelectedItemPosition()];
        String ownerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        updatePet(name, breed2, datka, weight, meds, sex, type, sterilised, medsHowOften, ownerID, petID, imageUrl);

    }

}