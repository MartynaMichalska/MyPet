package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class faqDogOther extends AppCompatActivity {
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_dog_other);
        back = (Button) findViewById(R.id.backDogsOther);
        back.setOnClickListener(v -> openMainAc());
    }

    private void openMainAc() {
        Intent intent= new Intent(this, faqDogsMain.class);
        startActivity(intent);
    }
}