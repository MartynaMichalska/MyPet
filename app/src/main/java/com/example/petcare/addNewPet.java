package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class addNewPet extends AppCompatActivity {

    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add = (Button) findViewById(R.id.addPet);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityChooseType();
            }
        });
    }

    public void openActivityChooseType ()
    {
        Intent intent = new Intent (this, chooseType.class);
        startActivity(intent);
    }


}