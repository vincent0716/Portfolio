package com.example.messagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    TextView textView;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    public static String wow;
    ProgressBar progressBar;
    Fragment fragment=null;
    ImageView imageView;
    String user1;
    int pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView=findViewById(R.id.home_user);
        progressBar=findViewById(R.id.progress_bar);
        imageView=findViewById(R.id.user_pic);
        progressBar.setVisibility(View.VISIBLE);
        user1=textView.getText().toString();
        fragment=new message_frag();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_frag,new message_frag()).commit();


        BottomNavigationView bottomNavigationView;
        bottomNavigationView=findViewById(R.id.bottomnavd);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment=null;
                switch (menuItem.getItemId()){
                    case R.id.messages:
                       fragment=new message_frag();

                        break;
                    case R.id.people:
                        fragment=new people_frag();

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.home_frag,fragment).commit();
                return true;
            }
        });


        Toolbar toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        setTitle(textView.getText());

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   Users user=snapshot.getValue(Users.class);
                   textView.setText(user.getUsername());
                   wow=user.getImageSrc();
                    pic=getResources().getIdentifier(user.getImageSrc().toString(),"drawable",Home.this.getPackageName());

                    imageView.setImageResource(pic);
                   progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.logout,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Home.this,MainActivity.class));
            return true;
        }
        return false;
    }


}