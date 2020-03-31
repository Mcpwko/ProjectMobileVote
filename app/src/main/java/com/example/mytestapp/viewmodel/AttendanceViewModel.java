package com.example.mytestapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytestapp.db.entities.Attendance;

import com.example.mytestapp.db.repository.AttendanceRepository;


import java.util.List;
//The ViewModel will use the observer pattern to get the data from the database
public class AttendanceViewModel extends AndroidViewModel {

    private Application application;

    private AttendanceRepository attendanceRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final LiveData<List<Attendance>> observableAttendance;

    public AttendanceViewModel(@NonNull Application application, final int idMeeting,
                             AttendanceRepository attendanceRepository) {
        super(application);

        this.application = application;

        this.attendanceRepository = attendanceRepository;
        observableAttendance = attendanceRepository.getAttendances(idMeeting,application);
    }


    //The Factory pattern is used to put the id into the ViewModel
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final AttendanceRepository attendanceRepository;
        private final int idMeeting;

        public Factory(@NonNull int idMeeting, Application application) {
            this.application = application;
            attendanceRepository = getAttendanceRepository();
            this.idMeeting = idMeeting;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new AttendanceViewModel(application, idMeeting,attendanceRepository);
        }
    }





    //We expose the LiveData list query so that it can be observed

    public LiveData<List<Attendance>> getAttendances() {
        return observableAttendance;
    }


    public static AttendanceRepository getAttendanceRepository(){ return AttendanceRepository.getInstance(); }
}
