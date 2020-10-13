package com.example.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admin.R;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAdapter5 extends ArrayAdapter<Transaction>
{

    Context context;
    DataSnapshot snapshot;

    public UserAdapter5(@NonNull Context context, ArrayList<Transaction> transaction)
    {
        super(context, 0,transaction);
        this.context = context;
        refresh();

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        try{
            final Transaction transaction = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.transaction_item, parent, false);
            }

            TextView t1 = convertView.findViewById(R.id.textView10);
            TextView t2 = convertView.findViewById(R.id.textView11);
            TextView t3 = convertView.findViewById(R.id.textView12);

            t1.setText(transaction.getDate());
            String particular;
            t3.setText(transaction.getAmount() + "");

            if(transaction.getType()==0)
            {
                particular = "For Maintenance";
            }
            else if(transaction.getType()==1)
            {
                particular = "For Advance";
            }
            else
            {
                particular = "Cleared Previous Due";
            }

            t2.setText(transaction.getFlatNo() + ": " + particular);

            if(position%2==0) {
                convertView.setBackgroundColor(Color.parseColor("#ffe082"));
            }
            else
            {
                convertView.setBackgroundColor(Color.WHITE);
            }
            return convertView;

        }
        catch(Exception e1)
        {
            Toast.makeText(context,e1.toString(),Toast.LENGTH_SHORT).show();
        }



        return convertView;
    }

    public void refresh()
    {
        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
        d1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setSnapshot(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setSnapshot(DataSnapshot snapshot) {
        this.snapshot = snapshot;
    }
}

