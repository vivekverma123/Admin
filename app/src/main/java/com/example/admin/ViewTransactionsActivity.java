package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.model.Transaction;
import com.example.model.UserAdapter5;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewTransactionsActivity extends AppCompatActivity {

    ListView l1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transactions);

        l1 = findViewById(R.id.listview3);
        TextView t1 = findViewById(R.id.textView4);
        t1.setText("Transaction Details");

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ownerRef = myRef.child("Transactions");

        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Transaction> f1 = new ArrayList<>();
                for (DataSnapshot d1 : dataSnapshot.getChildren())
                {
                    Transaction transaction = d1.getValue(Transaction.class);
                    f1.add(transaction);
                }
                UserAdapter5 u1 = new UserAdapter5(ViewTransactionsActivity.this, f1);
                l1.setAdapter(u1);
            }


            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}