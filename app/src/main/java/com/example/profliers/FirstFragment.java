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
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.profliers.databinding.FragmentFirstBinding;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {
    Database db;
    ArrayList<String> Surname,Name,Student_id,GPA,Created_Data;
    ArrayList<Integer> profile_id;

private FragmentFirstBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {



        List<String> list = new ArrayList<>();
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        Cursor c = db.getProfiles();
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    @SuppressLint("Range") Integer profile_id = c.getInt(c.getColumnIndex("profile_id"));
                    @SuppressLint("Range") String Surname = c.getString(c.getColumnIndex("Surname"));
                    @SuppressLint("Range") String Name = c.getString(c.getColumnIndex("Name"));
                    @SuppressLint("Range") String Student_id = c.getString(c.getColumnIndex("Student_id"));
                    @SuppressLint("Range") String GPA = c.getString(c.getColumnIndex("GPA"));
                    @SuppressLint("Range") String Creation_Database = c.getString(c.getColumnIndex("Creation_Database"));
                    list.add(profile_id.toString()  +"    :   " + Surname + "  ,  " + Name);
                }while (c.moveToNext());
            }
        }
        c.close();
        CustomAdapter listAdapter = new CustomAdapter(list);
        binding.listviewforbtnsprofiles.setAdapter(listAdapter);

        ((MainActivity)getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override

            public void onRefresh() {
                List<String> list2 = new ArrayList<>();
                Cursor d = db.getProfiles();
                if (d != null ) {
                    if  (d.moveToFirst()) {
                        do {
                            @SuppressLint("Range") Integer profile_id = d.getInt(d.getColumnIndex("profile_id"));
                            @SuppressLint("Range") String Surname = d.getString(d.getColumnIndex("Surname"));
                            @SuppressLint("Range") String Name = d.getString(d.getColumnIndex("Name"));

                            list2.add(profile_id.toString()  +"    :   " + Surname + "  ,  " + Name);
                        }while (d.moveToNext());
                    }
                }
                c.close();
//                    binding.listviewforbtnsprofiles.setAdapter(listAdapter);
                CustomAdapter listAdapter2 = new CustomAdapter(list2);
                binding.listviewforbtnsprofiles.setAdapter(listAdapter2);
                // Refresh Your Fragment
            }
        });
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
    }



@Override

    public void onDestroyView() {
        super.onDestroyView();
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
            textView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Sharecontext.MyVar.setCounter(i+1);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
            return textView;
        }
    }


}