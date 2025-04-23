package com.example.contactapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVH> {
    private final List<Contact> contacts;
    private final LayoutInflater inflater;
    private final MainActivity mainActivity;

    public ContactAdapter(Context ctx, List<Contact> contacts, MainActivity mainActivity) {
        this.contacts = contacts;
        this.inflater = LayoutInflater.from(ctx);
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ContactVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_contact, parent, false);
        return new ContactVH(itemView, parent.getContext(), mainActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVH holder, int position) {
        Contact c = contacts.get(position);

        holder.nameTextView.setText(c.name);
        holder.emailTextView.setText(c.email);
        holder.phoneTextView.setText(c.phone);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactVH extends RecyclerView.ViewHolder {
        final TextView nameTextView;
        final TextView emailTextView;
        final TextView phoneTextView;
        final Button editButton;
        final Button deleteButton;

        ContactVH(@NonNull View itemView, Context context, MainActivity mainActivity) {
            super(itemView);

            CRUDHelper crudHelper = new CRUDHelper(new DatabaseHelper(context));
            nameTextView  = itemView.findViewById(R.id.item_name);
            emailTextView = itemView.findViewById(R.id.item_email);
            phoneTextView = itemView.findViewById(R.id.item_phone);
            editButton    = itemView.findViewById(R.id.editButton);
            deleteButton  = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(v -> {
                Intent form = new Intent(context, FormActivity.class);
                form.putExtra("name", nameTextView.getText().toString());
                form.putExtra("phone", phoneTextView.getText().toString());
                form.putExtra("email", emailTextView.getText().toString());
                context.startActivity(form);
            });
            deleteButton.setOnClickListener(v -> new AlertDialog.Builder(context)
                    .setTitle("Konfirmasi")

                    .setMessage("Apakah kamu yakin ingin menghapus kontak ini?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        crudHelper.open();
                        crudHelper.delete(phoneTextView.getText().toString());
                        crudHelper.close();
                        mainActivity.refreshContacts();
                    })
                    .setNegativeButton("Tidak", null)
                    .show());
        }
    }
}