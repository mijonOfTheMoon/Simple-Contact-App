package com.example.contactapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CRUDHelper crudHelper;
    private ArrayList<Contact> contacts;
    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crudHelper = new CRUDHelper(new DatabaseHelper(this));
        contacts = new ArrayList<>();
        mAdapter = new ContactAdapter(this, contacts, this);

        RecyclerView rView = findViewById(R.id.RecyclerView);
        rView.setAdapter(mAdapter);
        rView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> startActivity(new Intent(this, FormActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshContacts();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void refreshContacts() {
        crudHelper.open();
        ArrayList<Contact> list = crudHelper.fetchAllUsers();
        crudHelper.close();

        contacts.clear();
        contacts.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}