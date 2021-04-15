package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class pickGender extends AppCompatActivity {

    private ImageView male;
    private ImageView female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_gender2);
        male= (ImageView) findViewById(R.id.male);
        female= (ImageView) findViewById(R.id.female);
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
    }
    public void openActivity ()
    {
        Intent intent = new Intent (this, petInfo01.class);
        startActivity(intent);
    }
}