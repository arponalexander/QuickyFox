package com.test.quickyfox.ServiceProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.test.quickyfox.Admin.AdminCheckNewProductsActivity;
import com.test.quickyfox.Buyers.HomeActivity;
import com.test.quickyfox.Buyers.MainActivity;
import com.test.quickyfox.Model.Products;
import com.test.quickyfox.R;
import com.test.quickyfox.ServiceProvider.BottomNavigationHome.SellerAddFragment;
import com.test.quickyfox.ServiceProvider.BottomNavigationHome.SellerChatFragment;
import com.test.quickyfox.ServiceProvider.BottomNavigationHome.SellerHomeFragment;
import com.test.quickyfox.ViewHolder.ItemViewHolder;
import com.test.quickyfox.ViewHolder.ProductViewHolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SellerHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedProductsRef;

    private BottomNavigationView mMainNav;
    private SellerHomeFragment homeFragment;
    private SellerChatFragment chatFragment;
    private SellerAddFragment addFragment;
    private FrameLayout mFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        homeFragment = new SellerHomeFragment();
        chatFragment = new SellerChatFragment();
/*
        LogoutBtn = findViewById(R.id.seller_logout_btn);
        CheckOrderBtn = findViewById(R.id.seller_check_services_btn);
        maintainProductsBtn = findViewById(R.id.seller_maintain_btn);
        addNewServiceBtn = findViewById(R.id.add_new_service_btn);*/
        mMainNav = findViewById(R.id.nav_bar_seller_home);
        /* mFrameLayout = findViewById(R.id.frame_layout_seller_home);*/


        unverifiedProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        recyclerView = findViewById(R.id.seller_home_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /* setFragment(homeFragment);*/

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.home_nav_seller:

                        /*setFragment(homeFragment);*/

                        Intent intentHome = new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                        startActivity(intentHome);

                        return true;

                    case R.id.add_service_nav_seller:


                        Intent intentAddActivity = new Intent(SellerHomeActivity.this, SellerProductCategoryActivity.class);
                        intentAddActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentAddActivity);
                        finish();

                        return true;

                    case R.id.chat_nav_seller:

                        Toast.makeText(SellerHomeActivity.this, "Under Development", Toast.LENGTH_SHORT).show();

                        /* setFragment(chatFragment);*/

                        return true;

                    case R.id.logout_nav_seller:

                        CharSequence options[] = new CharSequence[]
                                {
                                        "Yes", "No"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                        builder.setTitle("Do you want to logout?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {

                                if (position == 0) {
                                    final FirebaseAuth mAuth;
                                    mAuth = FirebaseAuth.getInstance();
                                    mAuth.signOut();


                                    Intent intentMainActivity = new Intent(SellerHomeActivity.this, MainActivity.class);
                                    intentMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intentMainActivity);
                                    finish();
                                }

                                if (position == 1) {

                                    Intent intentHome = new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                                    startActivity(intentHome);

                                }

                            }
                        });

                        builder.show();

                }
                return true;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(unverifiedProductsRef.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()),Products.class)
                .build();

        final FirebaseRecyclerAdapter<Products, ItemViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Products model)
            {
                holder.txtProductName.setText(model.getPname());
                /*holder.txtProductDescription.setText(model.getDescription());*/
                holder.txtProductPrice.setText("Price = ₱" + model.getPrice());
                holder.txtProductStatus.setText("Service Status: " + model.getProductState());
                Picasso.get().load(model.getImage()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        final String productID = model.getPid();

                        CharSequence options[] = new CharSequence[]
                                {
                                        "Yes","No"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(SellerHomeActivity.this);
                        builder.setTitle("Do you want to delete this Service?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position)
                            {

                                if(position == 0)
                                {

                                    deleteProduct(productID);
                                }
                                if(position == 1)
                                {


                                }
                            }
                        });

                        builder.show();
                    }
                });


            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_seller_item_view, parent, false);
                ItemViewHolder holder = new ItemViewHolder(view);
                return  holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void deleteProduct(String productID) {

        unverifiedProductsRef.child(productID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {

                Toast.makeText(SellerHomeActivity.this, "The Service has been Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

