

package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class userAccount extends AppCompatActivity {
    private Button showPets;
    private Button signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_account);
        Intent intent = getIntent();
        showPets = (Button) findViewById(R.id.showPetsButton);
        showPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMyPets();
            }
        });
        signOut = (Button) findViewById(R.id.logoutButton);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
    }

    public void openActivityMyPets ()
    {
        Intent intent = new Intent (this, myPets.class);
        startActivity(intent);
    }



}
