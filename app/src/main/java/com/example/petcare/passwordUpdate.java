package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class passwordUpdate extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private TextView newPass;
    private TextView oldPass;
    private TextView newPassRpt;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_update);
        newPass=(TextView) findViewById(R.id.newPass) ;
        oldPass=(TextView) findViewById(R.id.currentPass) ;
        newPassRpt=(TextView) findViewById(R.id.newPassRepeat) ;
        save=(Button) findViewById(R.id.savePass) ;
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

if(newPass.getText().toString().equals(newPassRpt.getText().toString()))
{
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(),oldPass.getText().toString() );

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(String.valueOf(newPass.getText())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful() && dataValidation(newPass.getText().toString())) {
                                        Toast.makeText(getApplicationContext(),"Password updated", Toast.LENGTH_LONG).show();
                                        openActivityuserAcc();
                                    } else {
                                        Toast.makeText(getApplicationContext(),"New password must be at least 7 characters long", Toast.LENGTH_LONG).show();
                                        openActivityuserAcc();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(),"Auth failed", Toast.LENGTH_LONG).show();
                            openActivityuserAcc();
                        }
                    }
                });
    }
else
{
    Toast.makeText(getApplicationContext(),"Passwords not matching! Please try again", Toast.LENGTH_LONG).show();
    openActivityuserAcc();

}
    }
});

    }

    private boolean dataValidation(String pas) {
        return pas.length() >= 7;

    }

    public void openActivityuserAcc ()
    {
        Intent intent = new Intent (this, userAccount.class);
        startActivity(intent);
    }
}