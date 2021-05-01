package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


public class petInfo02 extends AppCompatActivity {
    private Button button;
    private Spinner ifSterilised;
    private Spinner ifMeds;
    private Spinner medsHowOften;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info02);
        button= (Button) findViewById(R.id.petInfo02Next);
        ifSterilised = (Spinner) findViewById(R.id.spinner1);
        ifMeds = (Spinner) findViewById(R.id.spinner2);
        medsHowOften = (Spinner) findViewById(R.id.spinner3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSterilised = ifSterilised.getSelectedItemPosition() == 0;
                boolean isMeds = ifMeds.getSelectedItemPosition() == 0;

                Integer position = medsHowOften.getSelectedItemPosition();
                String medsHowOften = getResources().getStringArray(R.array.medsTimes)[position];

                openActivity(isSterilised, isMeds, medsHowOften);
            }
        });
    }
    public void openActivity (boolean isSterilised, boolean isMeds, String medsHowOften)
    {
        Intent intent = new Intent (this, addingPicture.class);
        intent.putExtra("isSterilised",isSterilised);
        intent.putExtra("isMeds", isMeds);
        intent.putExtra("medsHowOften", medsHowOften);
        intent.putExtra("isMale", getIntent().getBooleanExtra("isMale", false));;
        intent.putExtra("isDog", getIntent().getBooleanExtra("isDog", false));
        intent.putExtra("name", getIntent().getStringExtra("name"));
        intent.putExtra("dateOfBirth",  getIntent().getStringExtra("dateOfBirth"));
        intent.putExtra("breed",  getIntent().getStringExtra("breed"));
        intent.putExtra("weight",  getIntent().getIntExtra("weight", 0));
        startActivity(intent);
    }
}

