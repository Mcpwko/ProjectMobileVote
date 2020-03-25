package com.example.mytestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.ui.about.AboutFragment;
import com.example.mytestapp.ui.home.HomeFragment;
import com.example.mytestapp.ui.login.LoginFragment;
import com.example.mytestapp.ui.register.RegisterFragment;
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
        transaction.replace(R.id.guest_layout, new LoginFragment(), "1").commit();



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
        //Intent intent = new Intent(this, RegisterActivity.class);
        //startActivity(intent);

        FragmentTransaction transaction;

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.guest_layout, new RegisterFragment(), null).commit();
    }



    public void connect(View view){

        //DB
        AppDatabase db;

        db = AppDatabase.getInstance(this,null);


        EditText userName = findViewById(R.id.userName);

        User user = db.userDao().getUserByEmail(userName.getText().toString());

        EditText password = (EditText) findViewById(R.id.password);



        if(user.getPassword().equals(password.getText().toString())){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }




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


    public void create(View view){
        boolean result = isValidEmail((EditText)findViewById(R.id.email));
        boolean result2 = isValidPassword((EditText)findViewById(R.id.password),(EditText)findViewById(R.id.passwordCheck));
        if(result==true && result2==true ) {

            // ICI DB

            AppDatabase db;

            db = AppDatabase.getInstance(this,null);

            User user = new User();

            user.setFirstName(findViewById(R.id.editText4).toString());
            user.setLastName(findViewById(R.id.editText3).toString());
            user.setBirthdate(findViewById(R.id.datePicker1).toString());
            user.setPhoneNumber(findViewById(R.id.phoneNumber).toString());
            user.setAddress(findViewById(R.id.editText6).toString());
            user.setEmail(findViewById(R.id.email).toString());
            user.setPassword(findViewById(R.id.password).toString());

            db.userDao().insertUser(user);



            FragmentTransaction transaction;
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.guest_layout, new LoginFragment()).commit();
        }
    }

    public final static boolean isValidEmail(EditText target1) {
        CharSequence target = target1.getText();
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidPassword(EditText target, EditText target2){
        String password = target.getText().toString();
        String password2 = target2.getText().toString();
        if(password.isEmpty() || password2.isEmpty())
            return false;
        if(password.equals(password2))
            return true;
        return false;
    }

}

