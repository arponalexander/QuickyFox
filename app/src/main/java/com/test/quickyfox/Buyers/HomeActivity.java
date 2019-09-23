package com.test.quickyfox.Buyers;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.test.quickyfox.Admin.AdminMaintainProductsActivity;
import com.test.quickyfox.Buyers.ServiceStatus.ServiceStatusActivity;
import com.test.quickyfox.Model.Products;
import com.test.quickyfox.Prevalent.Prevalent;
import com.test.quickyfox.R;
import com.test.quickyfox.ViewHolder.ProductViewHolder;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference ProductsRef;
    private Query SearchRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    boolean doubleBackToExitPressedOnce = false;

    private String type ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //enables the admin to view home activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            type = getIntent().getExtras().get("Admin").toString();
        }
        /*else
        {
            type = getIntent().getExtras().get("Sellers").toString();
        }*/
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        SearchRef = FirebaseDatabase.getInstance().getReference().child("Products").child("productState").equalTo("Approved");


        Paper.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if(!type.equals("Admin"))
                {

                  Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(HomeActivity.this, "Not available", Toast.LENGTH_SHORT).show();
                }

            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.profile_image);

        //enables the admin to go to home without validation in the user
        if(!type.equals("Admin"))
        {
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        /*recyclerView.setLayoutManager(new GridLayoutManager(this,2));*/
        recyclerView.setLayoutManager(layoutManager);

    }
    /*private void firebaseSearch(String searchText){
        Query firebaseSearchQuery = SearchRef.orderByChild("pname").startAt("searchText").endAt("searchText" +"\uf8ff");

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder> {
            Products.class,
                    R.layout.activity_home,
                    ProductViewHolder.class,
                    firebaseSearchQuery
        }
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
            ProductViewHolder holder = new ProductViewHolder(view);
            return  holder;


        }
    }*/



    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef.orderByChild("productState").equalTo("Approved"), Products.class)
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
                                if(type.equals("Admin"))
                                {
                                    Intent intent = new Intent(HomeActivity.this, AdminMaintainProductsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);

                                }
                                else
                                {
                                    Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
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

    @Override
    public void onBackPressed() {

            super.onBackPressed();
            finish();

    }
    //Promt end up in main activity//
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();*/

        //default//
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_my_services)
        {
            if(!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, ServiceStatusActivity.class);
                startActivity(intent);
            }


        }
        else if (id == R.id.nav_cart)
        {
            if(!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }

        }
        else if (id == R.id.nav_search)
        {
            if(!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, SearchProductActivity.class);
                startActivity(intent);
            }

        }
        else if (id == R.id.nav_category)
        {
            if(!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, CategoriesActivity.class);
                startActivity(intent);
            }

        }


        else if (id == R.id.nav_settings)
        {
            if(!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }


        }
        else if (id == R.id.nav_logout)
        {
            if(!type.equals("Admin"))
            {
                Paper.book().destroy();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }


        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
