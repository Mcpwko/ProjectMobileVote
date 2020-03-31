package com.example.mytestapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;

import java.util.List;
//The ViewModel will use the observer pattern to get the data from the database

public class HomeListViewModel extends AndroidViewModel {

    private Application application;

    private PollRepository pollRepository;
    private MeetingRepository meetingRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final LiveData<List<Meeting>> observableMeetings;
    private final LiveData<List<Poll>> observablePolls;

    public HomeListViewModel(@NonNull Application application,
                                MeetingRepository meetingRepository,
                                PollRepository pollRepository) {
        super(application);

        this.application = application;

        this.meetingRepository = meetingRepository;
        this.pollRepository = pollRepository;



        observableMeetings = meetingRepository.getActiveMeeting(application);
        observablePolls = pollRepository.getActivePolls(application);
    }

    //The Factory pattern is used to put the id into the ViewModel

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final MeetingRepository meetingRepository;

        private final PollRepository pollRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            pollRepository = getPollRepository();
            meetingRepository = getMeetingRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new HomeListViewModel(application, meetingRepository, pollRepository);
        }
    }

    //We expose the LiveData list query so that it can be observed

    public LiveData<List<Meeting>> getMeetings() {
        return observableMeetings;
    }
    public LiveData<List<Poll>> getPolls() {
        return observablePolls;
    }

    public static PollRepository getPollRepository(){ return PollRepository.getInstance(); }
    public static MeetingRepository getMeetingRepository(){ return MeetingRepository.getInstance(); }
}

