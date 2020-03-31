package com.example.mytestapp.ui.addVote;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mytestapp.R;

//This class is used to give the oppportunity to the user to choose between creating a Poll or a
//Meeting
public class ChooseVoteFragment extends Fragment {

    private ChooseVoteViewModel mViewModel;

    public static ChooseVoteFragment newInstance() {
        return new ChooseVoteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        setHasOptionsMenu(true);

        if (container != null) {
            container.removeAllViews();
        }
        return inflater.inflate(R.layout.fragment_choosevote, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChooseVoteViewModel.class);
        // TODO: Use the ViewModel
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.sort);
        item.setVisible(false);
    }





}
