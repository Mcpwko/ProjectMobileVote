package com.example.mytestapp.ui.addVote.meeting;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.mytestapp.R;

import java.util.Date;

public class MeetingFragment extends Fragment {

    private MeetingViewModel mViewModel;

    public static MeetingFragment newInstance() {
        return new MeetingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_meeting, container, false);

        DatePicker datePicker = root.findViewById(R.id.dateMeeting);
        datePicker.setMinDate(new Date().getTime());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MeetingViewModel.class);
        // TODO: Use the ViewModel
    }

}
