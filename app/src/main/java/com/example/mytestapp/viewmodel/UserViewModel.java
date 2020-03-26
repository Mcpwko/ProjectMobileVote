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

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<User> observableClient;

    public UserViewModel(@NonNull Application application,
                           final String email, UserRepository userRepository) {
        super(application);

        repository = userRepository;

        applicationContext = application.getApplicationContext();

        observableClient = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableClient.setValue(null);

        LiveData<User> user = repository.getUser(email, applicationContext);

        // observe the changes of the client entity from the database and forward them
        observableClient.addSource(user, observableClient::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;

        private final UserRepository repository;

        public Factory(@NonNull Application application, String email) {
            this.application = application;
            this.email = email;
            repository = UserRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, email, repository);
        }
    }

    /**
     * Expose the LiveData UserEntity query so the UI can observe it.
     */
    public LiveData<User> getUser() {
        return observableClient;
    }

    public void createUser(User user, OnAsyncEventListener callback) {
        repository.insertUser(user, callback, applicationContext);
    }

    public void updateUser(User user, OnAsyncEventListener callback) {
        repository.updateUser(user, callback, applicationContext);
    }

    public void deleteUser(User user, OnAsyncEventListener callback) {
        repository.deleteUser(user, callback, applicationContext);
    }
}