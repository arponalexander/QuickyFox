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
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.test.quickyfox.Buyers.CategoriesActivity;
import com.test.quickyfox.Buyers.ProductDetailsActivity;
import com.test.quickyfox.Model.Products;
import com.test.quickyfox.R;
import com.test.quickyfox.ViewHolder.ServiceViewHolder;

 public class LaundryServicesActivity extends AppCompatActivity
 {

     private DatabaseReference ProductsRef;
     private RecyclerView recyclerView;
     RecyclerView.LayoutManager layoutManager;
     private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_services);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        recyclerView = findViewById(R.id.recycler_laundry_services);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        /*recyclerView.setLayoutManager(new GridLayoutManager(this,2));*/
        recyclerView.setLayoutManager(layoutManager);
        back = findViewById(R.id.back_service);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LaundryServicesActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
    }
     @Override
     protected void onStart()
     {
         super.onStart();

         FirebaseRecyclerOptions<Products> options =
                 new FirebaseRecyclerOptions.Builder<Products>()
                         .setQuery(ProductsRef.orderByChild("Category").equalTo("Laundry Services"), Products.class)
                         .build();

         FirebaseRecyclerAdapter<Products, ServiceViewHolder> adapter =
                 new FirebaseRecyclerAdapter<Products, ServiceViewHolder>(options) {
                     @Override
                     protected void onBindViewHolder(@NonNull ServiceViewHolder holder, int position, @NonNull final Products model)
                     {

                         holder.txtProductName.setText(model.getPname());
                         /*holder.txtProductDescription.setText(model.getDescription());*/
                         holder.txtProductPrice.setText("₱" + model.getPrice());
                         /* holder.txtProductCategory.setText("Category: " + model.getCategory());*/
                         holder.txtProductSeller.setText("Provider: " + model.getSellerName());
                         Picasso.get().load(model.getImage()).into(holder.imageView);


                         holder.itemView.setOnClickListener(new View.OnClickListener(){

                             @Override
                             public  void onClick(View view)
                             {

                                 {
                                     Intent intent = new Intent(LaundryServicesActivity.this, ProductDetailsActivity.class);
                                     intent.putExtra("pid", model.getPid());
                                     startActivity(intent);
                                 }

                             }

                         });
                     }

                     @NonNull
                     @Override
                     public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                     {
                         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_items_layout_list, parent, false);
                         ServiceViewHolder holder = new ServiceViewHolder(view);
                         return  holder;


                     }
                 };

         recyclerView.setAdapter(adapter);
         adapter.startListening();



     }

 }