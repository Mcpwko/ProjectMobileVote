package com.example.mytestapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.mytestapp.db.entities.Address1;
import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.entities.PossibleAnswers;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.AttendanceRepository;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.db.repository.PossibleAnswersRepository;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.ui.addVote.ChooseVoteFragment;
import com.example.mytestapp.ui.addVote.meeting.MeetingFragment;
import com.example.mytestapp.ui.addVote.poll.PollFragment;
import com.example.mytestapp.ui.addVote.poll.PollStep2Fragment;
import com.example.mytestapp.ui.home.HomeFragment;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.example.mytestapp.viewmodel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

//This class is considered as the main activity because it hosts basically all the fragments

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
    private MeetingRepository meetingRepository;
    private PollRepository pollRepository;
    private UserRepository userRepository;
    private AttendanceRepository attendanceRepository;
    private PossibleAnswersRepository possibleAnswersRepository;
    private boolean account;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //We create a shared preference for the answers
        SharedPreferences preferences = getSharedPreferences("Answers", 0);
        preferences.edit().remove("Answers").commit();

        userRepository = getUserRepository();
        pollRepository = getPollRepository();
        attendanceRepository = getAttendanceRepository();
        meetingRepository = getMeetingRepository();
        possibleAnswersRepository = getPossibleAnswersRepository();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.my_topics, R.id.nav_myaccount, R.id.nav_about, R.id.logout)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navController.setGraph(R.navigation.mobile_navigation);
        navController.getGraph();

        sharedPreferences = getSharedPreferences(MyPREFERENCE, Context.MODE_PRIVATE);

        // This is the menu item that contains your switch for the darkmode
        MenuItem menuItem = navigationView.getMenu().findItem(R.id.darkMode);
        aSwitch = menuItem.getActionView().findViewById(R.id.drawer_switch);


        // We use this condition to see if the menu must be set here
        if (navigationView.getCheckedItem() == navigationView.getMenu().findItem(R.id.nav_home))
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

        //if the switch is on we activate the night mode, if not we don't activate it
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


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        //We set the text present at the top of navigation drawer
        View hView = navigationView.getHeaderView(0);
        TextView navViewName = (TextView) hView.findViewById(R.id.nameAndroidNav);
        TextView navViewEmail = (TextView) hView.findViewById(R.id.emailAndroidNav);
        navViewName.setText(user.getDisplayName());
        navViewEmail.setText(user.getEmail());

        navViewName.setTextColor(Color.WHITE);
        navViewEmail.setTextColor(Color.WHITE);



        //This part concerns the implementation of the profil picture


        ImageButton button = hView.findViewById(R.id.imgAndroidBtnNav);


        SharedPreferences sp = getSharedPreferences("profilpic", Context.MODE_PRIVATE);

        try {
            //We parse the image in order to apply it
            Uri imageUri = Uri.parse(sp.getString("profilpic", ""));

            //if what we parsed is not null we can apply it
            if (imageUri != null) {
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                selectedImage = getResizedBitmap(selectedImage, 300);// 400 is for example, replace with desired size

                ImageView profilpic = hView.findViewById(R.id.imgAndroidBtnNav);
                profilpic.setImageBitmap(selectedImage);
            }

        } catch (IllegalArgumentException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //We also created a permission to ask the user if he lett the app communicate with the gallery
        //of his system
        button.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    pickImageFromGallery();


                }
            } else {
                pickImageFromGallery();
            }
        });



    }

    public UserRepository getUserRepository() {
        return UserRepository.getInstance();
    }

    public MeetingRepository getMeetingRepository() { return MeetingRepository.getInstance();}

    public PossibleAnswersRepository getPossibleAnswersRepository(){ return PossibleAnswersRepository.getInstance();}

    public void Logout(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    //This method is used to resize the picture from the gallery
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    //The method is used to pick an image from the gallery
    private void pickImageFromGallery(){

        Intent intent = new Intent (Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.addAnswerBtnStep2Poll);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            try {
                Uri imageUri = data.getData();

                //We store the picture with a sharedPreference in order to save it
                SharedPreferences sp = getSharedPreferences("profilpic",Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = sp.edit();

                edt.putString("profilpic", imageUri.toString());
                edt.commit();


                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                //Size of the picture set to 300
                selectedImage = getResizedBitmap(selectedImage, 300);// 300 is for example, replace with desired size

                imageView.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    //Grant or deny the permission method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }




    public PollRepository getPollRepository() {
        return PollRepository.getInstance();
    }

    public AttendanceRepository getAttendanceRepository(){
        return AttendanceRepository.getInstance();
    }

    //Block the back button of the smartphone
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this,"There is no back action",Toast.LENGTH_LONG).show();
        return;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.sort);
        menuItem.setVisible(false);
        MenuItem menuItem4 = menu.findItem(R.id.edit);
        menuItem4.setVisible(false);
        MenuItem menuItem2 = menu.findItem(R.id.action_about);
        menuItem2.setVisible(false);
        return true;
    }



    //Manages the action of every item in the menu
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction transaction; /////INUTILEEEEE
        transaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.sort:
                break;
            case R.id.edit:
                findViewById(R.id.saveChangesAccount).setVisibility(View.VISIBLE);
                findViewById(R.id.deleteAccount).setVisibility(View.VISIBLE);
                EditText mail = findViewById(R.id.emailEditAccount);
                mail.setEnabled(true);
                mail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                EditText lastName = findViewById(R.id.lastNameEditAccount);
                lastName.setEnabled(true);

                EditText firstName = findViewById(R.id.firstNameEditAccount);
                firstName.setEnabled(true);

                EditText password = findViewById(R.id.passwordEditAccount);
                password.setEnabled(true);

                EditText address = findViewById(R.id.addressEditAccount);
                address.setEnabled(true);

                EditText birthdate = findViewById(R.id.birthdateEditAccount);
                birthdate.setEnabled(true);

                Spinner spinner = findViewById(R.id.spinnerCityListAccount);
                spinner.setEnabled(true);

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


    //This method is used to save the updated account
    public void saveSettings(View view){


        findViewById(R.id.saveChangesAccount).setVisibility(View.GONE);
        findViewById(R.id.deleteAccount).setVisibility(View.GONE);
        User user = new User();

        EditText mail = findViewById(R.id.emailEditAccount);
        mail.setEnabled(false);
        user.setEmail(mail.getText().toString());

        EditText lastName = findViewById(R.id.lastNameEditAccount);
        lastName.setEnabled(false);
        user.setLastName(lastName.getText().toString());

        EditText firstName = findViewById(R.id.firstNameEditAccount);
        firstName.setEnabled(false);
        user.setFirstName(firstName.getText().toString());

        EditText password = findViewById(R.id.passwordEditAccount);
        password.setEnabled(false);
        user.setPassword(password.getText().toString());

        EditText address = findViewById(R.id.addressEditAccount);
        address.setEnabled(false);
        Spinner spinner = findViewById(R.id.spinnerCityListAccount);
        spinner.setEnabled(false);

        user.setAddress(new Address1(address.getText().toString(),spinner.getSelectedItem().toString()));

        EditText birthdate = findViewById(R.id.birthdateEditAccount);
        birthdate.setEnabled(false);

        FirebaseUser usera = FirebaseAuth.getInstance().getCurrentUser();

        user.setUid(usera.getUid());


        //Then we can update the user

        UserViewModel.Factory factory = new UserViewModel.Factory(
                getApplication(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        UserViewModel viewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);

        viewModel.updateUser(user, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "update: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "update: failure", e);
            }
        });


    }

    //the method is used to delete an account from the database
    public void deleteAccount(View view){


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                            DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference()
                                    .child("users").child(user.getUid());
                            mPostReference.removeValue();
                        }
                    }
                });

        Intent newAct = new Intent(this, LoginActivity.class);
        startActivity(newAct);
    }




    



    //This method handles the navigation between fragments
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Used to save the night mode State even if we close the app
    private void saveNightModeState(boolean nightMode) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);

        editor.apply();

    }

    //Check if the night mode is activated
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

    //method used by a button to add a vote
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


    //method used by a button to go to the next step of the creation of polls
    public void nextStep(View view){



        Poll poll = new Poll();

        //We set all the textviews by the new data

        EditText title = (EditText) findViewById(R.id.titlePoll);
        poll.setTitlePoll(title.getText().toString());

        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerCategoryPoll);
        poll.setCategoryPoll(mySpinner.getSelectedItem().toString());

        EditText description = (EditText) findViewById(R.id.descriptionPoll);
        poll.setDescPoll(description.getText().toString());

        DatePicker date = (DatePicker) findViewById(R.id.datePoll);
        Date dateInfo = getDateFromDatePicker(date);

        poll.setDeadlinePoll(dateInfo);

        //If something is empty we display an AlertDialog
        if(title.getText().toString().equals("") || description.getText().toString().equals("")){

            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.warning));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.pleasefillall));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.okbutton), (dialog, which) -> {
                alertDialog.dismiss();

            });
            alertDialog.show();
        }else{



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
    }

    //Method used to change the fragment if we click on a button
    public void selectPoll(View view){
        Toast.makeText(this, "Poll selected !", Toast.LENGTH_SHORT).show();
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home, new PollFragment()).commit();
    }


    //Method used to change the fragment if we click on a button
    public void selectMeeting(View view){
        Toast.makeText(this, "Meeting selected !", Toast.LENGTH_SHORT).show();
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home, new MeetingFragment()).commit();
    }
    //Method used to add an answer to a poll
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

    //Method used to validate the creation of the poll
    public void donePoll(View view){


        answersSharedPreferences = getSharedPreferences("Answers", Context.MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = answersSharedPreferences.getString("Answers", "");


        //We save the list of answers
        List<Integer> listAnswers = gson2.fromJson(json2, new TypeToken<ArrayList<Integer>>(){}.getType());
        if(listAnswers==null)
            listAnswers = new ArrayList<Integer>();

        listAnswers.add(R.id.answer1Step2Poll);
        listAnswers.add(R.id.answer2Step2Poll);
        EditText answer1 = (EditText) findViewById(R.id.answer1Step2Poll);
        String a1 = answer1.getText().toString();
        EditText answer2 = (EditText) findViewById(R.id.answer2Step2Poll);
        String a2 = answer2.getText().toString();

        //If one of the answer is empty we display an AlertDialog
        if(a1.equals("") ||a2.equals("")){

            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.warning));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.pleasegiveanswers));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.okbutton), (dialog, which) -> {
                alertDialog.dismiss();

            });
            alertDialog.show();

        }else

        {
            //Otherwise we can set our data
            List<String> answers = new ArrayList<String>();

            for (int i = 0; i < listAnswers.size(); i++) {
                EditText answer = (EditText) findViewById(listAnswers.get(i));
                String test = answer.getText().toString();
                answers.add(answer.getText().toString());
            }

            answers.remove("");

            LinkedHashSet<String> hashSet = new LinkedHashSet<>(answers);

            ArrayList<String> listWithoutDuplicates = new ArrayList<>(hashSet);


            pollSharedPreferences = getSharedPreferences("Poll", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = pollSharedPreferences.getString("Poll", "");
            Poll poll = gson.fromJson(json, Poll.class);


            poll.setType("Poll");


            FirebaseUser actual = FirebaseAuth.getInstance().getCurrentUser();
            poll.setUser_id(actual.getUid());

            OnAsyncEventListener callback = new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onFailure(Exception e) {
                }
            };
            String idPoll = pollRepository.insert(poll, callback);


            //We get the last poll in the database and we put the data into a listwithout duplicates




                for (int i = 0; i < listWithoutDuplicates.size(); i++) {
                    PossibleAnswers possibleAnswers = new PossibleAnswers();
                    String test = listWithoutDuplicates.get(i);

                    possibleAnswers.setAnswer(test);
                    possibleAnswers.setPollid(idPoll);

                    possibleAnswersRepository.insert(possibleAnswers,callback);


                }

            //We change the fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
//Basically the same method as donePoll. It is actioned when the button doneMeeting is pressed
    public void doneMeeting(View view){



        Meeting meeting = new Meeting();


        EditText title = (EditText) findViewById(R.id.subjectMeeting);
        meeting.setTitleMeeting(title.getText().toString());

        DatePicker date = (DatePicker) findViewById(R.id.dateMeeting);
        Date date2 = getDateFromDatePicker(date);
        meeting.setDayMeeting(date2);

        TimePicker hour = (TimePicker) findViewById(R.id.timeMeeting);
        int a =hour.getCurrentHour();
        int b = hour.getCurrentMinute();
        meeting.setTimeMeeting(a +":"+ b);

        EditText place = (EditText) findViewById(R.id.placeMeeting);
        meeting.setPlaceMeeting(place.getText().toString());

        EditText description = (EditText) findViewById(R.id.descriptionMeeting);
        meeting.setDescMeeting(description.getText().toString());

        meeting.setType("Meeting");

        if(title.getText().toString().equals("") || place.getText().toString().equals("") || description.getText().toString().equals("")){


            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getString(R.string.warning));
            alertDialog.setCancelable(false);
            alertDialog.setMessage(getString(R.string.pleasefillall));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.okbutton), (dialog, which) -> {
                alertDialog.dismiss();

            });
            alertDialog.show();

        }else{


        FirebaseUser actual = FirebaseAuth.getInstance().getCurrentUser();
        meeting.setUser_id(actual.getUid());


            Intent intent = new Intent(this, MainActivity.class);
            OnAsyncEventListener callback = new OnAsyncEventListener() {
                @Override
                public void onSuccess() {

                    startActivity(intent);
                }

                @Override
                public void onFailure(Exception e) {

                }
            };
            meetingRepository.insert(meeting, callback);








        }
    }




    //Method launched when the user accept a meeting suggested
    public void acceptMeeting(View view){
        Toast.makeText(this, "Meeting accepted !", Toast.LENGTH_SHORT).show();
        FirebaseUser actual = FirebaseAuth.getInstance().getCurrentUser();

        String idMeeting = PreferenceManager.getDefaultSharedPreferences(this).getString("MeetingId", "NotFound");

        Attendance attendance = new Attendance();
        attendance.setAnswerAttendance(true);
        attendance.setUser_id(actual.getUid());
        attendance.setMeeting_id(idMeeting);


        Intent intent = new Intent(this, MainActivity.class);
        OnAsyncEventListener callback = new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

                startActivity(intent);
                finish();


                FragmentTransaction transaction;
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.home, new HomeFragment()).commit();
            }

            @Override
            public void onFailure(Exception e) {
            }
        };

        attendanceRepository.insertAttendance(attendance,callback);


    }


    //Method launched when the user refuse a meeting suggested
    public void refuseMeeting(View view){
        Toast.makeText(this, "Meeting refused !", Toast.LENGTH_SHORT).show();
        FirebaseUser actual = FirebaseAuth.getInstance().getCurrentUser();
        String idMeeting = PreferenceManager.getDefaultSharedPreferences(this).getString("MeetingId", "NotFound");


        Attendance attendance = new Attendance();
        attendance.setAnswerAttendance(false);
        attendance.setUser_id(actual.getUid());
        attendance.setMeeting_id(idMeeting);
        //We create an attendance anyway

        Intent intent = new Intent(this, MainActivity.class);
        OnAsyncEventListener callback = new OnAsyncEventListener() {
            @Override
            public void onSuccess() {

                startActivity(intent);
                finish();


                FragmentTransaction transaction;
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.home, new HomeFragment()).commit();
            }

            @Override
            public void onFailure(Exception e) {
            }
        };

        attendanceRepository.insertAttendance(attendance,callback);


    }



//Filter button action
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
