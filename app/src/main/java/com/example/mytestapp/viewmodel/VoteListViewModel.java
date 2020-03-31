package com.example.mytestapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.entities.Vote;
import com.example.mytestapp.db.repository.MeetingRepository;
import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.db.repository.VoteRepository;

import java.util.List;

public class VoteListViewModel extends AndroidViewModel {

    private Application application;

    private VoteRepository voteRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final LiveData<List<Vote>> observableVotes;

    public VoteListViewModel(@NonNull Application application,final int idPoll,
                             VoteRepository voteRepository) {
        super(application);

        this.application = application;

        this.voteRepository = voteRepository;
        observableVotes = voteRepository.getVotesByPoll(idPoll,application);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final VoteRepository voteRepository;
        private final int idPoll;

        public Factory(@NonNull int idPoll, Application application) {
            this.application = application;
            voteRepository = getVoteRepository();
            this.idPoll = idPoll;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VoteListViewModel(application, idPoll,voteRepository);
        }
    }




    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<List<Vote>> getVotes() {
        return observableVotes;
    }


    public static VoteRepository getVoteRepository(){ return VoteRepository.getInstance(); }
}