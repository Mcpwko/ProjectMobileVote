package com.example.mytestapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.example.mytestapp.db.repository.VoteRepository;
//The ViewModel will use the observer pattern to get the data from the database

public class VoteViewModel extends AndroidViewModel {

    private VoteRepository voterepository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
   // private final LiveData<List<Vote>> observableVotes;

    public VoteViewModel(@NonNull Application application,
                         final int idUser,final int idPoll, VoteRepository voterepository) {
        super(application);

        this.voterepository = voterepository;

        this.application = application;


        //observableVotes = voterepository.getVote(idUser,idPoll, application);
    }

    //The Factory pattern is used to put the id into the ViewModel

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int idUser;
        private final int idPoll;

        private final VoteRepository voteRepository;

        public Factory(@NonNull Application application, int idUser,int idPoll) {
            this.application = application;
            this.idUser = idUser;
            this.idPoll = idPoll;
            voteRepository = getVoteRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new VoteViewModel(application, idUser,idPoll, voteRepository);
        }
    }

    //We expose the LiveData list query so that it can be observed

    /*public LiveData<List<Vote>> getVotes() {
        return observableVotes;
    }*/

    public static VoteRepository getVoteRepository(){ return VoteRepository.getInstance(); }
}
