package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class catsFaqTransition extends AppCompatActivity {
    private Button bck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cats_faq_transition);
        bck=(Button) findViewById(R.id.backToMainFaq2);
        bck.setOnClickListener(v -> openMainFaq());
    }
    private void openMainFaq() {
        Intent intent= new Intent (this, faqCatsMain.class);
        startActivity(intent);
    }
}