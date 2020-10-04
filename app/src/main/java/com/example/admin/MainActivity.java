package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.FlatOwner;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Context context;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


    }

    public void init()
    {
        context = MainActivity.this;
        Button btn1 = findViewById(R.id.addFlatOwner);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox1(view);
            }
        });

        Button btn2 = findViewById(R.id.removeFlatOwner);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox2(view);
            }
        });

        Button btn3 = findViewById(R.id.viewall);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewAllActivity.class));
            }
        });
    }

    public void showDialogBox1(View view)
    {
        final View alert_layout = getLayoutInflater().inflate(R.layout.insert_dialog,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Enter the record");
        alert.setView(alert_layout);

        alert.setPositiveButton("Add Flat Owner", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText e1 = alert_layout.findViewById(R.id.flatno);
                EditText e2 = alert_layout.findViewById(R.id.name);
                EditText e3 = alert_layout.findViewById(R.id.mobno);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                String flatNo = e1.getText().toString();
                String Name = e2.getText().toString();
                String mobNo = e3.getText().toString();

                if(flatNo.length()==0 || Name.length()==0 || mobNo.length()==0)
                {
                    Toast.makeText(MainActivity.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    FlatOwner flatOwner = new FlatOwner(flatNo, Name, mobNo);
                    myRef.child("FlatOwners").child(e1.getText().toString()).setValue(flatOwner);
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void showDialogBox2(View view)
    {
        final View alert_layout = getLayoutInflater().inflate(R.layout.remove_dialog,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Enter the Flat Number to be removed");
        alert.setView(alert_layout);

        alert.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText e1 = alert_layout.findViewById(R.id.remove_flat);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                String flatNo = e1.getText().toString();
                if(flatNo.length()==0)
                {
                    Toast.makeText(MainActivity.this,"Flat Number can't be empty",Toast.LENGTH_SHORT).show();
                }
                else {
                    myRef.child("FlatOwners").child(e1.getText().toString()).removeValue();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }
}