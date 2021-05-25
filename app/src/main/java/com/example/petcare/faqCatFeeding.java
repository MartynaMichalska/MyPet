package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class faqCatFeeding extends AppCompatActivity {
private ImageView dry;
private ImageView train;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_cat_feeding);
        dry=(ImageView) findViewById(R.id.dryFood);
        train=(ImageView) findViewById(R.id.transitionCat);
        dry.setOnClickListener(v -> openActivityDry());
        train.setOnClickListener(v -> openActivityTrans());
    }

    private void openActivityTrans() {
        Intent intent = new Intent(this, catsFaqTransition.class);
        startActivity(intent);
    }

    private void openActivityDry() {
        Intent intent = new Intent(this, faqDryFood.class);
        startActivity(intent);
    }
}