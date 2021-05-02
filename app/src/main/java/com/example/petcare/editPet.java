package com.example.petcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
    private EditText nameEt, breedEt, birthEt, weightEt;
    private SwitchCompat sterilisedBTN, medsBTN, maleBTN, dogBTN;
    private Spinner spinner;
    private String petID;
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
        breedEt = findViewById(R.id.editPet_bread_txt);
        birthEt = findViewById(R.id.editPet_birth_txt);
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
        breedEt.setText(pet.getBreed());
        birthEt.setText(pet.getDateOfBirth());
        weightEt.setText(pet.getWeight()+"");
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

    private Integer findMedsIndex(String text){
        String [] items = getResources().getStringArray(R.array.medsTimes);
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
                Uri storageUri = task.getResult();
                updatePet(storageUri.toString());
            }
        });
    }
    private void updatePet(String imageUrl) {
        String name = nameEt.getText().toString();
        String birth = birthEt.getText().toString();
        String breed = breedEt.getText().toString();
        Integer weight = Integer.parseInt(weightEt.getText().toString());
        boolean meds = medsBTN.isChecked();
        boolean sex = maleBTN.isChecked();
        boolean type = dogBTN.isChecked();
        boolean sterilised = sterilisedBTN.isChecked();
        String medsHowOften = getResources().getStringArray(R.array.medsTimes)[spinner.getSelectedItemPosition()];
        String ownerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        updatePet(name, breed, birth, weight, meds, sex, type, sterilised, medsHowOften, ownerID, petID, imageUrl);

    }

}