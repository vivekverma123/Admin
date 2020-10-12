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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserAdapter6 extends ArrayAdapter<Request>
{

    Context context;
    DataSnapshot snapshot;

    public UserAdapter6(@NonNull Context context, ArrayList<Request> request)
    {
        super(context, 0,request);
        this.context = context;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Request request = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.all_req_item, parent, false);
        }
        // Lookup view for data population
        TextView t1 = convertView.findViewById(R.id.textView27);
        TextView t2 = convertView.findViewById(R.id.textView29);
        TextView t3 = convertView.findViewById(R.id.textView30);
        TextView t4 = convertView.findViewById(R.id.textView31);

        t1.setText(request.getDate());
        t2.setText(request.getFlatNo());
        t3.setText("₹" + request.getAmt());

        if(request.getStatus()==false)
        {
            t4.setText("Pending");
        }
        else
        {
            t4.setText("Closed");
        }


        if(position%2==0) {
            convertView.setBackgroundColor(Color.parseColor("#C0D6E4"));
        }
        else
        {
            convertView.setBackgroundColor(Color.WHITE);
        }



        // Return the completed view to render on screen
        return convertView;
    }
}