package com.example.petcare;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.type.DateTime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class petInfo01 extends AppCompatActivity {

    private Button next;
    private TextView name;
    private TextView weight;
    private Spinner petStrain;
    private Spinner dateDay;
    private Spinner dateMonth;
    private Spinner dateYear;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info01);

        next= (Button) findViewById(R.id.petInfoNext);
        name=(TextView) findViewById(R.id.petName);
        weight=(TextView) findViewById(R.id.petWeight);
        petStrain = (Spinner) findViewById(R.id.petStrain);
       dateDay = (Spinner) findViewById(R.id.dateDay);
        dateMonth = (Spinner) findViewById(R.id.dateMnth);
        dateYear = (Spinner) findViewById(R.id.dateYer);
        Integer position1 = dateDay.getSelectedItemPosition();
        Integer position2 = dateMonth.getSelectedItemPosition();
        Integer position3 = dateYear.getSelectedItemPosition();
        String dateDay1 = getResources().getStringArray(R.array.dayz)[position1];
        String dateMont1 = getResources().getStringArray(R.array.mothz)[position2];
        String dateYear1 = getResources().getStringArray(R.array.yrs)[position3];
      //  Date finalDate;
        //finalDate = new Date (Integer.parseInt(dateDay1),Integer.parseInt(dateMont1),Integer.parseInt(dateYear1));
String date= dateDay1+"-"+dateMont1+"-"+dateYear1;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = petStrain.getSelectedItemPosition();
               if(position > -1 && dataValidation(name.getText().toString(), weight.getText().toString(), dateDay1, dateMont1, dateYear1))
               {
                   String petStrain = getResources().getStringArray(R.array.Strain)[position];
                   openActivity(name.getText().toString(), Integer.parseInt(weight.getText().toString()), date,  petStrain);
               }


            }
        });


    }
    public boolean dataValidation(String name, String weight, String day, String m, String y) {

        if (!name.isEmpty() && !weight.isEmpty() ) {

                if (Integer.parseInt(weight) > 0 && Integer.parseInt(weight) < 40) {

                        return true;

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

    public void openActivity (String name, int weight, String dateOfBirth, String breed)
    {
        Intent intent= new Intent(this,petInfo02.class);
        intent.putExtra("isMale", getIntent().getBooleanExtra("isMale", false));;
        intent.putExtra("isDog", getIntent().getBooleanExtra("isDog", false));
        intent.putExtra("name", name);
        intent.putExtra("dateOfBirth", dateOfBirth);
        intent.putExtra("breed", breed);
        intent.putExtra("weight", weight);
        startActivity(intent);
    }





}