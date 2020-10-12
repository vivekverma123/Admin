package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.provider.CalendarContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.model.Maintenance;
import com.example.model.Month;
import com.example.model.Request;
import com.example.model.Transaction;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApproveAdvanceRequest extends AppCompatActivity {

    ListView l1;
    DataSnapshot snapshot;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_approve_advance_request);

        refresh();

        context = ApproveAdvanceRequest.this;

        l1 = findViewById(R.id.list_req);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Request request = (Request)parent.getItemAtPosition(position);

                Intent i1 = new Intent(ApproveAdvanceRequest.this,ApproveRequestActivity.class);
                i1.putExtra("Request", request);
                i1.putExtra("Type","1");
                startActivity(i1);
            }
        });


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ownerRef = myRef.child("AdvancePayRequests");

        ownerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Request> f1 = new ArrayList<>();
                for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                    Request request = d1.getValue(Request.class);
                    if(request.getStatus()==false) {
                        f1.add(request);
                    }
                }
                UserAdapter2 u1 = new UserAdapter2(ApproveAdvanceRequest.this,f1);
                l1.setAdapter(u1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void refresh()
    {
        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
        d1.addValueEventListener(new ValueEventListener() {
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

                            int amt2;
                            if(snapshot.child("AdvancedAmount").child(r1.getFlatNo()).exists()==false)
                            {
                                amt2 = 0;
                            }
                            else
                            {
                                amt2 = snapshot.child("AdvancedAmount").child(r1.getFlatNo()).getValue(Integer.class);
                            }

                            DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                            d1.child("AdvancedAmount").child(r1.getFlatNo()).setValue(r1.getAmt() + amt2);
                            d1.child("AdvancePayRequests").child(r1.getId()).setValue(r1);

                            Month m2 = snapshot.child("Months").child(r1.getId_month()).getValue(Month.class);
                            m2.setAmtOb(m2.getAmtOb() + r1.getAmt());
                            d1.child("Months").child(r1.getId_month()).setValue(m2);

                            String t_id = d1.child("Transactions").push().getKey();
                            Transaction transaction = new Transaction(r1.getAmt(),1,r1.getDate(),t_id,r1.getFlatNo());
                            d1.child("Transactions").child(t_id).setValue(transaction);

                        }
                        catch(Exception e1)
                        {
                            Toast.makeText(ApproveAdvanceRequest.this,e1.toString(),Toast.LENGTH_SHORT).show();
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
                d1.child("AdvancePayRequests").child(request.getId()).setValue(request);

                dialogInterface.dismiss();

                refresh();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void onResume() {

        super.onResume();
        refresh();
    }

}