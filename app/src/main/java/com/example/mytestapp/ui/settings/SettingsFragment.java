package com.example.mytestapp.ui.settings;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytestapp.MainActivity;
import com.example.mytestapp.R;
import com.example.mytestapp.ui.home.HomeViewModel;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    public static final int IMAGE_PICK_CODE = 1000;
    public static final int PERMISSION_CODE = 1001;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        Spinner mySpinner = root.findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.language));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        return root;

    }
}

