package com.example.messagingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private Context context;
    private List<Chats> usersList;
    private List<Users> users1;
    FirebaseUser firebaseUser;
    DatabaseReference firebaseDatabase;
    String wow;
    int extra;
    public static final int mes_left=0;
    public static final int mes_right=1;
    DatabaseReference databaseReference;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Chats> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Chats> usersList) {
        this.usersList = usersList;
    }

    public ChatAdapter(Context context, List<Chats> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    public ChatAdapter() {


    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int i) {
     return usersList.get(i);

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(usersList.get(position).getSender().equals(firebaseUser.getUid())){
            return mes_right;
        }
        else{
            return mes_left;
        }

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
    int w=getItemViewType(i);
        Chats users=usersList.get(i);
        TextView textView,textView1;
        ImageView imageView;
       wow=New_Message.extra2;
       sender_pic();

                        if(w==mes_left) {
                            view = LayoutInflater.from(context).inflate(R.layout.chat_left, viewGroup, false);
                            textView = view.findViewById(R.id.message1);
                          extra=view.getResources().getIdentifier(wow,"drawable",context.getPackageName());
                            imageView=view.findViewById(R.id.chat_pic_mes);
                         imageView.setImageResource(extra);
                            textView.setText(users.getMessage());
                        }
                        else {

                            view = LayoutInflater.from(context).inflate(R.layout.chat_right, viewGroup, false);
                            textView = view.findViewById(R.id.message2);
                            String ref=New_Chat.img_sender;

                           int rr=view.getResources().getIdentifier(ref,"drawable",context.getPackageName());
                           imageView = view.findViewById(R.id.chat_pic_mes_right);
                          imageView.setImageResource(rr);
                            textView.setText(users.getMessage());
                        }
        return view;
    }

    private void sender_pic(){
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        users1=new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        Query query=firebaseDatabase.orderByChild("ID").equalTo(firebaseUser.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);
                    users1.add(users);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
