package com.example.messagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Messages_Home extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    EditText editText;
    Intent intent=null;
    ListView listView;
    List<Chats> chatsList;
    Button button;
    TextView textView;
    ImageView imageView;
    String message,receiver,sender,image_receiver;
    public static String  img_sender;
    int extra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar=findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setTitle("");
        textView=findViewById(R.id.message_user);
        imageView=findViewById(R.id.chat_pic);
        listView=findViewById(R.id.scroll);
        sender_pic();
        editText=findViewById(R.id.text_message);
        button=findViewById(R.id.button_send);
        receiver=message_frag.extra;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                message=editable.toString();
            }
        });

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(receiver);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user=snapshot.getValue(Users.class);
                textView.setText(user.getUsername());
                extra=getResources().getIdentifier(message_frag.extra2,"drawable",Messages_Home.this.getPackageName());
                imageView.setImageResource(extra);
                read_message(firebaseUser.getUid(),receiver);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sender=firebaseUser.getUid();
                messages(sender,receiver,message,image_receiver,img_sender);
                editText.setText(" ");
            }
        });
    }

    private void messages(String sender,String receiver,String message,String image_receiver,String img_sender){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        hashMap.put("image_receiver",image_receiver);
        hashMap.put("image_sender",img_sender);
        ref.child("Chats").push().setValue(hashMap);
    }
    private void read_message(final String myid, final String userid){
        chatsList=new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Chats chats=dataSnapshot.getValue(Chats.class);
                    if(chats.getReceiver().equals(myid) && chats.getSender().equals(userid) || chats.getReceiver().equals(userid) && chats
                            .getSender().equals(myid)){
                        chatsList.add(chats);
                    }
                    newAd1 chatAdapter=new newAd1(Messages_Home.this,chatsList);
                    chatAdapter.notifyDataSetChanged();
                    listView.setAdapter(chatAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sender_pic(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                img_sender =users.getImageSrc();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getBaseContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}