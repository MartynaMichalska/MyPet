package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class faqDogTricks extends AppCompatActivity {
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_dog_tricks);
        back = (Button) findViewById(R.id.backDogsTricks);
        back.setOnClickListener(v -> openMainAc());
    }

    private void openMainAc() {
        Intent intent= new Intent(this, faqDogsMain.class);
        startActivity(intent);
    }
}