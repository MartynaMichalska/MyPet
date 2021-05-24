

package com.example.petcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.petcare.db.Notification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class userAccount extends AppCompatActivity {
    private Button accountMngBt;
    private Button faqBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        syncNotifications();
        setContentView(R.layout.activity_users_account);
        Intent intent = getIntent();
        Button showPets = (Button) findViewById(R.id.showPetsButton);
        showPets.setOnClickListener(v -> openActivityMyPets());
        Button signOut = findViewById(R.id.logoutButton);
        Button callendar =(Button) findViewById(R.id.callendarBt);
        faqBt=(Button) findViewById(R.id.faqBt);
        callendar.setOnClickListener(v -> openActivityCallendar());
        accountMngBt=(Button) findViewById(R.id.accountManagementBt) ;
        accountMngBt.setOnClickListener(v -> openActivityAccountMng());
        faqBt.setOnClickListener(v -> openActivityFaq());

        signOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            finish();
        });
    }

    private void openActivityFaq() {
        Intent intent = new Intent(this, faqChoice.class);
        startActivity(intent);
    }

    private void syncNotifications() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("notifications")
                .whereEqualTo("ownerId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Notification> items = value.toObjects(Notification.class);
                        AlarmManager manager = (AlarmManager)(getSystemService( Context.ALARM_SERVICE ));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",new Locale("pl","PL"));
                        try {
                            for (Notification notification : items) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dateFormat.parse(notification.getDate()));
                                calendar.set(Calendar.HOUR_OF_DAY, 16);
                                calendar.set(Calendar.MINUTE, 5);
                                Intent serviceIntent = new Intent(getBaseContext(), NotificationService.class);
                                PendingIntent intent = PendingIntent.getBroadcast(getBaseContext(), 101, serviceIntent, 0);
                                manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intent);
                            }
                        }
                        catch(Throwable e){
                        }


                    }
                });
    }

    public void openActivityMyPets ()
    {
        Intent intent = new Intent (this, myPets.class);
        startActivity(intent);
    }
    public void openActivityAccountMng ()
    {
        Intent intent = new Intent (this, accountManagement.class);
        startActivity(intent);
    }
    public void openActivityCallendar ()
    {
        Intent intent = new Intent (this, eventsActivity.class);
        startActivity(intent);
    }




}
