package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.DueRecord;
import com.example.model.FlatOwner;
import com.example.model.Maintenance;
import com.example.model.Month;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    Context context;
    public static int tokenInt;
    public static String tokenString1,tokenString2;

    String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);



        init();
        generateMonth();

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

        Button btn4 = findViewById(R.id.initialdue);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox3(view);
            }
        });

        Button btn5 = findViewById(R.id.initialbal);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox4(view);
            }
        });

        Button btn6 = findViewById(R.id.app_main_req);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ApproveRequest.class));
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
                EditText e1 = alert_layout.findViewById(R.id.flatno1);
                EditText e2 = alert_layout.findViewById(R.id.name);
                EditText e3 = alert_layout.findViewById(R.id.due_amt);

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
        alert.setTitle("Remove Flat Owner");
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

    public void showDialogBox3(View view)
    {
        final View alert_layout = getLayoutInflater().inflate(R.layout.due_dialog,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Initialise Due");
        alert.setView(alert_layout);

        alert.setPositiveButton("Add Record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText e1 = alert_layout.findViewById(R.id.flatno1);
                EditText e2 = alert_layout.findViewById(R.id.due_amt);

                final String flatNo = e1.getText().toString();
                int amt1 = 0;
                amt1 = Integer.parseInt(e2.getText().toString());
                final int amt = amt1;
                if(flatNo.length()==0)
                {
                    Toast.makeText(MainActivity.this,"Flat Number can't be empty",Toast.LENGTH_SHORT).show();
                }
                else {

                    DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                    d1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("FlatOwners").child(flatNo).exists())
                            {
                                int amt1 = 0, amt2 = 0;
                                if(dataSnapshot.child("TotalDue").child(flatNo).exists())
                                {
                                    amt1 = dataSnapshot.child("TotalDue").child(flatNo).getValue(Integer.class);
                                }
                                if(dataSnapshot.child("InitialDue").child(flatNo).exists())
                                {
                                    amt2 = dataSnapshot.child("InitialDue").child(flatNo).getValue(Integer.class);
                                }

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference();
                                myRef.child("TotalDue").child(flatNo).setValue(amt1 - amt2 + amt);
                                myRef.child("InitialDue").child(flatNo).setValue(amt);
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Flat Number is not registered",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



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

    void showDialogBox4(View view)
    {
        final View alert_layout = getLayoutInflater().inflate(R.layout.bal_dialog,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Initialise Balance");
        alert.setView(alert_layout);

        alert.setPositiveButton("Initialise", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText e1 = alert_layout.findViewById(R.id.balamt);

                int x = Integer.parseInt(e1.getText().toString());

                DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                d1.child("InitialBalance").setValue(x);

                }
            }
        );

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void generateMonth()
    {
        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
        d1.addValueEventListener(new ValueEventListener() {


            String s1 = "";
            int month = -1;
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d1 : dataSnapshot.child("Months").getChildren()) {
                    Month m1 = d1.getValue(Month.class);
                    s1 = m1.getId();
                    month = m1.getM();
                }

                //Toast.makeText(MainActivity.this,s1,Toast.LENGTH_SHORT).show();

                GregorianCalendar Calendar = new GregorianCalendar();
                int curr_month = Calendar.getInstance().get(Calendar.MONTH) + 1;

                if(month==-1 || (curr_month<=12 && month + 1==curr_month) || (curr_month==1 && month==12))
                {
                    Month m1 = new Month();
                    m1.setBills();
                    m1.setM(curr_month);
                    m1.setY(Calendar.getInstance().get(Calendar.YEAR));
                    m1.setTitle(monthName[curr_month-1] + ", " + m1.getY());

                    DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                    String id = d1.child("Months").push().getKey();
                    m1.setId(id);

                    d1.child("Months").child(id).setValue(m1);
                    d1.child("CurrentMonth").setValue(id);

                    for(DataSnapshot d2 : dataSnapshot.child("FlatOwners").getChildren())
                    {
                        Toast.makeText(MainActivity.this,"Here",Toast.LENGTH_SHORT).show();
                        FlatOwner f1 = d2.getValue(FlatOwner.class);
                        Maintenance m2 = new Maintenance(f1.getFlatNo(),0);
                        d1.child("MaintenanceRecord").child(id).child(f1.getFlatNo()).setValue(m2);
                    }

                }

            }

            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}