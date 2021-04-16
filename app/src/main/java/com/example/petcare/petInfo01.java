package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class petInfo01 extends AppCompatActivity {

    private Button next;
    private TextView name;
    private TextView weight;
    private Spinner scroll;
    private TextView age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info01);

        next= (Button) findViewById(R.id.petInfoNext);
        name=(TextView) findViewById(R.id.petName);
        weight=(TextView) findViewById(R.id.petWeight);
        age=(TextView) findViewById(R.id.petAge);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( dataValidation(name.getText().toString(), weight.getText().toString(), age.getText().toString()))
               {
                   openActivity();
               }


            }
        });


    }
    public boolean dataValidation(String name, String weight, String age) {

        if (!name.isEmpty() && !weight.isEmpty() && !age.isEmpty()) {

                if (Integer.parseInt(weight) > 0 && Integer.parseInt(weight) < 40) {

                    if (Integer.parseInt(age) > 0 && Integer.parseInt(age) < 30) {
                        return true;

                    } else {
                        Toast.makeText(this, "Wrong age value", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "Wrong weight value", Toast.LENGTH_SHORT).show();
                    return false;
                }

        }
        else
            {
                Toast.makeText(this, "Please fill the blank spaces", Toast.LENGTH_SHORT).show();
                return false;
            }

    }

    public void openActivity ()
    {
        Intent intent= new Intent(this,petInfo02.class);
        startActivity(intent);
    }





}