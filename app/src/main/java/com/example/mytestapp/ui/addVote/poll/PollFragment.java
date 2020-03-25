package com.example.mytestapp.ui.addVote.poll;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.mytestapp.R;

import java.util.Date;

public class PollFragment extends Fragment {

    private PollViewModel mViewModel;

    public static PollFragment newInstance() {
        return new PollFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragment_poll, container, false);
        Spinner mySpinner = root.findViewById(R.id.spinnerCategory);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.category));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        DatePicker datePicker = root.findViewById(R.id.datePoll);
        datePicker.setMinDate(new Date().getTime());

        return root;
        bite
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PollViewModel.class);
        // TODO: Use the ViewModel
    }



}
