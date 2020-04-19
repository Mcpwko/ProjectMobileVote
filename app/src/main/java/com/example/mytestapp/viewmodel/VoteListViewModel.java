package com.example.mytestapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytestapp.db.repository.VoteRepository;
//The ViewModel will use the observer pattern to get the data from the database

public class VoteListViewModel extends AndroidViewModel {

    private Application application;

    private VoteRepository voteRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    //private final LiveData<List<Vote>> observableVotes;

    public VoteListViewModel(@NonNull Application application,final int idPoll,
                             VoteRepository voteRepository) {
        super(application);

        this.application = application;

        this.voteRepository = voteRepository;
        //observableVotes = voteRepository.getVotesByPoll(idPoll,application);
    }

    //The Factory pattern is used to put the id into the ViewModel

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




    //We expose the LiveData list query so that it can be observed

    /*public LiveData<List<Vote>> getVotes() {
        return observableVotes;
    }
*/

    public static VoteRepository getVoteRepository(){ return VoteRepository.getInstance(); }
}