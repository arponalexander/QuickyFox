package com.test.quickyfox.ServiceProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.test.quickyfox.R;

public class SellerResetPasswordActivity extends AppCompatActivity {

    private Button resetPassword;
    private EditText emailAddress;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_reset_password);

        auth = FirebaseAuth.getInstance();
        emailAddress = findViewById(R.id.seller_reset_password);
        resetPassword = findViewById(R.id.seller_reset_pass_btn);


        resetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ResetPassword();
            }
        });
    }

    private void ResetPassword()
    {
        final String email = emailAddress.getText().toString();

        if (!email.equals("")){

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(SellerResetPasswordActivity.this, "An email was sent to your email address", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SellerResetPasswordActivity.this, SellerLoginActivity.class);
                            startActivity(intent);

                        } else
                            {
                                Toast.makeText(SellerResetPasswordActivity.this, "Not a valid email address", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }


}
