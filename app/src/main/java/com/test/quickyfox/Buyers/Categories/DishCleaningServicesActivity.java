package com.test.quickyfox.Buyers.Categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.test.quickyfox.Buyers.ProductDetailsActivity;
import com.test.quickyfox.Model.Products;
import com.test.quickyfox.R;
import com.test.quickyfox.ViewHolder.ProductViewHolder;

public class DishCleaningServicesActivity extends AppCompatActivity
{
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_cleaning_services);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        recyclerView = findViewById(R.id.recycler_dish_cleaning_services);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        /*recyclerView.setLayoutManager(new GridLayoutManager(this,2));*/
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef.orderByChild("Category").equalTo("Dish Cleaning Services"), Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {

                        holder.txtProductName.setText(model.getPname());
                        /*holder.txtProductDescription.setText(model.getDescription());*/
                        holder.txtProductPrice.setText("₱" + model.getPrice());
                        /* holder.txtProductCategory.setText("Category: " + model.getCategory());*/
                        Picasso.get().load(model.getImage()).into(holder.imageView);


                        holder.itemView.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public  void onClick(View view)
                            {

                                {
                                    Intent intent = new Intent(DishCleaningServicesActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }

                            }

                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return  holder;


                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }

}