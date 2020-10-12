package com.example.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.admin.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class UserAdapter7 extends ArrayAdapter<Amount>
{

    Context context;
    DataSnapshot snapshot;

    public UserAdapter7(@NonNull Context context, ArrayList<Amount> amount)
    {
        super(context, 0,amount);
        this.context = context;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Amount amount = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.money_item, parent, false);
        }
        // Lookup view for data population
        TextView t1 = convertView.findViewById(R.id.textView27);
        TextView t2 = convertView.findViewById(R.id.textView29);

        t1.setText(amount.getFlatNo());
        t2.setText("₹" + amount.getAmt());

        if(position%2==0) {
            convertView.setBackgroundColor(Color.parseColor("#C0D6E4"));
        }
        else {
            convertView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }
}