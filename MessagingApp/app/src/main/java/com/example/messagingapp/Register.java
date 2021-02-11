package com.example.messagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class Register extends AppCompatActivity {

    private EditText textView,textView1,textView2;
    private Button button;
    private Spinner spinner;
    int ints,intel;
    ImageView imageView;
    int[] icons={R.drawable.white,R.drawable.personfemale,R.drawable.personmale};
    String txt_user,txt_pass,txt_email,txt_pic;
     FirebaseAuth mAuth;
     DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth=FirebaseAuth.getInstance();
        spinner=findViewById(R.id.spinner_image);
        textView=findViewById(R.id.txt_reg_user);
        textView1=findViewById(R.id.txt_reg_email);
        textView2=findViewById(R.id.txt_reg_password);

        spinner.setAdapter(new imageadapter());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txt_pic=getResources().getResourceEntryName(icons[i]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        textView1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            txt_email=editable.toString().trim();
            }
        });


        textView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                txt_pass=editable.toString();
            }
        });


        txt_user=textView.getText().toString();
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                txt_user=editable.toString();
            }
        });

        button=findViewById(R.id.btn_reg);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creds(txt_email,txt_pass,txt_user);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();

    }

    private void creds(final String email, final String password, final String username){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){

                            Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        else{
                            FirebaseUser firebaseUser=mAuth.getCurrentUser();
                            databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

                            HashMap<String,String> result=new HashMap<>();
                            result.put("ID",firebaseUser.getUid());
                            result.put("Username",username);
                            result.put("Email",email);
                            result.put("Password",password);
                            result.put("ImageSrc",txt_pic);
                            
                            databaseReference.setValue(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(Register.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    }
                });
    }

    private class imageadapter extends BaseAdapter {


        public imageadapter() {


        }

        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.imageview,null);
            imageView=view.findViewById(R.id.reg_img);
            imageView.setImageResource(icons[i]);
            imageView.setTag(icons[i]);

            return view;
        }
    }

}