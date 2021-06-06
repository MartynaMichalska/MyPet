

package com.example.petcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.petcare.db.Notification;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


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
        Button callendar = (Button) findViewById(R.id.callendarBt);
        faqBt = (Button) findViewById(R.id.faqBt);
        callendar.setOnClickListener(v -> openActivityCallendar());
        accountMngBt = (Button) findViewById(R.id.accountManagementBt) ;
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
                .whereEqualTo("ownerID", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Notification> items = value.toObjects(Notification.class);
                        AlarmManager manager = (AlarmManager)(getSystemService( Context.ALARM_SERVICE ));

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",new Locale("pl","PL"));
                        //SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");






                            long time = 0;
                            for (Notification notification : items) {
                                try{
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(dateFormat.parse(notification.getDate()));
                                    String[] timeFormatParser = notification.getTime().split( ":" );
                                    int hour = Integer.parseInt ( timeFormatParser[0].trim() );
                                    int min = Integer.parseInt ( timeFormatParser[1].trim() );
                                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                                    calendar.set(Calendar.MINUTE, min);
                                    if(calendar.getTimeInMillis() >= System.currentTimeMillis()) {
                                        time += 10000;
                                        Intent serviceIntent = new Intent(getBaseContext(), NotificationService.class);
                                        serviceIntent.putExtra("petName", notification.getPetName());
                                        serviceIntent.putExtra("message", notification.getText());
                                        PendingIntent intent = PendingIntent.getBroadcast(getBaseContext(), new Random().nextInt(), serviceIntent, 0);
                                        manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+time, intent);

                                    }
                                }
                            catch(Throwable e){
                                    Log.e("userAccount", "error:"+e);
                            }

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
