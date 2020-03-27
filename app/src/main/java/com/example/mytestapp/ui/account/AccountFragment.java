package com.example.mytestapp.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
        final TextView textView = root.findViewById(R.id.text_gallery);


        SharedPreferences preferences = this.getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("User", "");
        User user = gson.fromJson(json, User.class);


        TextView firstname = root.findViewById(R.id.textView2);
        firstname.setText(user.getFirstName());

        TextView name = root.findViewById(R.id.textView8);
        name.setText(user.getLastName());

        TextView birthDate = root.findViewById(R.id.textView19);
        birthDate.setText(user.getBirthDate());

        TextView address = root.findViewById(R.id.textView16);
        address.setText(user.getAddress().getAddress());

        TextView email = root.findViewById(R.id.textView11);
        email.setText(user.getEmail());

        TextView password = root.findViewById(R.id.textView13);
        password.setText(user.getPassword());

        accountViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });



        return root;
    }


}
