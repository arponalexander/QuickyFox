package com.test.quickyfox.Buyers;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;
import com.test.quickyfox.Admin.AdminHomeActivity;
import com.test.quickyfox.Model.Users;
import com.test.quickyfox.Prevalent.Prevalent;
import com.test.quickyfox.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText InputPhoneNumber,InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingbar;
    private TextView NotAdminLink, forgotPasswordLink;
    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;
    private ImageView AdminLink;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton =  findViewById(R.id.login_btn);

        InputPassword =  findViewById(R.id.login_password_input);
        InputPhoneNumber =  findViewById(R.id.login_phone_number_input);
        AdminLink =  findViewById(R.id.login_Applogo);
        NotAdminLink =  findViewById(R.id.not_admin_panel_link);
        forgotPasswordLink =  findViewById(R.id.forget_password_link);
        loadingbar = new ProgressDialog(this);


        NotAdminLink.setVisibility(View.INVISIBLE);
        chkBoxRememberMe =  findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginUser();
            }
        });

        forgotPasswordLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "login");
                startActivity(intent);
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                LoginButton.setText("Login As Admin");
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

            NotAdminLink.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    LoginButton.setText("Login");
                    NotAdminLink.setVisibility(View.INVISIBLE);
                    parentDbName = "Users";
                }
            });
        }

        private void LoginUser()
        {

            String phone = InputPhoneNumber.getText().toString();
            String password = InputPassword.getText().toString();
            AlertDialog.Builder altdial = new AlertDialog.Builder(LoginActivity.this);

            //Will prompt a next key in the keyboard
            InputPhoneNumber.setOnKeyListener(new View.OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER))
                    {
                        // Perform action on Enter key press
                        InputPhoneNumber.clearFocus();
                        InputPassword.requestFocus();
                        return true;
                    }
                    return false;
                }
            });

            InputPassword.setOnKeyListener(new View.OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER))
                    {
                        // Perform action on Enter key press
                        // check for username - password correctness here
                        return true;
                    }
                    return false;
                }
            });

            if (TextUtils.isEmpty(phone)){

                AlertDialog alert = altdial.create();
                alert.setMessage("Please write your Phone number..");
                alert.show();
            /*Toast.makeText(this,"Please write your phone number..",Toast.LENGTH_SHORT);*/
            }
            else if (TextUtils.isEmpty(password)){

                AlertDialog alert = altdial.create();
                alert.setMessage("Please write your Password..");
                alert.show();
                /*Toast.makeText(this,"Please write your password...",Toast.LENGTH_SHORT);*/
            }
            else
            {
                loadingbar.setTitle("Login Account");
                loadingbar.setMessage( "Please wait, while we are checking the credentials");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

                AllowAccessToAccount(phone,password);
            }
    }

    private void AllowAccessToAccount(final String phone, final String password)
    {
        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);

        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                           if (parentDbName.equals("Admins"))
                           {
                               Toast.makeText(LoginActivity.this,"Welcome Admin, You are Logged in Successfully", Toast.LENGTH_SHORT).show();
                               loadingbar.dismiss();

                               Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                               startActivity(intent);
                           }
                           else if (parentDbName.equals("Users"))
                           {
                               Toast.makeText(LoginActivity.this,"Logged in Successfully", Toast.LENGTH_SHORT).show();
                               loadingbar.dismiss();

                               Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                               Prevalent.currentOnlineUser = usersData;
                               startActivity(intent);
                           }
                        }
                        else
                        {
                            loadingbar.dismiss();
                            Toast.makeText(LoginActivity.this,"Password is incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                else
                    {
                    Toast.makeText(LoginActivity.this, "Account with this phone number " + phone + " does not exist", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

}
