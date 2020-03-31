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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mytestapp.R;
import com.example.mytestapp.db.entities.User;
import com.google.gson.Gson;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        setHasOptionsMenu(true);
        SharedPreferences preferences = this.getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("User", "");
        User user = gson.fromJson(json, User.class);


        TextView firstname = root.findViewById(R.id.firstNameEditAccount);
        firstname.setText(user.getFirstName());

        TextView name = root.findViewById(R.id.lastNameEditAccount);
        name.setText(user.getLastName());

        TextView birthDate = root.findViewById(R.id.birthdateEditAccount);
        birthDate.setText(user.getBirthDate());

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




        return root;
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.edit);
        if(item!=null)
        item.setVisible(true);

    }





}
