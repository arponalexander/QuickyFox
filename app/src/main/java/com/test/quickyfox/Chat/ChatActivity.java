//package com.test.quickyfox.Chat;
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.text.format.DateFormat;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.bookproject.adapters.AdapterChat;
//import com.example.bookproject.models.ModelChat;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//
//public class ChatActivity extends AppCompatActivity {
//
//    Toolbar toolbar;
//    RecyclerView recyclerView;
//    ImageView profileIv;
//    TextView nameTv, userStatusTv;
//    EditText messageEt;
//    ImageButton sendBtn;
//
//    //firebase auth
//    FirebaseAuth firebaseAuth;
//
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference userDbRef;
//
//    //for checking if user has seen message or not
//    ValueEventListener seenListener;
//    DatabaseReference userRefForSeen;
//
//    List<ModelChat> chatList;
//    AdapterChat adapterChat;
//
//    String hisUid;
//    String myUid;
//    String hisImage;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat);
//
//        //init views
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("");
//        recyclerView = findViewById(R.id.chat_recyclerView);
//        profileIv = findViewById(R.id.profileIv);
//        nameTv = findViewById(R.id.nameTv);
//        userStatusTv = findViewById(R.id.userStatusTv);
//        messageEt = findViewById(R.id.messageEt);
//        sendBtn = findViewById(R.id.sendBtn);
//
//        //layout for recyclerview
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setStackFromEnd(true);
//
//        //recyclerview properties
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        Intent intent = getIntent();
//        hisUid = intent.getStringExtra("hisUid");
//
//        //firebase auth instance
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        userDbRef = firebaseDatabase.getReference("Users");
//
//        //search user to get that users info
//        Query userQuery = userDbRef.orderByChild("uid").equalTo(hisUid);
//
//        //get user picture and name
//        userQuery.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                //check until required ifo is received
//                for (DataSnapshot ds: dataSnapshot.getChildren()){
//
//                    //get data
//                    String name = ""+ ds.child("name").getValue();
//                    hisImage = ""+ ds.child("image").getValue();
//                    String typingStatus = ""+ ds.child("typingTo").getValue();
//
//                    //check typing status
//                    if (typingStatus.equals(myUid)){
//                        userStatusTv.setText("Typing...");
//                    }
//                    else {
//
//                        //get value of online status
//                        String onlineStatus = ""+ ds.child("onlineStatus").getValue();
//                        if (onlineStatus.equals("online")){
//                            userStatusTv.setText(onlineStatus);
//
//                        }
//                        else {
//
//                            //convert timestamp to proper time and date
//                            //convert time stamp to dd//mm/yyyy hh:mm am/pm
//                            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//                            cal.setTimeInMillis(Long.parseLong(onlineStatus));
//                            String dateTime = DateFormat.format("dd/MM/yyyy hh:mm aa", cal).toString();
//                            userStatusTv.setText("Last seen at:"+ dateTime);
//
//                        }
//
//                    }
//
//                    //set data
//                    nameTv.setText(name);
//                    try {
//
//                        //if image received, set it to imageview in toolbar
//                        Picasso.get().load(hisImage).placeholder(R.drawable.ic_default_img_white).into(profileIv);
//
//                    }
//                    catch (Exception e){
//
//                        //exception getting picture, set default picture
//                        Picasso.get().load(R.drawable.ic_default_img_white).into(profileIv);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        //send message button
//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //get text from edit text
//                String message = messageEt.getText().toString().trim();
//
//                //check if text is empty or not
//                if (TextUtils.isEmpty(message)){
//
//                    //text empty
//                    Toast.makeText(ChatActivity.this,"Cannot send an empty message", Toast.LENGTH_SHORT).show();
//
//                }
//                else {
//
//                    //text not empty
//                    sendMessage(message);
//                }
//            }
//        });
//
//        //check edit text change listener
//        messageEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.toString().trim().length() == 0){
//                    checkTypingStatus("noOne");
//                }
//                else {
//
//                    //uid of receiver
//                    checkTypingStatus(hisUid);
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//
//        readMessages();
//
//
//        seenMessage();
//
//    }
//
//    private void seenMessage() {
//        userRefForSeen = FirebaseDatabase.getInstance().getReference("Chats");
//        seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds: dataSnapshot.getChildren()){
//                    ModelChat chat = ds.getValue(ModelChat.class);
//                    if (chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid)){
//                        HashMap<String, Object> hasSeenHastMap = new HashMap<>();
//                        hasSeenHastMap.put("isSeen", true);
//                        ds.getRef().updateChildren(hasSeenHastMap);
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    private void readMessages() {
//        chatList = new ArrayList<>();
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                chatList.clear();
//                for (DataSnapshot ds: dataSnapshot.getChildren()){
//                    ModelChat chat = ds.getValue(ModelChat.class);
//                    if (chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid) ||
//                            chat.getReceiver().equals(hisUid) && chat.getSender().equals(myUid)){
//                        chatList.add(chat);
//                    }
//
//                    //adapter
//                    adapterChat = new AdapterChat(ChatActivity.this, chatList, hisImage);
//                    adapterChat.notifyDataSetChanged();
//
//                    //set adapter to recyclerview
//                    recyclerView.setAdapter(adapterChat);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void sendMessage(String message) {
//
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//
//        String timestamp = String.valueOf(System.currentTimeMillis());
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("sender",myUid);
//        hashMap.put("receiver", hisUid);
//        hashMap.put("message", message);
//        hashMap.put("timestamp", timestamp);
//        hashMap.put("isSeen", false);
//        databaseReference.child("Chats").push().setValue(hashMap);
//
//        //reset edittext after sending message
//        messageEt.setText("");
//    }
//
//    private void checkUserStatus() {
//
//        //get current user
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user != null) {
//
//            //user is signed in stay here
//            //set email of logged in user
//            //mProfileTv.setText(user.getEmail());
//            //currently signed in users uid
//            myUid = user.getUid();
//
//        } else {
//
//            //user not signed in, go to main activity
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
//    }
//
//    private void checkOnlineStatus(String status){
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("onlineStatus", status);
//
//        //update value of online status of current user
//        dbRef.updateChildren(hashMap);
//    }
//
//    private void checkTypingStatus(String typing){
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("typingTo", typing);
//
//        //update value of online status of current user
//        dbRef.updateChildren(hashMap);
//    }
//
//    @Override
//    protected void onStart() {
//        checkUserStatus();
//
//        //set online
//        checkOnlineStatus("online");
//        super.onStart();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        //get timestamp
//        String timestamp = String.valueOf(System.currentTimeMillis());
//
//        //set offline with last seen timestamp
//        checkOnlineStatus(timestamp);
//        checkTypingStatus("noOne");
//        userRefForSeen.removeEventListener(seenListener);
//    }
//
//    @Override
//    protected void onResume() {
//
//        //set online
//        checkOnlineStatus("online");
//        super.onResume();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        //hide searchView
//        menu.findItem(R.id.action_search).setVisible(false);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        int id = item.getItemId();
//        if (id == R.id.action_logout){
//            firebaseAuth.signOut();
//            checkUserStatus();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//}
