package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.model.FlatOwner;
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

        l1 = findViewById(R.id.list_req);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogBox1();
            }
        });


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ownerRef = myRef.child("Requests");

        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Request> f1 = new ArrayList<>();
                for (DataSnapshot d1 : dataSnapshot.getChildren()) {
                    Request request = d1.getValue(Request.class);
                    if(request.getStatus()==false) {
                        f1.add(request);
                    }
                }
                UserAdapter2 u1 = new UserAdapter2(ApproveRequest.this,f1);
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

    void showDialogBox1()
    {
        final View alert_layout = getLayoutInflater().inflate(R.layout.dialog_approve,null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Approve/Reject Request");
        alert.setView(alert_layout);

        alert.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                }
        );

        alert.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();
    }

}