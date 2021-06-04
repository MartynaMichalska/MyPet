package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class faqDogsMain extends AppCompatActivity {
    private  ImageView feed;
    private  ImageView tricks;
    private  ImageView behaviour;
    private  ImageView other;
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_dogs_main);
        innitViews();
        tricks.setOnClickListener(v -> openActivityTricks());
        behaviour.setOnClickListener(v -> openActivityBehaviour());
        feed.setOnClickListener(v -> openActivityOther());
        other.setOnClickListener(v -> openActivityTricks());
        bt.setOnClickListener(v -> openActivityMainFaq());

    }

    private void openActivityMainFaq() {
        Intent intent = new Intent(this, faqChoice.class);
        startActivity(intent);
    }



    private void openActivityTricks() {
        Intent intent = new Intent(this, faqDogTricks.class);
        startActivity(intent);
    }

    private void openActivityBehaviour() {
        Intent intent = new Intent(this, faqDogBehaviour.class);
        startActivity(intent);
    }

    private void openActivityFeeding() {
        Intent intent = new Intent(this, faqDogFeeding.class);
        startActivity(intent);
    }

    private void openActivityOther() {
        Intent intent = new Intent(this, faqDogOther.class);
        startActivity(intent);
    }




    public void innitViews()
    {
        feed = (ImageView) findViewById(R.id.feedingDog);
        tricks = (ImageView) findViewById(R.id.tricksDog);
        behaviour = (ImageView) findViewById(R.id.behaviourDog);
        other = (ImageView) findViewById(R.id.othersDog);
        bt = (Button) findViewById(R.id.backToMainMainFaq1);

    }
}