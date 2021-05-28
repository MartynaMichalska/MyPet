package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class faqCatFeeding extends AppCompatActivity {
private ImageView dry;
private ImageView train;
private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_cat_feeding);
        dry=(ImageView) findViewById(R.id.dryFood);
        train=(ImageView) findViewById(R.id.transitionCat);
        bt=(Button) findViewById(R.id.backToMainFaqqq);
        dry.setOnClickListener(v -> openActivityDry());
        train.setOnClickListener(v -> openActivityTrans());
        bt.setOnClickListener(v -> openActivityBack());
    }

    private void openActivityBack() {
        Intent intent = new Intent(this, faqCatsMain.class);
        startActivity(intent);
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