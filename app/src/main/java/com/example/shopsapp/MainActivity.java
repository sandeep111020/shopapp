package com.example.shopsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button add;
    private RecyclerView recyclerView;
    private ShopsAdapter adapter;
    private DatabaseReference databaseRef;
    ArrayList<shopmodel> users = new ArrayList<shopmodel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=findViewById(R.id.addshop);
        recyclerView = findViewById(R.id.idRVItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users = new ArrayList<>();


        databaseRef = FirebaseDatabase.getInstance().getReference().child("Shops");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String tempname= snapshot1.child("name").getValue(String.class);
                    String tempnumber= snapshot1.child("number").getValue(String.class);
                    String tempcity= snapshot1.child("city").getValue(String.class);
                    String tempitems= snapshot1.child("items").getValue(String.class);
                    String tempoffer= snapshot1.child("offer").getValue(String.class);
                    String tempdesc= snapshot1.child("desc").getValue(String.class);
                    String tempurl= snapshot1.child("url").getValue(String.class);

                    users.add(new shopmodel(tempname, tempdesc,tempnumber,tempcity,tempitems,tempoffer,tempurl));
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new ShopsAdapter(users, MainActivity.this);
        recyclerView.setAdapter(adapter);
       /* FirebaseRecyclerOptions<shopmodel> options =
                new FirebaseRecyclerOptions.Builder<shopmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Shops"), shopmodel.class)
                        .build();

        adapter = new ShopsAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter);*/

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddShopScreen.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.example_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.action_search);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<shopmodel> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (shopmodel item : users) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getItems().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/
}