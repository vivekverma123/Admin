package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.model.Maintenance;
import com.example.model.UserAdapter4;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MaintenanceStatus extends AppCompatActivity {

        ListView l1;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maintenance_status);



            l1 = findViewById(R.id.list_main);



            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<Maintenance> f1 = new ArrayList<>();
                    for (DataSnapshot d1 : dataSnapshot.child("MaintenanceRecord").getChildren())
                    {
                        for(DataSnapshot d2 : d1.getChildren()) {
                            Maintenance maintenance = d2.getValue(Maintenance.class);
                            f1.add(maintenance);
                        }
                    }
                    UserAdapter4 u1 = new UserAdapter4(MaintenanceStatus.this, f1);
                    l1.setAdapter(u1);

                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }



            });
        }


    }
