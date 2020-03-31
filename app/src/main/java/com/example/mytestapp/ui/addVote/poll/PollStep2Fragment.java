package com.example.mytestapp.ui.addVote.poll;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mytestapp.R;

//This is the PollStep2Fragment class where all its specific operations are written
//The creation of a Poll is actually divided into 2 steps
//Here you will find the information about the second step of the creation
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
