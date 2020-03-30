package com.example.mytestapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytestapp.BaseApp;
import com.example.mytestapp.MainActivity;
import com.example.mytestapp.db.entities.Meeting;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;

import java.util.List;

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

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    /*public static class Factory extends ViewModelProvider.NewInstanceFactory {

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
    }*/

    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<Meeting>> getMeetings() {
        return observableMeetings;
    }

    /**
     * Expose the LiveData AccountEntities query so the UI can observe it.
     */
    public LiveData<List<Poll>> getPolls() {
        return observablePolls;
    }

    /*public void deleteAccount(AccountEntity account, OnAsyncEventListener callback) {
        repository.delete(account, callback, application);
    }

    public void executeTransaction(final AccountEntity sender, final AccountEntity recipient,
                                   OnAsyncEventListener callback) {
        repository.transaction(sender, recipient, callback, application);
    }*/

    public PollRepository getPollRepository(){ return PollRepository.getInstance(); }
    public MeetingRepository getMeetingRepository(){ return MeetingRepository.getInstance(); }
}

