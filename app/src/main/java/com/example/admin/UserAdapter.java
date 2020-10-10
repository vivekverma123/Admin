package com.example.admin;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import com.example.model.FlatOwner;

public class UserAdapter extends ArrayAdapter<FlatOwner>
{

    public UserAdapter(@NonNull Context context, ArrayList<FlatOwner> flatOwner)
    {
        super(context, 0,flatOwner);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FlatOwner flatOwner = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView flat = convertView.findViewById(R.id.textView);
        TextView name = convertView.findViewById(R.id.textView3);
        TextView mobNo = convertView.findViewById(R.id.textView5);

        flat.setText(flatOwner.getFlatNo());
        name.setText(flatOwner.getName());
        mobNo.setText(flatOwner.getMobNo());

        if(position%2==0) {
            convertView.setBackgroundColor(Color.parseColor("#C0D6E4"));
        }

        // Return the completed view to render on screen
        return convertView;
    }

}