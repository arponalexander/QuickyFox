package com.test.quickyfox.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.test.quickyfox.R;
import com.test.quickyfox.ServiceProvider.SellerProductCategoryActivity;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity
{
    private Button applyChangesBtn, deleteBtn;
    private TextView sellerName,date;
    private EditText name,price,description;
    private ImageView imageView;

    private String productID = "";
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);

        //Enables the admin to get data in certain product
        productID = getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);
        sellerName = findViewById(R.id.product_seller);
        date = findViewById(R.id.product_date);
        applyChangesBtn = findViewById(R.id.apply_changes_btn);
        name = findViewById(R.id.product_name_maintain);
        price = findViewById(R.id.product_price_maintain);
        description = findViewById(R.id.product_description_maintain);
        imageView = findViewById(R.id.product_image_maintain);
        deleteBtn = findViewById(R.id.delete_product_btn);

        displaySpecificProductInfo();


       applyChangesBtn.setOnClickListener(new View.OnClickListener()

       {
            @Override
            public void onClick(View view)
            {

                applyChanges();
            }
        });

       deleteBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {
               deleteThisProduct();
           }
       });

    }

    private void deleteThisProduct()
    {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) 
            {
                Intent intent = new Intent(AdminMaintainProductsActivity.this, SellerProductCategoryActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(AdminMaintainProductsActivity.this, "Product Deleted", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void applyChanges() {
        String pName = name.getText().toString();
        String pPrice = price.getText().toString();
        String pDescription = description.getText().toString();


        if (pName.equals(""))
        {
            Toast.makeText(this, "Please write the product name", Toast.LENGTH_SHORT).show();
        }
        else if (pPrice.equals(""))
        {
                Toast.makeText(this,"Please write the product price",Toast.LENGTH_SHORT).show();
        }
        else if (pDescription.equals(""))
        {
                Toast.makeText(this,"Please write the product description",Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", pDescription);
            productMap.put("pname", pName);
            productMap.put("price",pPrice);



            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainProductsActivity.this, "Changes applied",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AdminMaintainProductsActivity.this, AdminHomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }

    private void displaySpecificProductInfo()
    {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if(dataSnapshot.exists())
                {
                    String pName = dataSnapshot.child("pname").getValue().toString();
                    String pPrice = dataSnapshot.child("price").getValue().toString();
                    String pDescription = dataSnapshot.child("description").getValue().toString();
                    String pImage = dataSnapshot.child("image").getValue().toString();
                    String pSeller = dataSnapshot.child("sellerName").getValue().toString();
                    String pDate = dataSnapshot.child("date").getValue().toString();

                    name.setText(pName);
                    price.setText(pPrice);
                    description.setText(pDescription);
                    sellerName.setText(pSeller);
                    date.setText(pDate);
                    Picasso.get().load(pImage).into(imageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}
