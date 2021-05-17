package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class accountManagement extends AppCompatActivity {
private Button passwordUpdate;
private Button deleteAcc;
private TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       innitViews();
        email.setText("Hello, "+user.getEmail()+"!");
        passwordUpdate.setOnClickListener(v -> openActivityChangePassword());
        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog= new AlertDialog.Builder(accountManagement.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting your account permanently will result in all the data loss without a possibility of getting it back.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(),"Account deleted", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(accountManagement.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(accountManagement.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dissmiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                AlertDialog alertDialog =dialog.create();
                alertDialog.show();
            }
        });
    }

    public void openActivityChangePassword ()
    {
        Intent intent = new Intent (this, passwordUpdate.class);
        startActivity(intent);
    }


    public void innitViews()
    {
        email = (TextView) findViewById(R.id.yourEmail);
        passwordUpdate = (Button) findViewById(R.id.changePasswordBt);
        deleteAcc =(Button) findViewById(R.id.deleteAccBt);
    }
}