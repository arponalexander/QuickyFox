package com.test.quickyfox.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.quickyfox.Prevalent.Prevalent;
import com.test.quickyfox.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText;
    private Button confirmOrderBtn;

    private String totalAmount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total amount = ₱" + totalAmount, Toast.LENGTH_SHORT).show();

        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.shipment_name);
        phoneEditText = (EditText) findViewById(R.id.shipment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shipment_address);
        cityEditText = (EditText) findViewById(R.id.shipment_city);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check() {

        if (TextUtils.isEmpty(nameEditText.getText().toString())) {
            Toast.makeText(this, "Please provide your full name", Toast.LENGTH_LONG).show();

        }

        if (TextUtils.isEmpty(phoneEditText.getText().toString())) {
            Toast.makeText(this, "Please provide your Phone number", Toast.LENGTH_LONG).show();

        }

        if (TextUtils.isEmpty(addressEditText.getText().toString())) {
            Toast.makeText(this, "Please provide your Full address", Toast.LENGTH_LONG).show();

        }
        if (TextUtils.isEmpty(cityEditText.getText().toString())) {
            Toast.makeText(this, "Please provide your City", Toast.LENGTH_LONG).show();

        } else {
            ConfirmOrder();
        }

    }

    private void ConfirmOrder() {

        final String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("EEE, d MMM yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh :mm aa");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "not shipped");
        ordersMap.put("serviceStatus","Processing");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseDatabase.getInstance().getReference().child("Cart List")
                        .child("User View")
                        .child(Prevalent.currentOnlineUser.getPhone())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ConfirmFinalOrderActivity.this, "Your final order has been placed successfully.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });
            }
        });
    }

    /*@Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
}
