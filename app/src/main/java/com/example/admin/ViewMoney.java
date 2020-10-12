package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.model.Amount;
import com.example.model.FlatOwner;
import com.example.model.UserAdapter7;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewMoney extends AppCompatActivity {

    ListView l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_money);

        TextView t1 = findViewById(R.id.textView15);
        TextView t2 = findViewById(R.id.textView18);
        final TextView t3 = findViewById(R.id.title);

        l1 = findViewById(R.id.money_list);

        t1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                t3.setText("Advance Amount Remaining till date");

                DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                d1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList <Amount> m1 = new ArrayList<>();
                        for(DataSnapshot d1 : snapshot.child("FlatOwners").getChildren())
                        {
                            FlatOwner f1 = d1.getValue(FlatOwner.class);
                            Amount amount = new Amount(f1.getFlatNo(),snapshot.child("AdvancedAmount").child(f1.getFlatNo()).getValue(Integer.class));
                            m1.add(amount);
                        }
                        UserAdapter7 u1 = new UserAdapter7(ViewMoney.this,m1);
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
                t3.setText("Amount Accrued till last month");

                DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                d1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList <Amount> m1 = new ArrayList<>();
                        for(DataSnapshot d1 : snapshot.child("FlatOwners").getChildren())
                        {
                            FlatOwner f1 = d1.getValue(FlatOwner.class);
                            Amount amount = new Amount(f1.getFlatNo(),snapshot.child("TotalDue").child(f1.getFlatNo()).getValue(Integer.class));
                            m1.add(amount);
                        }
                        UserAdapter7 u1 = new UserAdapter7(ViewMoney.this,m1);
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