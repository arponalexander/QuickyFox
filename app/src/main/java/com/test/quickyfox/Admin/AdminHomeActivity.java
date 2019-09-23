package com.test.quickyfox.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.quickyfox.Buyers.HomeActivity;
import com.test.quickyfox.Buyers.MainActivity;
import com.test.quickyfox.R;

public class AdminHomeActivity extends AppCompatActivity {

    private Button LogoutBtn, CheckOrderBtn,maintainProductsBtn,checkApproveProductsBtn,chechServiceProviderBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        CheckOrderBtn = (Button)findViewById(R.id.admin_check_services_btn);
        maintainProductsBtn = (Button)findViewById(R.id.admin_maintain_btn);
        checkApproveProductsBtn = findViewById(R.id.check_approve_new_services);
        chechServiceProviderBtn = findViewById(R.id.admin_check_service_provider_btn);

        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              Intent intent = new Intent(AdminHomeActivity.this, HomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);

                /*Toast.makeText(AdminHomeActivity.this, "Under Development", Toast.LENGTH_SHORT).show();*/

            }
        });

        chechServiceProviderBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(AdminHomeActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this,AdminNewOrderActivity.class);
               startActivity(intent);
            }
        });

        checkApproveProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this, AdminCheckNewProductsActivity.class);
                startActivity(intent);
            }
        });
    }
}
