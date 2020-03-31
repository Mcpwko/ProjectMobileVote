package com.example.mytestapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.FragmentTransaction;

import androidx.navigation.ui.AppBarConfiguration;



import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.Toast;


import com.example.mytestapp.db.async.CreateUser;
import com.example.mytestapp.db.entities.Address1;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.ui.about.AboutFragment;
import com.example.mytestapp.ui.login.LoginFragment;
import com.example.mytestapp.ui.register.RegisterFragment;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

//This activity is represented by the login menu at the beginning of the app
public class LoginActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private User user;
    private UserRepository repository;
    private UserRepository userRepository;
    private static final String TAG = "LoginActivity";
    SharedPreferences sharedPreferences;


    //In the onCreate method we set the toolbar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        repository = getUserRepository();
        userRepository = getUserRepository();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarguest);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.guest_layout, new LoginFragment(), "1").commit();



    }

    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }

    //This method creates the items of the menu
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.sort);
        menuItem.setVisible(false);
        MenuItem menuItem2 = menu.findItem(R.id.edit);
        menuItem2.setVisible(false);
        return true;
    }


    //This method allows to start the Register fragment

    public void register(View view){

        FragmentTransaction transaction;

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.guest_layout, new RegisterFragment(), null).commit();
    }



    //This method is used to connect the user to his account
    public void connect(View view){
        EditText userName = findViewById(R.id.userNameLogin);
        String email = userName.getText().toString();
        EditText password = (EditText) findViewById(R.id.passwordLogin);
        String password1 = password.getText().toString();




        //We try to match what he wrote to what there is in the database
        repository.getUser(email, getApplication()).observe(LoginActivity.this, userEntity -> {
            if (userEntity != null) {
                if (userEntity.getPassword().equals(password1)) {

                    sharedPreferences = getApplicationContext().getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor edt = sharedPreferences.edit();

                    Gson gson = new Gson();
                    String json = gson.toJson(userEntity);
                    edt.putString("User", json);
                    edt.apply();



                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                } else {
                    //If he couldn't connect we put an alertDialog message to notfiy it
                    password.setText("");
                    final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle(getString(R.string.warning));
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage(getString(R.string.wronginformation));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.okbutton), (dialog, which) -> {
                        alertDialog.dismiss();

                    });
                    alertDialog.show();

                }

            } else {
                password.setText("");
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(getString(R.string.warning));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.wronginformation));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.okbutton), (dialog, which) -> {
                    alertDialog.dismiss();

                });
                alertDialog.show();
            }
        });




    }


    //This methods explains what action is performed by which buttons
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.action_about:
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


    //This is what happens when we click on the Register button
    public void create(View view){
        //We check if the mail is valid and if the 2 passwords are the same
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
            String emailAdress = email.getText().toString();

            EditText password = (EditText) findViewById(R.id.passwordRegister);
            user.setPassword(password.getText().toString());

            //This is what we do if the user entered nothing
            if(firstname.getText().toString().equals("") || name.getText().toString().equals("") ||
                    phone.getText().toString().equals("") || email.getText().toString().equals("") ||
                    password.getText().toString().equals("")) {

                //We create an alertDialog to ask the user to fill all the gaps
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle(getString(R.string.warning));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.pleasefillall));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.okbutton), (dialog, which) -> {
                    alertDialog.dismiss();

                });
                alertDialog.show();

                //////



            }else {


                //This is what we do if the mail is already in the database
                userRepository.getUser(emailAdress, getApplication()).observe(LoginActivity.this, userEntity -> {
                    if(userEntity!=null){
                        Toast.makeText(this, "Email already used !", Toast.LENGTH_SHORT).show();
                    }else{
                        //Create a User if everything went well
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
                        Toast.makeText(this, "Account created !", Toast.LENGTH_SHORT).show();

                        FragmentTransaction transaction;
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.guest_layout, new LoginFragment()).commit();


                    }

                });



            }
        }else{
            if(!result)
                Toast.makeText(this, "Give a real email!", Toast.LENGTH_SHORT).show();
            else{
                Toast.makeText(this, "Password not the same !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //This method checks if the mail entered has the conventionnal form of a mail
    public final static boolean isValidEmail(EditText target1) {
        CharSequence target = target1.getText();
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    //This method checks if both password entered are the same
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

