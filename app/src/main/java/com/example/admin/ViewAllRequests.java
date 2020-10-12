package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.model.Request;
import com.example.model.UserAdapter6;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllRequests extends AppCompatActivity {

    ListView l1;
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_requests);

        l1 = findViewById(R.id.all_req);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Request r1 = (Request)parent.getItemAtPosition(position);
                Intent i1 = new Intent(ViewAllRequests.this,RequestDetails.class);
                i1.putExtra("Type","" + value);
                i1.putExtra("Request",r1);
                startActivity(i1);
            }
        });

        TextView t1 = findViewById(R.id.textView15);
        TextView t2 = findViewById(R.id.textView18);
        TextView t3 = findViewById(R.id.textView19);
        final TextView title = findViewById(R.id.title);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Maintenance Payment Requests");
                value = 0;
                DatabaseReference d1 = FirebaseDatabase.getInstance().getReference("Requests");
                        d1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                ArrayList<Request> a1 = new ArrayList<>();
                                for(DataSnapshot d1 : snapshot.getChildren())
                                {
                                    Request r1 = d1.getValue(Request.class);
                                    a1.add(r1);
                                }
                                UserAdapter6 u1 = new UserAdapter6(ViewAllRequests.this,a1);
                                l1.setAdapter(u1);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Advance Payment Requests");
                value = 1;
                DatabaseReference d1 = FirebaseDatabase.getInstance().getReference("AdvancePayRequests");
                d1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<Request> a1 = new ArrayList<>();
                        for(DataSnapshot d1 : snapshot.getChildren())
                        {
                            Request r1 = d1.getValue(Request.class);
                            a1.add(r1);
                        }
                        UserAdapter6 u1 = new UserAdapter6(ViewAllRequests.this,a1);
                        l1.setAdapter(u1);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setText("Maintenance Payment Requests");
                value = 2;
                DatabaseReference d1 = FirebaseDatabase.getInstance().getReference("DueRequests");
                d1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<Request> a1 = new ArrayList<>();
                        for(DataSnapshot d1 : snapshot.getChildren())
                        {
                            Request r1 = d1.getValue(Request.class);
                            a1.add(r1);
                        }
                        UserAdapter6 u1 = new UserAdapter6(ViewAllRequests.this,a1);
                        l1.setAdapter(u1);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}