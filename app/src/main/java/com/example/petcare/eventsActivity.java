package com.example.petcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcare.db.Notification;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class eventsActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private MaterialCalendarView petsCalendar;
    private List<Notification> notifications;
    private Button backBT;
    private TextView petsLegend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        petsLegend = findViewById(R.id.pets_legend);
        firebaseFirestore = FirebaseFirestore.getInstance();
        petsCalendar = findViewById(R.id.pets_calendar_view);
        backBT= (Button) findViewById(R.id.backBT) ;
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
        petsLegend.setMovementMethod(new ScrollingMovementMethod());

        Query query = firebaseFirestore.collection("notifications")
                .whereEqualTo("ownerID", FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                notifications = value.toObjects(Notification.class);
                petsCalendar.addDecorator(new petDecorator(notifications, getResources()));
            }
        });
        petsCalendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                checkEventsForDate(date);
            }
        });

    }

    private void checkEventsForDate(CalendarDay day) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            StringBuilder stringBuilder = new StringBuilder();
            for (Notification notification : notifications) {
                Date date = dateFormat.parse(notification.getDate());
                calendar.setTime(date);
                if (calendar.get(Calendar.YEAR) == day.getYear() && calendar.get(Calendar.MONTH) + 1 == day.getMonth() && calendar.get(Calendar.DAY_OF_MONTH) == day.getDay()) {
                    stringBuilder.append(notification.getPetName()+" - ");
                    stringBuilder.append(notification.getText()+"\n\n");

                }

            }
            if(stringBuilder.length() > 0){
                petsLegend.setText(stringBuilder.toString());
            }

        } catch (Throwable t) {

        }
    }



    public void openMainActivity()
    {
        Intent intent = new Intent (this, userAccount.class);
        startActivity(intent);
    }


}