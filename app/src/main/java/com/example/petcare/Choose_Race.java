package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Choose_Race extends AppCompatActivity {

    private ImageView dog;
    private ImageView cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_race);
        dog= (ImageView) findViewById(R.id.doggie);
        cat= (ImageView) findViewById(R.id.cat);
        dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
    }
    public void openActivity ()
    {
        Intent intent = new Intent (this, pickGender.class);
        startActivity(intent);
    }
}