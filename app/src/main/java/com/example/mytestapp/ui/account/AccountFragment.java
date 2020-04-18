package com.example.mytestapp.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.mytestapp.R;
import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.viewmodel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

//This is the AccountFragment class where all its specific operations are written

public class AccountFragment extends Fragment {

    private UserRepository repository;
    private UserViewModel userViewModel;
    private User user;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        UserViewModel.Factory factory = new UserViewModel.Factory(
                getActivity().getApplication(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );

        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        //Here is explained what is the layout the class must load to display information
        root = inflater.inflate(R.layout.fragment_account, container, false);

        //Allow to create the menu and its icons
        setHasOptionsMenu(true);

        userViewModel.getUser().observe(getActivity(), accountEntity -> {
            if (accountEntity != null) {
                user = accountEntity;
                updateContent();
            }
        });


        return root;
    }


    private void updateContent() {
        if (user != null) {
            //We set all the Textviews with what is inside the Database for a specific User
            TextView firstname = root.findViewById(R.id.firstNameEditAccount);
            firstname.setText(user.getFirstName());

            TextView name = root.findViewById(R.id.lastNameEditAccount);
            name.setText(user.getLastName());

            TextView birthDate = root.findViewById(R.id.birthdateEditAccount);
            birthDate.setText(user.getBirthDate()+ "");

            TextView address = root.findViewById(R.id.addressEditAccount);
            address.setText(user.getAddress().getAddress());

            TextView email = root.findViewById(R.id.emailEditAccount);
            email.setText(user.getEmail());

            TextView password = root.findViewById(R.id.passwordEditAccount);
            password.setText(user.getPassword());

            Button button = root.findViewById(R.id.saveChangesAccount);
            button.setVisibility(View.GONE);

            Button button2 = root.findViewById(R.id.deleteAccount);
            button2.setVisibility(View.GONE);

            Spinner mySpinner = root.findViewById(R.id.spinnerCityListAccount);

            String city = user.getAddress().getCity();

            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(myAdapter);

            for(int i=0; i<myAdapter.getCount();i++){
                if(myAdapter.getItem(i).equals(city)) {
                    mySpinner.setSelection(i);
                    mySpinner.setEnabled(false);
                }

            }
        }
    }


    //This method set an item from the menu visible
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.edit);
        if(item!=null)
        item.setVisible(true);

    }





}
