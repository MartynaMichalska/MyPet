package com.example.petcare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petcare.db.Pet;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class myPets extends AppCompatActivity {
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private Button addingPet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);
        addingPet= (Button) findViewById(R.id.addPet);

        Query query = firebaseFirestore.collection("pets").whereEqualTo("ownerId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirestoreRecyclerOptions<Pet> options = new FirestoreRecyclerOptions.Builder<Pet>()
                    .setQuery(query, Pet.class)
                    .build();
        addingPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        adapter = new FirestoreRecyclerAdapter<Pet, PetViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PetViewHolder holder, int position, @NonNull Pet pet) {
                holder.list_name.setText(pet.getName());
                holder.itemView.setOnClickListener(v -> navigateToViewPet(pet.getId()));
                holder.notificationsButton.setOnClickListener(v -> navigateToNotifications(pet.getId()));

            }

            @NonNull
            @Override
            public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pet_single, parent, false);
                return new PetViewHolder(view);

            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);


    }

    private void navigateToNotifications(String id) {
        Intent intent = new Intent(this, callendarActivity.class);
        intent.putExtra("petID", id);
        startActivity(intent);
    }

    private void navigateToViewPet(String id) {
        Intent intent = new Intent(this, viewPet.class);
        intent.putExtra(editPet.Arg_PetID,id);
        startActivity(intent);

    }


    private static class PetViewHolder extends RecyclerView.ViewHolder{
        private final TextView list_name;
        private final Button notificationsButton;


        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            notificationsButton=(Button) itemView.findViewById(R.id.notificationsBT);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void openActivity()
    {
        Intent intent = new Intent (this, chooseType.class);
        startActivity(intent);
    }
}
