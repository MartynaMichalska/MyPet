package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class faqCatsMain extends AppCompatActivity {
private  ImageView feed;
private  ImageView tricks;
private  ImageView social;
private  ImageView other;
private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_cats_main);
        innitViews();
        feed.setOnClickListener(v -> openActivityFeed());
        tricks.setOnClickListener(v -> openActivityTricks());
        social.setOnClickListener(v -> openActivitySocial());
        other.setOnClickListener(v -> openActivityOther());
        bt.setOnClickListener(v -> openActivityMainFaq());

    }

    private void openActivityMainFaq() {
        Intent intent = new Intent(this, faqChoice.class);
        startActivity(intent);
    }

    private void openActivityFeed() {
        Intent intent = new Intent(this, faqCatFeeding.class);
        startActivity(intent);
    }
    private void openActivityTricks() {
        Intent intent = new Intent(this, faqCatTricks.class);
        startActivity(intent);
    }
    private void openActivitySocial() {
        Intent intent = new Intent(this, faqCatSocial.class);
        startActivity(intent);
    }
    private void openActivityOther() {
        Intent intent = new Intent(this, faqCatOther.class);
        startActivity(intent);
    }



    public void innitViews()
    {
        feed = (ImageView) findViewById(R.id.catFaqFeed);
        tricks = (ImageView) findViewById(R.id.catFaqTricks);
        social = (ImageView) findViewById(R.id.catFaqSocial);
        other = (ImageView) findViewById(R.id.catFaqOther);
        bt = (Button) findViewById(R.id.backToMainMainFaq);

    }
}