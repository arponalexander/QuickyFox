package com.test.quickyfox.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.quickyfox.R;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private CheckBox AcceptTermsAndConditions;
    private TextView TermsandConditions;
    private EditText InputName, InputPhoneNumber, InputPassword,InputConfirmPassword;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        AcceptTermsAndConditions = findViewById(R.id.checkbox_terms_and_condition);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        AcceptTermsAndConditions = findViewById(R.id.checkbox_terms_and_condition);
        TermsandConditions = findViewById(R.id.txt_terms_and_condition);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        InputConfirmPassword = (EditText) findViewById(R.id.register_confirm_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        loadingbar = new ProgressDialog(this);


        TermsandConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RegisterActivity.this, TermsAndConditionActivity.class);
                startActivity(intent);
            }
        });

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }
    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        String confirmPassword = InputConfirmPassword.getText().toString();
        AlertDialog.Builder altdial = new AlertDialog.Builder(RegisterActivity.this);

        if((phone.length() < 11)){

            AlertDialog alert = altdial.create();
            alert.setMessage("Please enter valid Phone number..");
            alert.show();
            /*Toast.makeText(this,"Please write your password...",Toast.LENGTH_SHORT);*/
            /*Toast.makeText(this,"Please enter a valid phone number",Toast.LENGTH_SHORT);*/
        }
        else if (TextUtils.isEmpty(name)){

            AlertDialog alert = altdial.create();
            alert.setMessage("Please your full name..");
            alert.show();
            /*Toast.makeText(this,"Please write your name...",Toast.LENGTH_SHORT);*/
        }
        else if (TextUtils.isEmpty(phone)){

            AlertDialog alert = altdial.create();
            alert.setMessage("Please your Phone number..");
            alert.show();
            /*Toast.makeText(this,"Please write your phone number..",Toast.LENGTH_SHORT);*/
        }
        else if (TextUtils.isEmpty(password)){
            AlertDialog alert = altdial.create();
            alert.setMessage("Please your password..");
            alert.show();
            /*Toast.makeText(this,"Please write your password...",Toast.LENGTH_SHORT);*/
        }
        else if (!confirmPassword.equals(password))
        {
            AlertDialog alert = altdial.create();
            alert.setMessage("Please re-enter your password..");
            alert.show();
            /*Toast.makeText(this,"Password did not match",Toast.LENGTH_SHORT);*/
        }
        else if(TextUtils.isEmpty(confirmPassword))
        {
            AlertDialog alert = altdial.create();
            alert.setMessage("Please your password number..");
            alert.show();
           /* Toast.makeText(this,"Please write again your password...",Toast.LENGTH_SHORT);*/
        }

        else
        {
            if(AcceptTermsAndConditions.isChecked())
            {
                loadingbar.setTitle("Create Account");
                loadingbar.setMessage( "Please wait, while we are checking the credentials");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

                ValidatephoneNumber(name, phone, password);
            }
            else
                {

                    Toast.makeText(this, "Please Read the Terms and Conditions before creating an account", Toast.LENGTH_SHORT).show();
                }

        }
    }

    private void ValidatephoneNumber(final String name, final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){

                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("password",password);
                    userdataMap.put("name",name);

                    RootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                                        Toast.makeText(RegisterActivity.this,"Congratulation your account has been created",Toast.LENGTH_SHORT).show();
                                                        loadingbar.dismiss();

                                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                        startActivity(intent);

                                    }
                                    else{

                                        loadingbar.dismiss();
                                        Toast.makeText(RegisterActivity.this,"Network Error: Please try again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(RegisterActivity.this,"This " + phone + "already exist",Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(RegisterActivity.this,"Please try again using another phone number",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
