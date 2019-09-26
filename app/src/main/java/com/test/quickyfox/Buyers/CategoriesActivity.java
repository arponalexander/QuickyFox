package com.test.quickyfox.Buyers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.test.quickyfox.Buyers.Categories.AirconServiceActivity;
import com.test.quickyfox.Buyers.Categories.CarpentryServicesActivity;
import com.test.quickyfox.Buyers.Categories.CleaningServicesActivity;
import com.test.quickyfox.Buyers.Categories.ConstructionServicesActivity;
import com.test.quickyfox.Buyers.Categories.DishCleaningServicesActivity;
import com.test.quickyfox.Buyers.Categories.ElectricalServicesActivity;
import com.test.quickyfox.Buyers.Categories.GroceryHelperServicesActivity;
import com.test.quickyfox.Buyers.Categories.LaundryServicesActivity;
import com.test.quickyfox.Buyers.Categories.MaintenanceServicesActivity;
import com.test.quickyfox.Buyers.Categories.MoverServicesActivity;
import com.test.quickyfox.Buyers.Categories.PestControlServicesActivity;
import com.test.quickyfox.Buyers.Categories.PlumbingServicesActivity;
import com.test.quickyfox.R;

public class CategoriesActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cleaningServices, contstructionServices, electricalServices, carpentryServices, groceryHelperServices, laundryServices,
            maintenanceServices,moverServices, pestControlServices, plumbingServices, airconServices,dishcleaningServices;

    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        back = findViewById(R.id.back_categories);

        cleaningServices = findViewById(R.id.cleaning_service_categories);
        contstructionServices = findViewById(R.id.construction_service_categories);
        electricalServices = findViewById(R.id.electrical_service_categories);
        carpentryServices = findViewById(R.id.carpentry_service_categories);
        groceryHelperServices = findViewById(R.id.grocery_helper_service_categories);
        laundryServices = findViewById(R.id.laundry_service_categories);
        maintenanceServices = findViewById(R.id.maintenance_service_categories);
        moverServices = findViewById(R.id.mover_service_categories);
        pestControlServices = findViewById(R.id.pest_control_service_categories);
        plumbingServices = findViewById(R.id.plumbing_service_categories);
        airconServices = findViewById(R.id.aircon_service_categories);
        dishcleaningServices = findViewById(R.id.dish_cleaning_service_categories);


        cleaningServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CategoriesActivity.this, CleaningServicesActivity.class);
                startActivity(intent);
            }
        });

        contstructionServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CategoriesActivity.this, ConstructionServicesActivity.class);
                startActivity(intent);

            }
        });

        electricalServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(CategoriesActivity.this, ElectricalServicesActivity.class);
                startActivity(intent);
            }
        });

        carpentryServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(CategoriesActivity.this, CarpentryServicesActivity.class);
                startActivity(intent);
            }
        });
        groceryHelperServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(CategoriesActivity.this, GroceryHelperServicesActivity.class);
                startActivity(intent);
            }
        });
        laundryServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(CategoriesActivity.this, LaundryServicesActivity.class);
                startActivity(intent);
            }
        });

        maintenanceServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(CategoriesActivity.this, MaintenanceServicesActivity.class);
                startActivity(intent);
            }
        });
        moverServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(CategoriesActivity.this, MoverServicesActivity.class);
                startActivity(intent);
            }
        });
        pestControlServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(CategoriesActivity.this, PestControlServicesActivity.class);
                startActivity(intent);
            }
        });
        plumbingServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CategoriesActivity.this, PlumbingServicesActivity.class);
                startActivity(intent);

            }
        });
        airconServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(CategoriesActivity.this, AirconServiceActivity.class);
                startActivity(intent);
            }
        });
        dishcleaningServices.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(CategoriesActivity.this, DishCleaningServicesActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CategoriesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}
