package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.petcare.R;
import com.example.petcare.faqCatsMain;
import com.example.petcare.faqDogsMain;

public class faqChoice extends AppCompatActivity {
private Button cat;
private Button dog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_choice);
        cat= (Button) findViewById(R.id.chooseCatFAQ);
        dog= (Button) findViewById(R.id.chooseDogFAQ);
        dog.setOnClickListener(v -> openActivityDogs());
        cat.setOnClickListener(v -> openActivityCats());
    }

    private void openActivityCats() {
        Intent intent = new Intent(this, faqCatsMain.class);
        startActivity(intent);
    }
    private void openActivityDogs() {
        Intent intent = new Intent(this, faqDogsMain.class);
        startActivity(intent);
    }

}