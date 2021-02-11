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
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class people_frag extends Fragment {

    private View view;
    List<Users> list;
    public static String extra,extra1,extra2;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.people_frag,container,false);

        final ListView listView=view.findViewById(R.id.list_user);
        list=new ArrayList<>();
        final UserAdapter userAdapter=new UserAdapter(getActivity(),list);

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users users=dataSnapshot.getValue(Users.class);
                     assert users!=null;
                     assert firebaseUser!=null;

                     if(!users.getID().equals(firebaseUser.getUid())){
                         list.add(users);
                     }
                }
                userAdapter.notifyDataSetChanged();
                listView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             TextView textView=view.findViewById(R.id.txtuser_list);
                ImageView imageView=view.findViewById(R.id.txtuser_img);

             Users users=list.get(i);
                Intent intent=new Intent(getActivity(),MessageActivity.class);
                extra=users.getID();
                extra1=textView.getText().toString();
                int ww=view.getResources().getIdentifier(users.getImageSrc(),"drawable",getActivity().getPackageName());
                imageView.setImageResource(ww);
                Toast.makeText(getActivity(),users.getID(),Toast.LENGTH_SHORT).show();
                extra2=users.getImageSrc();
                startActivity(intent);


            }
        });

        return view;
    }
}
