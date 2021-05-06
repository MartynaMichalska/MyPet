package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.petcare.db.Notification;
import com.example.petcare.db.Pet;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class callendarActivity extends AppCompatActivity {
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    private Button callendarBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callendar);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.recyclerView);
        callendarBt= (Button) findViewById(R.id.callendarAddBt);
        final String petID = getIntent().getStringExtra("petID");

        Query query = firebaseFirestore.collection("notifications").whereEqualTo("petID", petID);
        query.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               Log.e("query", "data:"+value.toObjects(Notification.class).size() + "Error:"+error);
            }
        });
        FirestoreRecyclerOptions<Notification> options = new FirestoreRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification.class)
                .build();
        callendarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNotificationActivity(petID);
            }
        });
        adapter = new FirestoreRecyclerAdapter<Notification, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Notification notification) {
                holder.text.setText(notification.getText());
                holder.dateText.setText(notification.getDate());
                holder.itemView.setOnClickListener(v -> navigateToEditNotification(notification.getId()));
                holder.itemView.setBackgroundColor(position%2==0 ? Color.GRAY: Color.GREEN);

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.callendar_item_notification, parent, false);
                return new ViewHolder(view);

            }
        };
      //  mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

    }

    private void navigateToEditNotification(String notificationID) {
        Intent intent = new Intent(this, notificationActivity.class);
        intent.putExtra("notificationID", notificationID );
        startActivity(intent);
    }

    private void openAddNotificationActivity(String petID) {
        Intent intent = new Intent(this, notificationActivity.class);
        intent.putExtra("petID", petID );
        startActivity(intent);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView text;
        private final TextView dateText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.callendarItemText);
            dateText = itemView.findViewById(R.id.callendarItemDate);
        }
    }
}