package com.example.profliers;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import com.example.profliers.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.profliers.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListner {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private  Database db;
    public String Surname;
    public String Name;
    public String Student_id;
    public String GPA;
    public String Access_type ;
    ListView listView ;
    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              openDialog();
            }
        });

        db =new Database(this);




    }
    public void hidebutton(){
        binding.fab.setVisibility(View.INVISIBLE);
    }
    public void showbutton(){
        binding.fab.setVisibility(View.VISIBLE);
    }
    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
    }
    @Override
    public void Hello(){
        if(Surname != "" && Name !="" && Student_id != "" && GPA != ""){
        if(db.addtoprofile(Surname,Name,Student_id,GPA)){
            Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
            if(getFragmentRefreshListener()!=null){
                getFragmentRefreshListener().onRefresh();
            }

        }else{
            Toast.makeText(this,"Failure",Toast.LENGTH_LONG).show();
        }}else{
            Toast.makeText(this,"Enter Values",Toast.LENGTH_LONG).show();

        }
    }



    public interface FragmentRefreshListener{
        void onRefresh();
    }


    @Override
    public void applytext(String Surname, String Name, String Studentid, String GPA) {
            this.Surname= Surname;
            this.Name=Name;
            this.Student_id=Studentid;
            this.GPA=GPA;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}