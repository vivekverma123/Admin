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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.FlatOwner;
import com.example.model.Helper;
import com.example.model.Maintenance;
import com.example.model.Month;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    Context context;
    public static int tokenInt;
    public static String tokenString1,tokenString2;
    public int amount;

    String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    protected void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);

            this.requestWindowFeature(Window.FEATURE_NO_TITLE);

            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            setContentView(R.layout.activity_main);

            init();

            //refresh();

            TextView t1 = findViewById(R.id.textView9);
            TextView t2 = findViewById(R.id.textView10);

            t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogBox5(v);
                }
            });

            t2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogBox6(v);
                }
            });
        }
        catch(Exception e1)
        {
            Toast.makeText(MainActivity.this,e1.toString(),Toast.LENGTH_SHORT).show();
        }



    }

    public void init()
    {
        context = MainActivity.this;
        //Button btn1 = findViewById(R.id.addFlatOwner);
        FrameLayout btn1 = findViewById(R.id.frame1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    showDialogBox1(view);

            }
        });

        //Button btn2 = findViewById(R.id.removeFlatOwner);
        FrameLayout btn2 = findViewById(R.id.frame2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox2(view);
            }
        });

        //Button btn3 = findViewById(R.id.viewall);
        FrameLayout btn3 = findViewById(R.id.frame3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewAllActivity.class));
            }
        });

        //Button btn5 = findViewById(R.id.initialbal);
        FrameLayout btn5 = findViewById(R.id.frame5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox4(view);
            }
        });

        //Button btn6 = findViewById(R.id.app_main_req);
        FrameLayout btn6 = findViewById(R.id.frame6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ApproveRequest.class));
            }
        });

        //Button btn7 = findViewById(R.id.view_months);
        FrameLayout btn7 = findViewById(R.id.frame7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewMonths.class));
            }
        });

        //Button btn8 = findViewById(R.id.app_advanc_req);
        FrameLayout btn8 = findViewById(R.id.frame8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ApproveAdvanceRequest.class));
            }
        });

        //Button btn9 = findViewById(R.id.appr_due_req);
        FrameLayout btn9 = findViewById(R.id.frame9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ApproveDueRequest.class));
            }
        });

        FrameLayout btn4 = findViewById(R.id.frame4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ViewTransactionsActivity.class));
            }
        });
    }

    public void showDialogBox1(View view)
    {
        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();

        d1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot1)
            {
                final View alert_layout = getLayoutInflater().inflate(R.layout.insert_dialog,null);

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Enter the record");
                alert.setView(alert_layout);

                alert.setPositiveButton("Add Flat Owner", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                        EditText e1 = alert_layout.findViewById(R.id.flatno1);
                        EditText e2 = alert_layout.findViewById(R.id.name);
                        EditText e3 = alert_layout.findViewById(R.id.mobno);

                        EditText e4 = alert_layout.findViewById(R.id.due10);
                        EditText e5 = alert_layout.findViewById(R.id.advance10);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        String flatNo = e1.getText().toString();
                        String Name = e2.getText().toString();
                        String mobNo = e3.getText().toString();
                        String due = e4.getText().toString();
                        String advance = e5.getText().toString();

                        if(flatNo.length()==0 || Name.length()==0 || mobNo.length()==0 || due.length()==0 || advance.length()==0)
                        {
                            Toast.makeText(MainActivity.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            FlatOwner flatOwner = new FlatOwner(flatNo, Name, mobNo);
                            myRef.child("FlatOwners").child(flatNo).setValue(flatOwner);
                            myRef.child("TotalDue").child(flatNo).setValue(Integer.parseInt(due));
                            myRef.child("AdvancedAmount").child(flatNo).setValue(Integer.parseInt(advance));

                            String s1 = dataSnapshot1.child("CurrentMonth").getValue(String.class);

                            Month m1 = dataSnapshot1.child("Months").child(s1).getValue(Month.class);
                            int amt2 = m1.getContr();

                            Maintenance m2 = new Maintenance(flatNo,0,0,m1.getTitle(),amt2);

                            myRef.child("MaintenanceRecord").child(s1).child(flatNo).setValue(m2);

                            refresh();

                        }

                    }
                catch(Exception e1)
                    {
                        Toast.makeText(MainActivity.this,e1.toString(),Toast.LENGTH_SHORT).show();
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
                refresh();

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

    void showDialogBox5(View view)
    {
        final View alert_layout = getLayoutInflater().inflate(R.layout.bal_dialog,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Enter Name");
        alert.setView(alert_layout);
        EditText e1 = alert_layout.findViewById(R.id.balamt);
        e1.setHint("society name");

        alert.setPositiveButton("Set/Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText e1 = alert_layout.findViewById(R.id.balamt);

                        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                        d1.child("Name").setValue(e1.getText().toString());

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

    void showDialogBox6(View view)
    {
        final View alert_layout = getLayoutInflater().inflate(R.layout.bal_dialog,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Enter Address");
        alert.setView(alert_layout);
        EditText e1 = alert_layout.findViewById(R.id.balamt);
        e1.setHint("society address");

        alert.setPositiveButton("Set/Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText e1 = alert_layout.findViewById(R.id.balamt);

                        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                        d1.child("Address").setValue(e1.getText().toString());

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
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot)
            {
                for (DataSnapshot d1 : dataSnapshot.child("Months").getChildren())
                {
                    Month m1 = d1.getValue(Month.class);
                    s1 = m1.getId();
                    month = m1.getM();
                }

                //Toast.makeText(MainActivity.this,s1,Toast.LENGTH_SHORT).show();

                GregorianCalendar Calendar = new GregorianCalendar();
                int curr_month = Calendar.getInstance().get(Calendar.MONTH) + 1;

                if (month == -1 || (curr_month <= 12 && month + 1 == curr_month) || (curr_month == 1 && month == 12)) {
                    final Month m1 = new Month();
                    m1.setBills();
                    m1.setM(curr_month);
                    m1.setY(Calendar.getInstance().get(Calendar.YEAR));
                    m1.setTitle(monthName[curr_month - 1] + ", " + m1.getY());

                    if(dataSnapshot!=null) {

                        final View alert_layout = getLayoutInflater().inflate(R.layout.bal_dialog, null);

                        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Enter maintenance amount for this month (this action cannot be undone, enter carefully)");
                        alert.setView(alert_layout);


                        alert.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        EditText e1 = alert_layout.findViewById(R.id.balamt);

                                        int x = Integer.parseInt(e1.getText().toString());

                                        amount = x;

                                        m1.setContr(x);

                                        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();

                                        String id_prev = "-1";

                                        if(dataSnapshot.child("CurrentMonth").exists())
                                        {
                                            id_prev = dataSnapshot.child("CurrentMonth").getValue(String.class);
                                        }


                                        String id = d1.child("Months").push().getKey();
                                        m1.setId(id);

                                        d1.child("Months").child(id).setValue(m1);
                                        d1.child("CurrentMonth").setValue(id);

                                        if(dataSnapshot.child("FlatOwners").exists()) {

                                            for (DataSnapshot d2 : dataSnapshot.child("FlatOwners").getChildren()) {
                                                Toast.makeText(MainActivity.this, "Here", Toast.LENGTH_SHORT).show();
                                                FlatOwner f1 = d2.getValue(FlatOwner.class);

                                                if (id_prev.equals("-1") == false) {
                                                    Maintenance m1 = dataSnapshot.child("MaintenanceRecord").child(id_prev).child(f1.getFlatNo()).getValue(Maintenance.class);
                                                    int amt1 = dataSnapshot.child("TotalDue").child(f1.getFlatNo()).getValue(Integer.class);
                                                    amt1 += (m1.getContr() - m1.getAmt_paid());
                                                    d1.child("TotalDue").child(f1.getFlatNo()).setValue(amt1);
                                                }

                                                Maintenance m2 = new Maintenance(f1.getFlatNo(), 0, 0, m1.getTitle(), m1.getContr());
                                                int amt1 = dataSnapshot.child("AdvancedAmount").child(f1.getFlatNo()).getValue(Integer.class);

                                                if (amt1 > 0) {
                                                    if (amt1 >= m1.getContr()) {
                                                        amt1 -= m1.getContr();
                                                        m2.setAmt_paid(m1.getContr());
                                                        m2.setStatus(2);
                                                    } else {
                                                        m2.setAmt_paid(amt1);
                                                        amt1 = 0;
                                                        m2.setStatus(1);
                                                    }
                                                }

                                                d1.child("AdvancedAmount").child(f1.getFlatNo()).setValue(amt1);
                                                d1.child("MaintenanceRecord").child(id).child(f1.getFlatNo()).setValue(m2);
                                            }
                                        }
                                    }
                                }
                        );

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                                amount = 0;
                            }
                        });

                        AlertDialog dialog = alert.create();
                        dialog.show();
                    }

                }
                if(dataSnapshot.child("CurrentMonth").exists()) {
                    refresh();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void refresh()
    {
        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
        d1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    TextView t1 = findViewById(R.id.textView9);
                    TextView t2 = findViewById(R.id.textView10);
                    TextView t3 = findViewById((R.id.textView11));
                    TextView t4 = findViewById((R.id.textView12));
                    TextView t5 = findViewById((R.id.textView13));
                    TextView t6 = findViewById((R.id.textView14));

                    String date = Helper.getLastDate();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, YYYY");
                    Date date1 = new Date();
                    String curr_date = simpleDateFormat.format(date1);

                    if (dataSnapshot.child("Name").exists() == false) {
                        t1.setText("Name not set");
                    } else {
                        t1.setText(dataSnapshot.child("Name").getValue(String.class));
                    }

                    if (dataSnapshot.child("Address").exists() == false) {
                        t2.setText("Address not set");
                    } else {
                        t2.setText(dataSnapshot.child("Address").getValue(String.class));
                    }

                    int amt1 = 0;
                    int amt2 = 0;

                    if(dataSnapshot.child("InitialBalance").exists()==true) {
                        amt2 = dataSnapshot.child("InitialBalance").getValue(Integer.class);
                    }

                    for (DataSnapshot d1 : dataSnapshot.child("Months").getChildren()) {

                        Month m1 = d1.getValue(Month.class);
                        amt2 += (m1.getAmtOb() - m1.getTotExp());
                    }

                    String id = dataSnapshot.child("CurrentMonth").getValue(String.class);
                    Month m1 = dataSnapshot.child("Months").child(id).getValue(Month.class);

                    for (DataSnapshot d1 : dataSnapshot.child("FlatOwners").getChildren()) {
                        FlatOwner f1 = d1.getValue(FlatOwner.class);
                        amt1 += dataSnapshot.child("TotalDue").child(f1.getFlatNo()).getValue(Integer.class);
                        Maintenance m2 = dataSnapshot.child("MaintenanceRecord").child(id).child(f1.getFlatNo()).getValue(Maintenance.class);
                        amt1 += m1.getContr() - m2.getAmt_paid();
                    }

                    t3.setText("Balance as on " + curr_date + ": ");
                    t5.setText("Amount accrued till " + curr_date + ": ");

                    t4.setText("₹" + amt2);
                    t6.setText("₹" + amt1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        generateMonth();
    }
}