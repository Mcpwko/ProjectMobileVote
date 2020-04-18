package com.example.mytestapp.ui.Poll;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytestapp.db.entities.Poll;
import com.example.mytestapp.db.entities.PossibleAnswers;
import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.db.repository.PossibleAnswersRepository;


import java.util.List;

public class PollSelectedViewModel extends AndroidViewModel {
    private Application application;
    private PollRepository pollRepository;
    private PossibleAnswersRepository possibleAnswersRepository;


    //private final LiveData<Poll> observablePoll;
    //private final LiveData<List<PossibleAnswers>> observableAnswers;

    public PollSelectedViewModel(@NonNull Application application, final int idPoll,
                                    PollRepository pollRepository, PossibleAnswersRepository possibleAnswersRepository) {
        super(application);

        this.application = application;
        this.pollRepository = pollRepository;
        this.possibleAnswersRepository = possibleAnswersRepository;



        //observablePoll = pollRepository.getPoll(idPoll,application);
        //observableAnswers = possibleAnswersRepository.getPossibleAnswersByPoll(idPoll,application);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final int idPoll;
        private final PollRepository pollRepository;
        private final PossibleAnswersRepository possibleAnswersRepository;

        public Factory(@NonNull Application application, int idPoll) {
            this.idPoll = idPoll;
            this.application = application;
            pollRepository = getPollRepository();
            possibleAnswersRepository = getPossibleAnswersRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new com.example.mytestapp.ui.Poll.PollSelectedViewModel(application,idPoll,
                    pollRepository,possibleAnswersRepository);
        }
    }


    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    /*public LiveData<Poll> getPoll() {
        return observablePoll;
    }
    public LiveData<List<PossibleAnswers>> getPossibleAnswers(){ return observableAnswers; }
*/

    public static PollRepository getPollRepository(){ return PollRepository.getInstance(); }
    public static PossibleAnswersRepository getPossibleAnswersRepository(){ return PossibleAnswersRepository.getInstance(); }
}
