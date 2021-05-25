package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class faqDryFood extends AppCompatActivity {
    private Button bck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_dry_food);
        bck=(Button) findViewById(R.id.backToMainFaq);
        bck.setOnClickListener(v -> openMainFaq());
    }

    private void openMainFaq() {
        Intent intent= new Intent (this, faqCatsMain.class);
        startActivity(intent);
    }
}