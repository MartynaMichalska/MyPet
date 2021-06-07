package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class faqDogFeeding extends AppCompatActivity {
    private Button bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_dog_food);
        bt = findViewById(R.id.backDogsFeeding);
        bt.setOnClickListener(v -> backToMain());
    }

    private void backToMain() {
        Intent intent = new Intent(this, faqDogsMain.class);
        startActivity(intent);
    }
}
