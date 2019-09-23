package com.test.quickyfox.ServiceProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.quickyfox.Buyers.TermsAndConditionActivity;
import com.test.quickyfox.R;

import java.util.HashMap;

public class SellerRegistrationActivity extends AppCompatActivity {

    private EditText nameInput, phoneInput, emailInput, passwordInput,addressInput;
    private Button registerBtn;
    private CheckBox SellerTerms;
    private TextView TermsAndConditionsSeller;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        SellerTerms=findViewById(R.id.checkbox_terms_and_condition_seller);
        TermsAndConditionsSeller = findViewById(R.id.txt_terms_and_condition_seller);
        mAuth = FirebaseAuth.getInstance();
        loadingbar = new ProgressDialog(this);
        registerBtn = findViewById(R.id.seller_registration_btn);
        nameInput = findViewById(R.id.seller_name);
        phoneInput = findViewById(R.id.seller_phone);
        emailInput = findViewById(R.id.seller_email);
        passwordInput = findViewById(R.id.seller_password);
        addressInput = findViewById(R.id.seller_address);

        TermsAndConditionsSeller.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerRegistrationActivity.this, TermsAndConditionActivity.class);
                startActivity(intent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                RegisterSeller();
            }
        });
    }

    private void RegisterSeller()
    {

        AlertDialog.Builder altdial = new AlertDialog.Builder(SellerRegistrationActivity.this);
        final String name = nameInput.getText().toString();
        final String phone = phoneInput.getText().toString();
        final String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        final String address = addressInput.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            AlertDialog alert = altdial.create();
            alert.setMessage("Please provide your name..");
            alert.show();
            /* Toast.makeText(this,"Please write again your password...",Toast.LENGTH_SHORT);*/
        }
        else if(TextUtils.isEmpty(email))
        {
            AlertDialog alert = altdial.create();
            alert.setMessage("Please provide your email address..");
            alert.show();
            /* Toast.makeText(this,"Please write again your password...",Toast.LENGTH_SHORT);*/
        }
        else if(TextUtils.isEmpty(phone))
        {
            AlertDialog alert = altdial.create();
            alert.setMessage("Please provide your phone number..");
            alert.show();
            /* Toast.makeText(this,"Please write again your password...",Toast.LENGTH_SHORT);*/
        }

        else if(SellerTerms.isChecked()) {

            if (!name.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !address.equals("")) {
                loadingbar.setTitle("Creating Account");
                loadingbar.setMessage("Please wait, while we are making you Service Provider");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final DatabaseReference rootRef;
                                    rootRef = FirebaseDatabase.getInstance().getReference();

                                    String sid = mAuth.getCurrentUser().getUid();

                                    HashMap<String, Object> sellerMap = new HashMap<>();
                                    sellerMap.put("sid", sid);
                                    sellerMap.put("phone", phone);
                                    sellerMap.put("email", email);
                                    sellerMap.put("address", address);
                                    sellerMap.put("name", name);

                                    rootRef.child("Sellers").child(sid).updateChildren(sellerMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    loadingbar.dismiss();
                                                    Toast.makeText(SellerRegistrationActivity.this, "You are now a BnB Retailer!", Toast.LENGTH_SHORT).show();


                                                    Intent intent = new Intent(SellerRegistrationActivity.this, SellerHomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });


                                }
                            }
                        });


            }

        }
        else
        {
            Toast.makeText(this, "Please Read and Accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
        }
    }
}
