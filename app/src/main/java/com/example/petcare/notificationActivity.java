package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.petcare.db.Notification;
import com.example.petcare.db.Pet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class notificationActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private EditText textInput;
    private Button selectDateButton, deleteBtn;
    private Button selectTimeButton;
    private Button addBt;
    private Pet pet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        deleteBtn = findViewById(R.id.deleteNotificationBt);
        firebaseFirestore = FirebaseFirestore.getInstance();
        textInput = (EditText) findViewById(R.id.addingTextNotification);
        selectDateButton = (Button) findViewById(R.id.selectDateBt);
        selectTimeButton = (Button) findViewById(R.id.selectTimeBt);
        addBt = (Button) findViewById(R.id.saveNotificationBt);
        String notificationID = getIntent().getStringExtra("notificationID");
        String petID = getIntent().getStringExtra("petID");
        if (petID != null){
            getPetName(petID);
        }
        if(notificationID == null){
            deleteBtn.setVisibility(View.INVISIBLE);
        }
        else{
            deleteBtn.setVisibility(View.VISIBLE);
        }
        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(notificationActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                selectTimeButton.setText(hourOfDay + ":" + minute);
                               /* if(hourOfDay >= 0 && hourOfDay <= 9)
                                    selectTimeButton.setText("0" + hourOfDay + ":" + minute);
                                if(minute >= 0 && minute <= 9)
                                    selectTimeButton.setText(hourOfDay + ":" +"0" + minute); */

                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        deleteBtn.setOnClickListener(v -> deleteNotification(notificationID));

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                new DatePickerDialog(notificationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy", new Locale("pl","PL"));
                        selectDateButton.setText(dateFormat.format(myCalendar.getTime()));
                    }
                }, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        if(notificationID == null)
        {
            addBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = textInput.getText().toString();
                    String date = selectDateButton.getText().toString();
                    String time = selectTimeButton.getText().toString();
                    if(text.length()>0 && !date.equals("select date") && !time.equals("select time"))
                    {
                        addNotification(textInput.getText().toString(), selectDateButton.getText().toString(), selectTimeButton.getText().toString(), petID);

                    }
                    else
                    {

                    }

                }
            });
        }
        else
        {
            firebaseFirestore.collection("notifications").document(notificationID).addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(value != null && value.exists() && error == null) {
                        Notification notification = value.toObject(Notification.class);
                        textInput.setText(notification.getText());
                        selectDateButton.setText(notification.getDate());
                        selectTimeButton.setText(notification.getTime());
                        addBt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String text = textInput.getText().toString();
                                String date = selectDateButton.getText().toString();
                                String time = selectTimeButton.getText().toString();
                                if (text.length() > 0 && !date.equals("select date") && !time.equals("select time")) {
                                    updateNotification(text, date, time, notification.getId(), notification.getPetID(), notification.getPetName());
                                } else {

                                }

                            }

                        });
                    }
                }
            });
        }
    }

    private void deleteNotification(String notificationID) {
        firebaseFirestore.collection("notifications").document(notificationID).delete();
        finish();
    }

    private void getNotificationDetails(String notificationID) {
        firebaseFirestore.collection("notifications").document(notificationID)
                .get().addOnCompleteListener(this, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Notification notification = task.getResult().toObject(Notification.class);
                fillUI(notification);
            }
        });
    }

    private void fillUI(Notification notification) {
        textInput.setText(notification.getText());
        selectDateButton.setText(notification.getDate());
    }

    private void getPetName(String petID) {
        firebaseFirestore.collection("pets").document(petID)
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        pet = value.toObject(Pet.class);
                    }
                });

    }
    private void updateNotification(String text, String date, String time, String notificationID, String petID, String petName) {
        firebaseFirestore.collection("notifications").document(notificationID)
                .set(new Notification(
                        notificationID,
                        petID,
                        text,
                        date,
                        time, FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        petName))
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
    }
    private void addNotification(String text, String date, String time, String petID) {
        DocumentReference doc = firebaseFirestore.collection("notifications").document();
        doc.set(new Notification(
                    doc.getId(),
                    petID,
                    text,
                    date,
                    time, FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    pet.getName()))
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                    }
                });
    }
}