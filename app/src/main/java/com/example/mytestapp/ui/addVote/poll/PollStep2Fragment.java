package com.example.mytestapp.ui.addVote.poll;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mytestapp.R;

public class PollStep2Fragment extends Fragment {

    private PollStep2ViewModel mViewModel;

    public static PollStep2Fragment newInstance() {
        return new PollStep2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        return inflater.inflate(R.layout.poll_step2_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PollStep2ViewModel.class);
        // TODO: Use the ViewModel
    }



}
