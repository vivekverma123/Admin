package com.example.admin;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import com.example.model.FlatOwner;
import com.example.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAdapter2 extends ArrayAdapter<Request>
{

    Context context;
    DataSnapshot snapshot;

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
        TextView e2 = convertView.findViewById(R.id.rem_client);

        flat.setText(request.getFlatNo());
        amt.setText("₹" + request.getAmt());
        e2.setText(request.getRemarkClient());

        if(position%2==0) {
            convertView.setBackgroundColor(Color.parseColor("#ce93d8"));
        }
        else
        {
            convertView.setBackgroundColor(Color.WHITE);
        }



        // Return the completed view to render on screen
        return convertView;
    }
}