package com.example.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import com.example.model.FlatOwner;
import com.example.model.Request;

public class UserAdapter2 extends ArrayAdapter<Request>
{

    Context context;

    public UserAdapter2(@NonNull Context context, ArrayList<Request> request)
    {
        super(context, 0,request);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Request request = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.req_item, parent, false);
        }
        // Lookup view for data population
        TextView flat = convertView.findViewById(R.id.flat);
        TextView amt = convertView.findViewById(R.id.amt_2);
        Button b1 = convertView.findViewById(R.id.approve_btn);

        flat.setText(request.getFlatNo());
        amt.setText("" + request.getAmt());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, request.getFlatNo() + " " + request.getAmt(),Toast.LENGTH_SHORT).show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

}