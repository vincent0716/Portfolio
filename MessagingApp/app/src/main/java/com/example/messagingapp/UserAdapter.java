package com.example.messagingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private List<Users> usersList;

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

    public UserAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    public UserAdapter() {


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
        view=inflater.inflate(R.layout.list_user,null);
        Users users=usersList.get(i);
        int ww=view.getResources().getIdentifier(users.getImageSrc(),"drawable",context.getPackageName());
        TextView textView=view.findViewById(R.id.txtuser_list);
        ImageView imageView=view.findViewById(R.id.txtuser_img);
        imageView.setImageResource(ww);
        textView.setText(users.getUsername());

        return view;
    }

}
