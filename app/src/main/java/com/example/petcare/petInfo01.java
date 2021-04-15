package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
               if( dataValidation(name.getText().toString(), Integer.parseInt(weight.getText().toString()), Integer.parseInt(age.getText().toString())))
               {
                   openActivity();
               }


            }
        });


    }
    private boolean dataValidation(String name, int weight, int age)
    {
        if(!name.isEmpty() && !Integer.toString(weight).isEmpty() && !Integer.toString(age).isEmpty() )
        {
            if (weight > 0 && weight < 40) {

                if (age > 0 && age < 30) {
                    return true;

                }
                else
                {
                    Toast.makeText(this, "Wrong age value", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else
            {
                Toast.makeText(this, "Wrong weight value", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else
        {
            Toast.makeText(this, "Please fill the blank spaces", Toast.LENGTH_SHORT).show();
            return false;
        }
/*
        if(!name.isEmpty()) {
            if (!String.valueOf(weight).isEmpty()) {
                if (weight > 0 && weight < 40) {
                    if(!String.valueOf(age).isEmpty()) {
                        if (age > 0 && age < 30) {
                            return true;

                        } else {
                            Toast.makeText(this, "Wrong age value", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Please fill the blank spaces", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(this, "Wrong weight value", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(this, "Please fill the blank spaces", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else
        {
            Toast.makeText(this, "Please fill the blank spaces", Toast.LENGTH_SHORT).show();
            return false;
        }
*/
    }

    public void openActivity ()
    {
        Intent intent= new Intent(this,petInfo02.class);
        startActivity(intent);
    }


}