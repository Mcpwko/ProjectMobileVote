package com.example.mytestapp.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.UserRepository;

//The ViewModel will use the observer pattern to get the data from the database

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

    //The Factory pattern is used to put the id into the ViewModel

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

    //We expose the LiveData list query so that it can be observed

    public LiveData<User> getUser() {
        return observableClient;
    }

    public static UserRepository getUserRepository(){ return UserRepository.getInstance(); }
}