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
private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_choice);
        cat= (Button) findViewById(R.id.chooseCatFAQ);
        dog= (Button) findViewById(R.id.chooseDogFAQ);
        back = ( Button) findViewById(R.id.backChoice);
        dog.setOnClickListener(v -> openActivityDogs());
        cat.setOnClickListener(v -> openActivityCats());
        back.setOnClickListener(v -> openMainActivity());
    }

    private void openMainActivity() {
        Intent intent= new Intent ( this, userAccount.class);
        startActivity(intent);
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