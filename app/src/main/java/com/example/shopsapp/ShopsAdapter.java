package com.example.shopsapp;

import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder>  {


    Context context;

    int i=0;
    String itemkey;


    private ArrayList<shopmodel> exampleList;

    public ShopsAdapter(ArrayList<shopmodel> exampleList, Context context) {
        this.exampleList = exampleList;
        this.context = context;
    }


    public void filterList(ArrayList<shopmodel> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        exampleList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopsdisplaylayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopsAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        shopmodel model = exampleList.get(position);
        holder.name.setText("Name: "+model.getName());
        holder.number.setText("Number :"+model.getNumber());


        holder.items.setText("Items: "+model.getItems());
        holder.location.setText("City: "+model.getCity());
        Glide.with(context).load(model.getUrl()).into(holder.image);

        holder.viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,SingleItemScreen.class);
                i.putExtra("name",model.getName());
                i.putExtra("number",model.getNumber());
                i.putExtra("city",model.getCity());
                i.putExtra("desc",model.getDesc());
                i.putExtra("items",model.getItems());
                i.putExtra("offers",model.getOffer());
                i.putExtra("image",model.getUrl());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return exampleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView name,number,location,items;
        Button viewmore;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            name = (TextView) itemView.findViewById(R.id.Specname);
            number = (TextView) itemView.findViewById(R.id.Specnumber);
            location=itemView.findViewById(R.id.Speccity);
            image = itemView.findViewById(R.id.specimage);
            items=itemView.findViewById(R.id.Specitems);
            viewmore=itemView.findViewById(R.id.showstatus);
        }
    }
}





