package com.example.petcare;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.petcare.db.Pet;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class editPet extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    public final static String Arg_PetID = "Arg_PetID";
    private EditText nameEt, breedEt, birthEt, weightEt;
    private SwitchCompat sterilisedBTN, medsBTN, maleBTN, dogBTN;
    private Spinner spinner;
    private String petID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);
        innitViews();
        petID = getIntent().getStringExtra(Arg_PetID);
        firebaseFirestore = FirebaseFirestore.getInstance();
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
        findViewById(R.id.editPet_save_btn).setOnClickListener(v -> {
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
            updatePet(name, breed, birth, weight, meds, sex, type, sterilised, medsHowOften, ownerID, petID);
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
                           String petID){
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
                ""
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
}
