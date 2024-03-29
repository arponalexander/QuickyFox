package com.test.quickyfox.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.test.quickyfox.Model.Products;
import com.test.quickyfox.R;
import com.test.quickyfox.ViewHolder.ServiceViewHolder;

public class SearchProductActivity extends AppCompatActivity
{
    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        inputText = findViewById(R.id.search_product_name);
        SearchBtn = findViewById(R.id.search_btn);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchProductActivity.this));

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchInput = inputText.getText().toString();

                onStart();
            }
        });
    }

        // search by product name
        @Override
        protected void onStart()
        {
            super.onStart();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");


            FirebaseRecyclerOptions<Products> options =
                    new FirebaseRecyclerOptions.Builder<Products>()
                            .setQuery(reference.orderByChild("pname").startAt(SearchInput), Products.class)
                            .build();

                    /*new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(reference.orderByChild("pname")).startAt(SearchInput),Products.class)
                    .build();*/

            FirebaseRecyclerAdapter<Products, ServiceViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ServiceViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ServiceViewHolder holder, int position, @NonNull final Products model)
                        {
                            holder.txtProductName.setText(model.getPname());
                           /* holder.txtProductDescription.setText(model.getDescription());*/
                            holder.txtProductPrice.setText("Price = ₱" + model.getPrice());
                            Picasso.get().load(model.getImage()).into(holder.imageView);

                            holder.itemView.setOnClickListener(new View.OnClickListener(){

                                @Override
                                public  void onClick(View view)
                                {
                                    Intent intent = new Intent(SearchProductActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }

                            });
                        }

                        @NonNull
                        @Override
                        public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                        {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_items_layout, parent, false);
                            ServiceViewHolder holder = new ServiceViewHolder(view);
                            return  holder;
                        }
                    };

            searchList.setAdapter(adapter);
            adapter.startListening();
        }



    }

