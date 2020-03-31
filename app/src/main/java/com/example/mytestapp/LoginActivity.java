package com.example.mytestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mytestapp.db.AppDatabase;
import com.example.mytestapp.db.async.CreateUser;
import com.example.mytestapp.db.entities.Address1;
import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.ui.about.AboutFragment;
import com.example.mytestapp.ui.home.HomeFragment;
import com.example.mytestapp.ui.login.LoginFragment;
import com.example.mytestapp.ui.register.RegisterFragment;
import com.example.mytestapp.ui.settings.SettingsFragment;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private User user;
    private UserRepository repository;
    private static final String TAG = "LoginActivity";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("MCPJACK");
        repository = getUserRepository();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarguest);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.guest_layout, new LoginFragment(), "1").commit();



    }

    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.sort);
        menuItem.setVisible(false);
        MenuItem menuItem2 = menu.findItem(R.id.edit);
        menuItem2.setVisible(false);
        return true;
    }


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
        EditText userName = findViewById(R.id.userNameLogin);
        String email = userName.getText().toString();
        EditText password = (EditText) findViewById(R.id.passwordLogin);
        String password1 = password.getText().toString();


        repository.getUser(email, getApplication()).observe(LoginActivity.this, userEntity -> {
            if (userEntity != null) {
                if (userEntity.getPassword().equals(password1)) {
                    // We need an Editor object to make preference changes.
                    // All objects are from android.context.Context
                    //SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
                    //editor.putString(BaseActivity.PREFS_USER, clientEntity.getEmail());
                    //editor.apply();
                    //Creation of a sharedPreference to save the user

                    sharedPreferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor edt = sharedPreferences.edit();

                    Gson gson = new Gson();
                    String json = gson.toJson(userEntity);
                    edt.putString("User", json);
                    edt.apply();



                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    //emailView.setText("");
                    //passwordView.setText("");
                } else {
                    /*passwordView.setError(getString(R.string.error_incorrect_password));
                    passwordView.requestFocus();
                    passwordView.setText("");*/
                }
                //progressBar.setVisibility(View.GONE);
            } else {
                //emailView.setError(getString(R.string.error_invalid_email));
                //emailView.requestFocus();
                //passwordView.setText("");
                //progressBar.setVisibility(View.GONE);
            }
        });




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
        boolean result = isValidEmail((EditText)findViewById(R.id.emailRegister));
        boolean result2 = isValidPassword((EditText)findViewById(R.id.passwordRegister),(EditText)findViewById(R.id.passwordCheckRegister));
        if(result==true && result2==true ) {

            User user = new User();


            EditText firstname = (EditText) findViewById(R.id.firstnameRegister);
            user.setFirstName(firstname.getText().toString());

            EditText name = (EditText) findViewById(R.id.lastNameRegister);
            user.setLastName(name.getText().toString());

            DatePicker date = (DatePicker) findViewById(R.id.datePickerRegister);
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy.MM.dd");
            String dateFormat = dateformat.format(new Date(date.getYear(),date.getMonth(),date.getDayOfMonth()));
            user.setBirthdate(dateFormat);

            EditText phone = (EditText) findViewById(R.id.phoneNumberRegister);
            user.setPhoneNumber(phone.getText().toString());

            EditText address = (EditText) findViewById(R.id.adressRegister);
            Spinner mySpinner = (Spinner) findViewById(R.id.spinnerCityListRegister);
            user.setAddress(new Address1(address.getText().toString(),mySpinner.getSelectedItem().toString()));

            EditText email = (EditText) findViewById(R.id.emailRegister);
            user.setEmail(email.getText().toString());

            EditText password = (EditText) findViewById(R.id.passwordRegister);
            user.setPassword(password.getText().toString());

            new CreateUser(getApplication(), new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "createUserWithEmail: success");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.d(TAG, "createUserWithEmail: failure", e);
                }
            }).execute(user);





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

