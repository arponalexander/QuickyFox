package com.test.quickyfox.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.quickyfox.Model.Cart;
import com.test.quickyfox.R;
import com.test.quickyfox.ViewHolder.CartViewHolder;

public class AdminUserProductsActivity extends AppCompatActivity
{

    private RecyclerView productList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;

    private String userID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        userID = getIntent().getStringExtra("uid");

        productList = findViewById(R.id.products_list);
        productList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productList.setLayoutManager(layoutManager);

        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Admin View").child(userID).child("Products");


    }

    @Override
    protected void onStart()
    {

        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef,Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model)
            {
                /*holder.txtProductQuantity.setText("Quantity is = "+ model.getQuantity());*/
                holder.txtProductPrice.setText("Price is = " + model.getPrice());
                holder.txtProductName.setText("Service name: " + model.getPname());
                holder.txtProductCategory.setText("Service Category: " +model.getCategory());
                holder.txtProductSellerName.setText("Service Provider: " + model.getSellerName());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        productList.setAdapter(adapter);
        adapter.startListening();

    }
}
