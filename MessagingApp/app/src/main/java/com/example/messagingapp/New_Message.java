package com.example.messagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class New_Message extends AppCompatActivity {
DatabaseReference firebaseDatabase;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
EditText editText;
ListView listView;
Button button;
TextView textView,textView1;
ImageView imageView,imageView1;
String ew,search;
List<Users> list;
public static String extra,extra1,extra2;
int ew1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__message);
        listView=findViewById(R.id.new_mes_list);
        editText=findViewById(R.id.new_mes_search);
        button=findViewById(R.id.search_b);

        list=new ArrayList<>();
        final newad newad=new newad(getBaseContext(),getData());
        listView.setAdapter(newad);
        newad.notifyDataSetChanged();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               search=editable.toString();
               firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                list=new ArrayList<>();

                    firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                    firebaseDatabase= FirebaseDatabase.getInstance().getReference("Users");
                    Query query=firebaseDatabase.orderByChild("Username").startAt(search)
                            .endAt(search+"\uf8ff");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    Users users=dataSnapshot.getValue(Users.class);
                                    if(!users.getID().equals(firebaseUser.getUid())){
                                        assert users!=null;
                                        list.add(users);
                                    }
                                }
                                ((newad)listView.getAdapter()).update(list);



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


            }
        });

     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             TextView textView1=view.findViewById(R.id.result_name);
             ImageView imageView=view.findViewById(R.id.result_img);
             Users users=list.get(i);
             Intent intent=new Intent(New_Message.this,New_Chat.class);
             extra=users.getID();
             extra1=textView1.getText().toString();
              extra2=users.getImageSrc();
             Toast.makeText(New_Message.this,users.getID(),Toast.LENGTH_SHORT).show();
             startActivity(intent);
         }
     });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }
   public class newad extends BaseAdapter{
        private Context context;
        private List<Users> usersList;

        public newad(Context context, List<Users> usersList) {
            this.context = context;
            this.usersList = usersList;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public List<Users> getUsersList() {
            return usersList;
        }

        public void setUsersList(List<Users> usersList) {
            this.usersList = usersList;
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.list_newchats,null);

            textView=view.findViewById(R.id.result_name);
            imageView=view.findViewById(R.id.result_img);
            ew=usersList.get(i).getImageSrc();
            ew1=getResources().getIdentifier(ew,"drawable",New_Message.this.getPackageName());
            textView.setText(usersList.get(i).getUsername());
            imageView.setImageResource(ew1);

            return view;
        }
       public void update(List<Users> variables){
           usersList=new ArrayList<> ();
           usersList.addAll (variables);
           notifyDataSetChanged ();
       }
    }

    public List<Users> getData(){
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase= FirebaseDatabase.getInstance().getReference("Users");

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Users users=dataSnapshot.getValue(Users.class);

                    if(!users.getID().equals(firebaseUser.getUid())){
                        assert users!=null;
                        list.add(users);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(New_Message.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        return list;
        }
       private void clicker(final List<Users> arrayList){


           firebaseDatabase= FirebaseDatabase.getInstance().getReference("Users");
           firebaseDatabase.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                       Users users=dataSnapshot.getValue(Users.class);
                       assert users!=null;
                       arrayList.add(users);
                   }


                 //  Toast.makeText(New_Message.this,String.valueOf(arrayList.size()),Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   Toast.makeText(New_Message.this,error.getMessage(),Toast.LENGTH_SHORT).show();
               }
           });
       }

}