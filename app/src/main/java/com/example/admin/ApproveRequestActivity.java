package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Maintenance;
import com.example.model.Month;
import com.example.model.Request;
import com.example.model.Transaction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class ApproveRequestActivity extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference storageReference;
    Request r1;
    DataSnapshot snapshot;
    String type,child_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request3);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        refresh();

        TextView title = findViewById(R.id.req_title);

        TextView e1 = findViewById(R.id.data1);
        TextView e2 = findViewById(R.id.data2);
        TextView e3 = findViewById(R.id.data3);
        TextView e4 = findViewById(R.id.data4);
        TextView e5 = findViewById(R.id.data5);
        TextView e6 = findViewById(R.id.data6);

        Intent i1 = getIntent();
        r1 = (Request)i1.getSerializableExtra("Request");
        type = i1.getStringExtra("Type");

        if(type.equals("0"))
        {
            e5.setText("Maintenance");
            child_id = "Requests";

        }
        else if(type.equals("1"))
        {
            e5.setText("Advance Payment");
            child_id = "AdvancePayRequests";
        }
        else
        {
            e5.setText("Previous Due Payment");
            child_id = "DueRequests";
        }

        e1.setText(r1.getDate());
        e2.setText(r1.getFlatNo());
        e3.setText("₹" + r1.getAmt());
        e4.setText(r1.getRemarkClient());
        e6.setText(r1.getId());

        Button b1 = findViewById(R.id.download);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task<Uri> ref = storageReference.child("images/" + r1.getInvoice_id()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Intent i1 = new Intent(ApproveRequestActivity.this,ViewInvoice.class);
                        i1.putExtra("URL",uri.toString());
                        startActivity(i1);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });


            }
        });

        Button b2 = findViewById(R.id.approve);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type.equals("0"))
                {
                    EditText e1 = findViewById(R.id.data7);
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

                    int flag = 0;

                    if (m1.getAmt_paid() < amt2) {
                        m1.setStatus(1);
                    } else if (m1.getAmt_paid() == amt2)
                    {
                        m1.setStatus(2);
                    }
                    else
                    {
                        flag = 1;
                    }

                    if(flag==0) {
                        m2.setAmtOb(m2.getAmtOb() + amt);

                        d1.child("Requests").child(request.getId()).setValue(request);
                        d1.child("MaintenanceRecord").child(id).child(request.getFlatNo()).setValue(m1);
                        d1.child("Months").child(id).setValue(m2);

                        if(r1.getId_month().equals(snapshot.child("CurrentMonth").getValue(String.class))==false)
                        {
                            int a = snapshot.child("TotalDue").child(r1.getFlatNo()).getValue(Integer.class);
                            d1.child("TotalDue").child(r1.getFlatNo()).setValue(a - r1.getAmt());
                        }

                        //String t_id = d1.child("Transactions").push().getKey();
                        Transaction transaction = new Transaction(r1.getAmt(), 0, r1.getDate(), r1.getId(), r1.getFlatNo());
                        d1.child("Transactions").child(r1.getId()).setValue(transaction);
                        Toast.makeText(ApproveRequestActivity.this,"Approved",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(ApproveRequestActivity.this,"Cannot Approve, flat owner cannot pay more than the maintenance amount set by you, ask them to file request under advance payment, if already paid and reject the request.",Toast.LENGTH_LONG).show();
                    }
                }
                else if(type.equals("1"))
                {
                    EditText e1 = findViewById(R.id.data7);
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
                    Transaction transaction = new Transaction(r1.getAmt(), 1, r1.getDate(), r1.getId(), r1.getFlatNo());
                    d1.child("Transactions").child(r1.getId()).setValue(transaction);
                    Toast.makeText(ApproveRequestActivity.this,"Approved",Toast.LENGTH_LONG).show();
                }
                else
                {
                    EditText e1 = findViewById(R.id.data7);
                    String s1 = e1.getText().toString();

                    Request request = r1;

                    request.setStatus(true);
                    request.setRemarkAdmin("APPROVED: " + s1);

                    int amt2;
                    if(snapshot.child("TotalDue").child(r1.getFlatNo()).exists()==false)
                    {
                        amt2 = 0;
                    }
                    else
                    {
                        amt2 = snapshot.child("TotalDue").child(r1.getFlatNo()).getValue(Integer.class);
                    }

                    if(amt2 - r1.getAmt() <0)
                    {
                        Toast.makeText(ApproveRequestActivity.this,"Cannot approve, flat owner cannot pay more than the amount that is due, ask them to file request under advance payment and reject the request",Toast.LENGTH_LONG).show();
                    }
                    else {

                        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                        d1.child("TotalDue").child(r1.getFlatNo()).setValue(amt2 - r1.getAmt());
                        d1.child("DueRequests").child(r1.getId()).setValue(r1);

                        Month m2 = snapshot.child("Months").child(r1.getId_month()).getValue(Month.class);
                        m2.setAmtOb(m2.getAmtOb() + r1.getAmt());
                        d1.child("Months").child(r1.getId_month()).setValue(m2);

                        String t_id = d1.child("Transactions").push().getKey();
                        Transaction transaction = new Transaction(r1.getAmt(), 2, r1.getDate(), r1.getId(), r1.getFlatNo());
                        d1.child("Transactions").child(r1.getId()).setValue(transaction);
                        Toast.makeText(ApproveRequestActivity.this,"Approved",Toast.LENGTH_LONG).show();
                    }
                }
                refresh();
                back();
            }
        });

        Button b3 = findViewById(R.id.reject);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText e1 = findViewById(R.id.data7);
                String s1 = e1.getText().toString();

                Request request = r1;

                request.setStatus(true);
                request.setRemarkAdmin("REJECTED: " + s1);

                DatabaseReference d1 = FirebaseDatabase.getInstance().getReference();
                d1.child(child_id).child(request.getId()).setValue(request);
                back();
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

    public void back()
    {
        super.onBackPressed();
    }
}