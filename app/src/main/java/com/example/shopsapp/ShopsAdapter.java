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


public class ShopsAdapter extends FirebaseRecyclerAdapter<shopmodel, ShopsAdapter.myviewholder> implements Filterable {


    Context context;

    int i=0;
    String itemkey;


    private List<shopmodel> exampleList;
    private List<shopmodel> exampleListFull;
    public ShopsAdapter(@NonNull FirebaseRecyclerOptions<shopmodel> options, Context context) {
        super(options);
        this.context = context;


    }




    @NonNull
    @Override
    public com.example.shopsapp.ShopsAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopsdisplaylayout, parent, false);

        return new com.example.shopsapp.ShopsAdapter.myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull shopmodel model) {
        holder.name.setText("Name: "+model.getName());
        holder.number.setText("Number :"+model.getNumber());

        itemkey= getRef(position).getKey();

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



    class myviewholder extends RecyclerView.ViewHolder {

        TextView name, number,location;


        Button viewmore;

        ImageView image;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.Specname);
            number = (TextView) itemView.findViewById(R.id.Specnumber);
            location=itemView.findViewById(R.id.Speccity);
            image = itemView.findViewById(R.id.specimage);
            viewmore=itemView.findViewById(R.id.showstatus);



        }
    }
 /*   @Override
    public int getItemCount() {
        return exampleList.size();
    }
*/
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<shopmodel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (shopmodel item : exampleListFull) {
                    if (item.getItems().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


}