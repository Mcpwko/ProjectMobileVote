package com.example.mytestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mytestapp.ui.about.AboutFragment;
import com.example.mytestapp.ui.login.LoginFragment;
import com.example.mytestapp.ui.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("MCPJACK");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarguest);
        setSupportActionBar(toolbar);

        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_login, R.id.nav_settings, R.id.nav_about)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_guest_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(toolbar,navController);


        navController.setGraph(R.navigation.guest_navigation);*/
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.guest_layout, new LoginFragment(), null).commit();



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**public boolean onOptionsItemSelected(MenuItem item){

    }*/

    //This method allows to start the Register Activity

    public void register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void connect(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_settings:
                //getFragmentManager().beginTransaction().commit();
                transaction.replace(R.id.guest_layout, new SettingsFragment(), null).commit();
                break;
            case R.id.action_about:
                //getFragmentManager().beginTransaction().commit();
                transaction.replace(R.id.guest_layout, new AboutFragment(), null).commit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void apply(View view){
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.guest_layout, new LoginFragment()).commit();
    }

}

