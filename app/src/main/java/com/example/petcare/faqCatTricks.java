package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class faqCatTricks extends AppCompatActivity {
private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_cat_tricks);
        back = findViewById(R.id.backToMainFaq3);
        back.setOnClickListener(v -> openMainAc());
    }

    private void openMainAc() {
        Intent intent = new Intent(this, faqCatsMain.class);
        startActivity(intent);
    }
}