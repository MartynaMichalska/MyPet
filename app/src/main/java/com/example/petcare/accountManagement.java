package com.example.petcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class accountManagement extends AppCompatActivity {
private Button passwordUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        passwordUpdate= (Button) findViewById(R.id.changePasswordBt);
        passwordUpdate.setOnClickListener(v -> openActivityChangePassword());
    }
    public void openActivityChangePassword ()
    {
        Intent intent = new Intent (this, passwordUpdate.class);
        startActivity(intent);
    }

}