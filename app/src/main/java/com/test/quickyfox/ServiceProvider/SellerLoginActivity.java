package com.test.quickyfox.ServiceProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.test.quickyfox.R;

public class SellerLoginActivity extends AppCompatActivity {

    private Button loginSellerBtn;
    private EditText emailInput, passwordInput;
    private TextView sellerRegister,forgotPasswordLink;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.seller_login_email);
        passwordInput = findViewById(R.id.seller_login_password);
        sellerRegister = findViewById(R.id.register_seller_txtView);
        loginSellerBtn = findViewById(R.id.seller_login_btn);
        loadingbar = new ProgressDialog(this);
        forgotPasswordLink = findViewById(R.id.seller_login_resetpassword);

        forgotPasswordLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerLoginActivity.this, SellerResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        loginSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginSeller();
                /*Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                startActivity(intent);*/
            }
        });

        sellerRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(SellerLoginActivity.this, SellerRegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginSeller() {

        final String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!email.equals("") && !password.equals("")) {

            loadingbar.setTitle("Seller Account Login");
            loadingbar.setMessage("Please wait, while we are logging you as a Service Provider");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Intent intent = new Intent(SellerLoginActivity.this, SellerHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "Please enter your login credentials", Toast.LENGTH_SHORT).show();
        }
    }
}
