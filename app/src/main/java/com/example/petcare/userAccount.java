

package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class userAccount extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_account);
        Intent intent = getIntent();
        Button showPets = (Button) findViewById(R.id.showPetsButton);
        showPets.setOnClickListener(v -> openActivityMyPets());
        Button signOut = findViewById(R.id.logoutButton);

        signOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });
    }

    public void openActivityMyPets ()
    {
        Intent intent = new Intent (this, myPets.class);
        startActivity(intent);
    }



}
