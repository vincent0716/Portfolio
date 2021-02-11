package com.example.messagingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class message_frag extends Fragment {

    private View view;
    public static String extra,extra1,extra2;
    private ListView listView;
    FirebaseUser firebaseUser;
    FloatingActionButton floatingActionButton;
    DatabaseReference databaseReference;

    private List<String> userr;
    List<Users> list;
    ContentLoadingProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.message_frag,container,false);
        listView=view.findViewById(R.id.list_messages);
        floatingActionButton=view.findViewById(R.id.floating_add);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        progressBar=view.findViewById(R.id.progress_bar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView=view.findViewById(R.id.txtuser_list);
                ImageView imageView=view.findViewById(R.id.txtuser_img);

                Users users=list.get(i);
                Intent intent=new Intent(getActivity(),Messages_Home.class);
                extra=users.getID();
                extra1=textView.getText().toString();
                int ww=view.getResources().getIdentifier(users.getImageSrc(),"drawable",getActivity().getPackageName());
                imageView.setImageResource(ww);
                Toast.makeText(getActivity(),users.getID(),Toast.LENGTH_SHORT).show();
                extra2=users.getImageSrc();
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),New_Message.class);
                startActivity(intent);
            }
        });

      //  progressBar.show();
        databaseReference= FirebaseDatabase.getInstance().getReference("Chats");
        userr=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userr.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Chats chats=dataSnapshot.getValue(Chats.class);
                    if(chats.getSender().equals(firebaseUser.getUid())){
                        userr.add(chats.getReceiver());

                    }
                    if(chats.getReceiver().equals(firebaseUser.getUid())){
                        userr.add(chats.getSender());

                    }

                }
                readchats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }



    private void readchats() {
        list=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);

                    for(String id:userr){
                        if(users.getID().equals(id)){
                            if(list.size()!=0){
                                for(Users users1:list){
                                    if(!users.getID().equals(users1.getID())){
                                        list.add(users);
                                    }
                                }
                            }
                            else{
                                list.add(users);
                            }
                        }
                    }
                }
                UserAdapter userAdapter=new UserAdapter(getActivity(),list);
                listView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
