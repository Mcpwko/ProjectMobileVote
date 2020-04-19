package com.example.mytestapp.db.repository;

//The class is used to transfer data from DAO to ViewModel

public class PossibleAnswersRepository {

    private static PossibleAnswersRepository instance;

    private PossibleAnswersRepository() {}

    public static PossibleAnswersRepository getInstance() {
        if (instance == null) {
            synchronized (PossibleAnswersRepository.class) {
                if (instance == null) {
                    instance = new PossibleAnswersRepository();
                }
            }
        }
        return instance;
    }
    //The methods below are used to get datas from the DAO

    //A voir comment faire
    /*public LiveData<List<PossibleAnswers>> getPossibleAnswersByPoll (int id, Context context) {
        return AppDatabase.getInstance(context).possibleAnswerDao().getPossibleAnswersByPoll(id);
    }*/



}
