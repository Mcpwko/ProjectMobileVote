package com.example.mytestapp.ui.Meeting;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytestapp.db.entities.Attendance;
import com.example.mytestapp.db.entities.Meeting;

import com.example.mytestapp.db.repository.AttendanceRepository;
import com.example.mytestapp.db.repository.MeetingRepository;

import com.example.mytestapp.db.repository.UserRepository;

import java.util.List;

public class MeetingSelectedViewModel extends AndroidViewModel {

    private Application application;
    private MeetingRepository meetingRepository;
    private UserRepository userRepository;
    private AttendanceRepository attendanceRepository;


    private final LiveData<Meeting> observableMeeting;
    private final LiveData<Attendance> observableAttendance;

    public MeetingSelectedViewModel(@NonNull Application application,final int idMeeting, final int idUser,
                             MeetingRepository meetingRepository,AttendanceRepository attendanceRepository,UserRepository userRepository) {
        super(application);

        this.application = application;

        this.meetingRepository = meetingRepository;
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;



        observableMeeting = meetingRepository.getMeeting(idMeeting,application);
        observableAttendance = attendanceRepository.getAttendance(idUser,idMeeting,application);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final int idMeeting;
        private final int idUser;
        private final MeetingRepository meetingRepository;
        private final AttendanceRepository attendanceRepository;
        private final UserRepository userRepository;

        public Factory(@NonNull Application application, int idMeeting,int idUser) {
            this.idMeeting = idMeeting;
            this.idUser = idUser;
            this.application = application;
            meetingRepository = getMeetingRepository();
            attendanceRepository = getAttendanceRepository();
            userRepository = getUserRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new com.example.mytestapp.ui.Meeting.MeetingSelectedViewModel(application,idMeeting,idUser,
                                                            meetingRepository,attendanceRepository,userRepository);
        }
    }


    /**
     * Expose the LiveData ClientAccounts query so the UI can observe it.
     */
    public LiveData<Meeting> getMeeting() {
        return observableMeeting;
    }
    public LiveData<Attendance> getAttendance(){ return observableAttendance; }


    public static MeetingRepository getMeetingRepository(){ return MeetingRepository.getInstance(); }
    public static AttendanceRepository getAttendanceRepository(){ return AttendanceRepository.getInstance(); }
    public static UserRepository getUserRepository(){ return UserRepository.getInstance(); }
}
