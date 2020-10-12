package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.model.Request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RequestDetails extends AppCompatActivity {

    FirebaseStorage storage;
    StorageReference storageReference;
    Request r1;
    DataSnapshot snapshot;
    String type,child_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        TextView title = findViewById(R.id.req_title);

        TextView e1 = findViewById(R.id.data1);
        TextView e2 = findViewById(R.id.data2);
        TextView e3 = findViewById(R.id.data3);
        TextView e4 = findViewById(R.id.data4);
        TextView e5 = findViewById(R.id.data5);
        TextView e6 = findViewById(R.id.data6);
        TextView e7 = findViewById(R.id.data7);

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
        e7.setText(r1.getRemarkAdmin());

        Button b1 = findViewById(R.id.download);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task<Uri> ref = storageReference.child("images/" + r1.getInvoice_id()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Intent i1 = new Intent(RequestDetails.this,ViewInvoice.class);
                        i1.putExtra("URL",uri.toString());
                        startActivity(i1);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });


            }
        });

    }
}