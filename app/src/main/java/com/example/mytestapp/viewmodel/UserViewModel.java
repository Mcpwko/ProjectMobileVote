package com.example.mytestapp.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.util.OnAsyncEventListener;

//The ViewModel will use the observer pattern to get the data from the database

public class UserViewModel extends AndroidViewModel {

    private static final String TAG = "UserViewModel";

    private UserRepository repository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<User> observableUser;

    public UserViewModel(@NonNull Application application,
                           final String userId, UserRepository userRepository) {
        super(application);

        repository = userRepository;

        observableUser = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableUser.setValue(null);

        LiveData<User> account = repository.getUser(userId);

        // observe the changes of the client entity from the database and forward them
        observableUser.addSource(account, observableUser::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String userId;

        private final UserRepository repository;

        public Factory(@NonNull Application application, String userId) {
            this.application = application;
            this.userId = userId;
            repository = getUserRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, userId, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<User> getUser() {
        return observableUser;
    }

    public void updateUser(User user, OnAsyncEventListener callback) {
        getUserRepository().update(user, callback);
    }

    public void deleteClient(User user, OnAsyncEventListener callback) {
        getUserRepository().delete(user, callback);
    }

    public static UserRepository getUserRepository(){return UserRepository.getInstance(); }
}