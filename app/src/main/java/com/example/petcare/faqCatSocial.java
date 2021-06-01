package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class faqCatSocial extends AppCompatActivity {
private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_cat_social);
        bt=(Button) findViewById(R.id.backToMainFaqq);
        bt.setOnClickListener(v -> openActivityFaq());
    }

    private void openActivityFaq() {
        Intent intent= new Intent(this, faqCatsMain.class);
        startActivity(intent);
    }
}