package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.model.FlatOwner;
import com.example.model.Maintenance;
import com.example.model.Month;
import com.example.model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApproveRequest extends AppCompatActivity {

    ListView l1;
    DataSnapshot snapshot;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_approve_request);

        refresh();

        context = ApproveRequest.this;

        try {


            l1 = findViewById(R.id.list_req2);
            l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Request request = (Request) parent.getItemAtPosition(position);
                    showDialogBox1(request);
                }
            });


            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference ownerRef = myRef.child("Requests");

            ownerRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<Request> f1 = new ArrayList<>();
                    for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                        Request request = d1.getValue(Request.class);
                        if (request.getStatus() == false) {
                            f1.add(request);
                        }
                    }
                    UserAdapter2 u1 = new UserAdapter2(ApproveRequest.this, f1);
                    l1.setAdapter(u1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch(Exception e1)
        {
            Toast.makeText(ApproveRequest.this,e1.toString(),Toast.LENGTH_SHORT);
        }

    }

    public void refresh()
    {
        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
        d1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                setSnapshot(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setSnapshot(DataSnapshot snapshot)
    {
        this.snapshot = snapshot;
    }

    void showDialogBox1(Request request)
    {
        final View alert_layout = getLayoutInflater().inflate(R.layout.dialog_approve,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Approve/Reject Request");
        alert.setView(alert_layout);


        final Request r1 = request;

        alert.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //Toast.makeText(context,snapshot.child("CurrentMonth").getValue(String.class),Toast.LENGTH_SHORT).show();

                        try {

                            EditText e1 = alert_layout.findViewById(R.id.remark_admin);
                            String s1 = e1.getText().toString();

                            Request request = r1;

                            request.setStatus(true);
                            request.setRemarkAdmin("APPROVED: " + s1);

                            int amt = request.getAmt();

                            DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();

                            String id = r1.getId_month();

                            Maintenance m1 = snapshot.child("MaintenanceRecord").child(id).child(request.getFlatNo()).getValue(Maintenance.class);

                            m1.setAmt_paid(m1.getAmt_paid() + amt);

                            Month m2 = snapshot.child("Months").child(id).getValue(Month.class);

                            int amt2 = m2.getContr();

                            if (m1.getAmt_paid() < amt2) {
                                m1.setStatus(1);
                            } else if (m1.getAmt_paid() == amt2)
                            {
                                m1.setStatus(2);
                            }

                            m2.setAmtOb(m2.getAmtOb() + amt);

                            d1.child("Requests").child(request.getId()).setValue(request);
                            d1.child("MaintenanceRecord").child(id).child(request.getFlatNo()).setValue(m1);
                            d1.child("Months").child(id).setValue(m2);

                        }
                        catch(Exception e1)
                        {
                            Toast.makeText(ApproveRequest.this,e1.toString(),Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );

        alert.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                EditText e1 = alert_layout.findViewById(R.id.remark_admin);
                String s1 = e1.getText().toString();

                Request request = r1;

                request.setStatus(true);
                request.setRemarkAdmin("REJECTED: " + s1);

                DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                d1.child("Requests").child(request.getId()).setValue(request);

                dialogInterface.dismiss();

                refresh();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

}