package com.example.mytestapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mytestapp.db.async.CreateMeeting;
import com.example.mytestapp.db.async.CreatePoll;
import com.example.mytestapp.db.async.CreatePossibleAnswer;
import com.example.mytestapp.db.async.CreateUser;
import com.example.mytestapp.db.async.GetLastPoll;
import com.example.mytestapp.db.entities.Address1;
import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.entities.PossibleAnswers;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.ui.about.AboutFragment;
import com.example.mytestapp.ui.addVote.ChooseVoteFragment;
import com.example.mytestapp.ui.addVote.meeting.MeetingFragment;
import com.example.mytestapp.ui.addVote.poll.PollFragment;
import com.example.mytestapp.ui.addVote.poll.PollStep2Fragment;
import com.example.mytestapp.ui.home.HomeFragment;
import com.example.mytestapp.ui.login.LoginFragment;
import com.example.mytestapp.ui.settings.SettingsFragment;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private int cpt = 2;
    private AppBarConfiguration mAppBarConfiguration;

    private static final String TAG = "MainActivity";

    public static final String MyPREFERENCE = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    SharedPreferences sharedPreferences;
    SharedPreferences pollSharedPreferences;
    SharedPreferences answersSharedPreferences;
    public boolean homepage;
    ImageView imageView;
    Button button;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private Switch aSwitch;
    private PollRepository pollRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("Answers", 0);
        preferences.edit().remove("Answers").commit();

        pollRepository = getPollRepository();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
        //ROND VOLANT EN BAS A GAUCHE DE LECRAN
        /**fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
        }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,R.id.my_topics, R.id.nav_myaccount, R.id.nav_about,R.id.nav_settings, R.id.logout)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navController.setGraph(R.navigation.mobile_navigation);
        navController.getGraph();

        sharedPreferences = getSharedPreferences(MyPREFERENCE, Context.MODE_PRIVATE);

        //aSwitch = findViewById(R.id.darkMode);

        MenuItem menuItem = navigationView.getMenu().findItem(R.id.darkMode); // This is the menu item that contains your switch
        aSwitch = menuItem.getActionView().findViewById(R.id.drawer_switch);


        if(navigationView.getCheckedItem()== navigationView.getMenu().findItem(R.id.nav_home))
            homepage = true;

        final Context context = this;

        MenuItem logout = navigationView.getMenu().findItem(R.id.logout);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent newAct = new Intent(context, LoginActivity.class);
                startActivity(newAct);
                return false;
            }
        });








        checkNightModeActivated();





        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveNightModeState(true);
                    recreate();
                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveNightModeState(false);
                    recreate();
                }
            }
        });



        SharedPreferences userpreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = userpreferences.getString("User", "");
        User user = gson2.fromJson(json2, User.class);



        View hView = navigationView.getHeaderView(0);
        TextView navViewName = (TextView)hView.findViewById(R.id.navviewName);
        TextView navViewEmail = (TextView)hView.findViewById(R.id.navviewEmail);
        navViewName.setText(user.getLastName() + " " + user.getFirstName());
        navViewEmail.setText(user.getEmail());
    }

    public void Logout(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }




    public PollRepository getPollRepository() {
        return PollRepository.getInstance();
    }

    //Block the back button of the smartphone
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        //Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container_view_tag);
        Toast.makeText(MainActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.sort);
            menuItem.setVisible(false);
        MenuItem menuItem2 = menu.findItem(R.id.action_about);
        menuItem2.setVisible(false);
        MenuItem menuItem3 = menu.findItem(R.id.action_settings);
        menuItem3.setVisible(false);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.sort:
                break;
            case R.id.all_filter:
                Toast.makeText(this,"all selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.meeting_filter:
                Toast.makeText(this,"meeting selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.poll_filter:
                Toast.makeText(this,"poll selected",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMenu(View view){
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.inflate(R.menu.filter);
        popupMenu.show();
    }


    




    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void saveNightModeState(boolean nightMode) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);

        editor.apply();

    }

    public void checkNightModeActivated() {
        if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)){
            aSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            aSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void addVote(View view){
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.home, new ChooseVoteFragment()).commit();
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


    public void nextStep(View view){

        Poll poll = new Poll();


        EditText title = (EditText) findViewById(R.id.title);
        poll.setTitlePoll(title.getText().toString());

        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerCategory);
        poll.setCategoryPoll(mySpinner.getSelectedItem().toString());

        EditText description = (EditText) findViewById(R.id.description);
        poll.setDescPoll(description.getText().toString());

        DatePicker date = (DatePicker) findViewById(R.id.datePoll);
        Date dateInfo = getDateFromDatePicker(date);

        poll.setDeadlinePoll(dateInfo);


        SharedPreferences preferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = preferences.getString("User", "");
        User user = gson2.fromJson(json2, User.class);

        poll.setUser_id(user.getUid());

        pollSharedPreferences = getApplicationContext().getSharedPreferences("Poll", MODE_PRIVATE);
        SharedPreferences.Editor edt = pollSharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(poll);
        edt.putString("Poll", json);
        edt.apply();

        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home, new PollStep2Fragment()).commit();
    }

    public void selectPoll(View view){
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home, new PollFragment()).commit();
    }



    public void selectMeeting(View view){
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home, new MeetingFragment()).commit();
    }

    public void addAnswer(View view){
        EditText answer = new EditText(this);
        cpt = cpt+1;
        answer.setHint("Answer " + cpt);
        answer.setId(cpt);

        answersSharedPreferences = getSharedPreferences("Answers", Context.MODE_PRIVATE);
        Gson gson22 = new Gson();
        String json22 = answersSharedPreferences.getString("Answers", "");
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        List<Integer> listAnswers = gson22.fromJson(json22, type);

        if(listAnswers==null)
        listAnswers = new ArrayList<Integer>();

        listAnswers.add(answer.getId());

        answersSharedPreferences = getApplicationContext().getSharedPreferences("Answers", MODE_PRIVATE);
        SharedPreferences.Editor edt = answersSharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(listAnswers);
        edt.putString("Answers", json);
        edt.apply();

        LinearLayout ll = findViewById(R.id.linearLayoutAnswers);
        ll.addView(answer);

    }


    public void donePoll(View view){


        answersSharedPreferences = getSharedPreferences("Answers", Context.MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = answersSharedPreferences.getString("Answers", "");
        List<Integer> listAnswers = gson2.fromJson(json2, new TypeToken<ArrayList<Integer>>(){}.getType());

        listAnswers.add(R.id.answer1);
        listAnswers.add(R.id.answer2);

        List<String> answers = new ArrayList<String>();

        for(int i = 0; i< listAnswers.size();i++){
            EditText answer = (EditText) findViewById(listAnswers.get(i));
            String test = answer.getText().toString();
            answers.add(answer.getText().toString());
        }


        pollSharedPreferences = getSharedPreferences("Poll", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = pollSharedPreferences.getString("Poll", "");
        Poll poll = gson.fromJson(json, Poll.class);

        EditText title = (EditText) findViewById(R.id.answer1);
        String meeting = title.getText().toString();

        poll.setStatusOpen(true);

        new CreatePoll(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createUserWithEmail: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createUserWithEmail: failure", e);
            }
        }).execute(poll);



        pollRepository.getLastPoll( getApplication()).observe(MainActivity.this, pollentity -> {
            Poll poll2 = pollentity;




            for(int i = 0; i < answers.size(); i++) {
                PossibleAnswers possibleAnswers = new PossibleAnswers();
                possibleAnswers.setAnswer(null);
                String test = answers.get(i);

                possibleAnswers.setAnswer(test);
                possibleAnswers.setPollid(poll2.getPid());



                new CreatePossibleAnswer(getApplication(), new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "createUserWithEmail: success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "createUserWithEmail: failure", e);
                    }
                }).execute(possibleAnswers);
            }
        });


        //Récupérer toutes les réponses




        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home, new HomeFragment()).commit();
    }

    public void doneMeeting(View view){


        Meeting meeting = new Meeting();


        EditText title = (EditText) findViewById(R.id.subjectmeeting);
        meeting.setTitleMeeting(title.getText().toString());

        DatePicker date = (DatePicker) findViewById(R.id.dateMeeting);
        Date date2 = getDateFromDatePicker(date);
        meeting.setDayMeeting(date2);

        TimePicker hour = (TimePicker) findViewById(R.id.timePicker);
        int a =hour.getCurrentHour();
        int b = hour.getCurrentMinute();
        meeting.setTimeMeeting(a +":"+ b);

        EditText place = (EditText) findViewById(R.id.place);
        meeting.setPlaceMeeting(place.getText().toString());

        EditText description = (EditText) findViewById(R.id.descriptionmeeting);
        meeting.setDescMeeting(description.getText().toString());

        meeting.setStatusOpen(true);

        SharedPreferences userpreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = userpreferences.getString("User", "");
        User user = gson2.fromJson(json2, User.class);



        meeting.setUser_id(user.getUid());

        new CreateMeeting(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createUserWithEmail: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createUserWithEmail: failure", e);
            }
        }).execute(meeting);



        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home, new HomeFragment()).commit();
    }

    public void apply(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.all_filter:
                Toast.makeText(this,"all selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.meeting_filter:
                Toast.makeText(this,"meeting selected",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.poll_filter:
                Toast.makeText(this,"poll selected",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}
