package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Your_account2 extends AppCompatActivity {
private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_account2);
        Intent intent=getIntent();
        TextView email =(TextView) findViewById(R.id.editTextTextEmailAddress);
        TextView password =(TextView) findViewById(R.id.editTextTextPassword);
        button2 = (Button) findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });
    }

    public void openActivity2 ()
    {
        Intent intent = new Intent (this, Add_Activity.class);
        startActivity(intent);
    }


}