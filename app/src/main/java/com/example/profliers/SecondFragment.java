package com.example.profliers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.profliers.databinding.ContentMainBinding;
import com.example.profliers.databinding.FragmentFirstBinding;
import com.example.profliers.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

private FragmentSecondBinding binding;
    Integer ids;
    Database db;
    Cursor profile;
    List<String> list = new ArrayList<>();

    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        ids = Sharecontext.MyVar.counter;
        profile =db.getProfileact(ids);


        db.addtoactivity("Opened",Sharecontext.MyVar.counter);



        Cursor c = db.getProfileActivity(ids);
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    @SuppressLint("Range") String Activitys = c.getString(c.getColumnIndex("Access_type"));
                    @SuppressLint("Range") String Activity_time = c.getString(c.getColumnIndex("time_spam"));
                    list.add(Activitys + "   @   " + Activity_time);
                }while (c.moveToNext());
            }
        }
        c.close();
        CustomAdapter listAdapter = new CustomAdapter(list);
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        if (profile !=null){
            if(profile.moveToFirst()){
                do{
                    @SuppressLint("Range") String Name = profile.getString(profile.getColumnIndex("Name"));
                    @SuppressLint("Range") String Student_id = profile.getString(profile.getColumnIndex("Student_id"));
                    @SuppressLint("Range") String GPA = profile.getString(profile.getColumnIndex("GPA"));
                    @SuppressLint("Range") String Createdat = profile.getString(profile.getColumnIndex("Creation_Database"));
                    binding.Surname.setText("Surname:  " + profile.getString(profile.getColumnIndex("Surname")));
                    binding.Name.setText("Name:   " + Name);
                    binding.GPA.setText("GPA:  " + GPA);
                    binding.IDs.setText("Student ID : " + Student_id);
                    binding.PRcreate.setText("Created At:  " + Createdat);
                    break;
                }while (profile.moveToNext());
            }

        }
        binding.forspecificprofile.setAdapter(listAdapter);
      return binding.getRoot();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


        if (context !=null){
            db= new Database(context);

        }
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).hidebutton();
    }

@RequiresApi(api = Build.VERSION_CODES.O)
@Override
    public void onDestroyView() {
        db.addtoactivity("Closed",Sharecontext.MyVar.counter);
        super.onDestroyView();
    ((MainActivity) getActivity()).showbutton();
    binding = null;
    }
    class CustomAdapter extends BaseAdapter {
        List<String> items;

        public CustomAdapter(List<String> items) {
            super();
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return items.get(i).hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(getContext());
            textView.setText(items.get(i));
            textView.setPadding(23,23,23,23);
            textView.setId(i);
            return textView;
        }
    }


}