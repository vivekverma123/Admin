package com.example.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserAdapter4 extends ArrayAdapter<Maintenance>
{

    Context context;
    DataSnapshot snapshot;

    public UserAdapter4(@NonNull Context context, ArrayList<Maintenance> maintenance)
    {
        super(context, 0,maintenance);
        this.context = context;
        refresh();

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        try{
            final Maintenance m1 = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.main_list_item, parent, false);
            }

            TextView t1 = convertView.findViewById(R.id.textView16);
            TextView t2 = convertView.findViewById(R.id.textView17);
            TextView t3 = convertView.findViewById(R.id.textView18);
            TextView t4 = convertView.findViewById(R.id.textView19);

            t1.setText(m1.getFlatNumber());
            t2.setText("₹" + m1.getAmt_paid());
            t3.setText("₹" + (m1.getContr() - m1.getAmt_paid()));

            if(m1.getStatus()==0)
            {
                t4.setText("Not Paid");
            }
            else if(m1.getStatus()==1)
            {
                t4.setText("Partially Paid");
            }
            else
            {
                t4.setText("Paid");
            }

            if(position%2==0) {
                convertView.setBackgroundColor(Color.parseColor("#a5d6a7"));
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

