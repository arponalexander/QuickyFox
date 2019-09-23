package com.test.quickyfox.ServiceProvider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.test.quickyfox.R;

public class SellerProductCategoryActivity extends AppCompatActivity {

    private ImageView carpentry, cleaning, construction,electrical;
    private ImageView groceryHelper,laundry,maintenance,mover;
    private ImageView pestControl, plumbing, airconCleaning, dishCleaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_category);



        carpentry = (ImageView) findViewById(R.id.carpentry_service);
        cleaning = (ImageView) findViewById(R.id.cleaning_service);
        construction = (ImageView) findViewById(R.id.construction_service);
        electrical = (ImageView) findViewById(R.id.electrical_service);
        groceryHelper = (ImageView) findViewById(R.id.grocery_helper_service);
        laundry = (ImageView) findViewById(R.id.laundry_service);
        maintenance = (ImageView) findViewById(R.id.maintenance_service);
        mover = (ImageView) findViewById(R.id.mover_service);
        pestControl = (ImageView) findViewById(R.id.pest_control_service);
        plumbing = (ImageView) findViewById(R.id.plumbing_service);
        airconCleaning = (ImageView) findViewById(R.id.aircon_service);
        dishCleaning = (ImageView) findViewById(R.id.dish_cleaning_service);


        carpentry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Carpentry Services");
                startActivity(intent);
            }

        });
        cleaning.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Cleaning Services");
                startActivity(intent);
            }

        });
        construction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Construction Services");
                startActivity(intent);
            }

        });

        electrical.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Electrical Services");
                startActivity(intent);
            }

        });

        groceryHelper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Grocery Helper Services");
                startActivity(intent);
            }

        });

        laundry.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Laundry Services");
                startActivity(intent);
            }

        });

        maintenance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Maintenance Services");
                startActivity(intent);
            }

        });

        mover.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Move and Relocate Services");
                startActivity(intent);
            }

        });

        pestControl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Pest Control Services");
                startActivity(intent);
            }

        });

        plumbing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Plumbing Services");
                startActivity(intent);
            }

        });

        airconCleaning.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Air Conditioner Cleaning Services");
                startActivity(intent);
            }

        });

        dishCleaning.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("Category","Dish Cleaning Services");
                startActivity(intent);
            }

        });


    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(SellerProductCategoryActivity.this, SellerHomeActivity.class);
        startActivity(intent);
    }

}
