package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.model.FlatOwner;
import com.example.model.Month;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewMonths extends AppCompatActivity {

    ListView l1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_months);

        l1 = findViewById(R.id.list_month);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Month m1 = (Month)parent.getItemAtPosition(position);
                Intent i1 = new Intent(ViewMonths.this,EditMonth.class);
                i1.putExtra("Month",m1);
                startActivity(i1);

            }
        });

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ownerRef = myRef.child("Months");

        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Month> f1 = new ArrayList<>();
                for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                    Month month = d1.getValue(Month.class);
                    f1.add(month);
                }
                com.example.admin.UserAdapter3 u1 = new com.example.admin.UserAdapter3(ViewMonths.this,f1);
                l1.setAdapter(u1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}