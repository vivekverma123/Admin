package com.example.admin;
import com.example.admin.R;

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
import com.example.model.Month;
import com.example.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAdapter3 extends ArrayAdapter<Month>
{

    Context context;
    DataSnapshot snapshot;

    public UserAdapter3(@NonNull Context context, ArrayList<Month> month)
    {
        super(context, 0,month);
        this.context = context;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Month month = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.month_list_item, parent, false);
        }

        TextView t1 = convertView.findViewById(R.id.month_title);
        t1.setText(month.getTitle());

        if(position%2==0) {
            convertView.setBackgroundColor(Color.parseColor("#C0D6E4"));
        }

        return convertView;
    }
}