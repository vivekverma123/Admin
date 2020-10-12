package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewInvoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invoice);

        Intent i1 = getIntent();
        String url = i1.getStringExtra("URL");

        ImageView imageView = findViewById(R.id.imageView);

        Picasso.with(this).load(url).resize(400, 600).into(imageView);

    }
}