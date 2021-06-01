package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class emailUpdate extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
private Button submit ;
private EditText oldE;
private EditText newE;
private EditText newE2;
private EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_update);
        innitViews();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newE.getText().toString().equals(newE2.getText().toString())) {
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(oldE.getText().toString(), pass.getText().toString());
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updateEmail(String.valueOf(newE.getText())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful() && newE.getText().toString().contains("@"))
                                                {
                                                    Toast.makeText(getApplicationContext(),"Email updated", Toast.LENGTH_LONG).show();
                                                    openActivityuserAcc();
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(),"Wrong new E-mail format!", Toast.LENGTH_LONG).show();
                                                    openActivityuserAcc();
                                                }

                                            }
                                        });
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Auth failed", Toast.LENGTH_LONG).show();
                                        openActivityuserAcc();
                                    }
                                }
                            });


                }
                else {
                    Toast.makeText(getApplicationContext(),"Emails not matching! Please try again", Toast.LENGTH_LONG).show();
                    openActivityuserAcc();
                }



            }
        });

    }

    private void innitViews() {
        submit= (Button) findViewById(R.id.emailSubmit);
        oldE = (EditText) findViewById(R.id.currentEmail);
        newE = (EditText) findViewById(R.id.newEmail);
        newE2 = (EditText) findViewById(R.id.newEmailRepeat);
        pass = (EditText) findViewById(R.id.emailVerPass);
    }
    public void openActivityuserAcc ()
    {
        Intent intent = new Intent (this, userAccount.class);
        startActivity(intent);
    }
}