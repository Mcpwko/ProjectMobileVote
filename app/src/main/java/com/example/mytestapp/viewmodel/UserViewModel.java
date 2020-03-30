package com.example.mytestapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.util.OnAsyncEventListener;

public class UserViewModel extends AndroidViewModel {

    private UserRepository repository;
    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final LiveData<User> observableClient;

    public UserViewModel(@NonNull Application application,
                           final int idUser, UserRepository userRepository) {
        super(application);

        repository = userRepository;

        this.application = application;


        observableClient = repository.getUserById(idUser, application);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int idUser;

        private final UserRepository repository;

        public Factory(@NonNull Application application, int idUser) {
            this.application = application;
            this.idUser = idUser;
            repository = getUserRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, idUser, repository);
        }
    }

    /**
     * Expose the LiveData UserEntity query so the UI can observe it.
     */
    public LiveData<User> getUser() {
        return observableClient;
    }

    public static UserRepository getUserRepository(){ return UserRepository.getInstance(); }
}