package com.example.mytestapp.ui.Poll;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytestapp.MainActivity;
import com.example.mytestapp.R;

import com.example.mytestapp.db.entities.User;
import com.example.mytestapp.db.entities.Vote;

import com.example.mytestapp.db.repository.PollRepository;
import com.example.mytestapp.db.repository.PossibleAnswersRepository;
import com.example.mytestapp.db.repository.UserRepository;
import com.example.mytestapp.db.repository.VoteRepository;

import com.example.mytestapp.ui.home.HomeFragment;
import com.example.mytestapp.util.OnAsyncEventListener;
import com.example.mytestapp.viewmodel.UserViewModel;
import com.example.mytestapp.viewmodel.VoteViewModel;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//We use PollSelectedFragment when we select a Poll from the HomeFragment
public class PollSelectedFragment extends Fragment {

    private PollSelectedViewModel mViewModel;
    private UserViewModel userViewModel;
    private VoteViewModel voteViewModel;
    private int idPoll;
    private PollRepository pollRep;
    private UserRepository userRep;
    private VoteRepository voteRep;
    private PossibleAnswersRepository possibleAnsRep;
    private static final String TAG = "PollSelectedFragment";
    public PollSelectedFragment(int idPoll){
        this.idPoll = idPoll;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }
        View root = inflater.inflate(R.layout.fragment_selected_poll, container, false);
        setHasOptionsMenu(true);

        SharedPreferences preferences = getActivity().getSharedPreferences("User", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("User", "");
        User actualUser = gson.fromJson(json, User.class);

        //The layout will be useful to store into a scrollView all the possible answers
        LinearLayout linearLayout = root.findViewById(R.id.linearLayoutAnswersPollSelected);
        Button validate = root.findViewById(R.id.validateAnswers);
        Button editPoll = root.findViewById(R.id.editPoll);
        Button deletePoll = root.findViewById(R.id.deleteAnswers);
        Button updatePoll = root.findViewById(R.id.updatePoll);

        PollSelectedViewModel.Factory factory = new PollSelectedViewModel.Factory(
                getActivity().getApplication(),idPoll);

        mViewModel = ViewModelProviders.of(this,factory).get(PollSelectedViewModel.class);

        //This block sets the text elements created in the layout with the data in the database

        /*mViewModel.getPoll().observe(getActivity(),poll -> {
            if(this.isVisible()) {
                UserViewModel.Factory factoryUser = new UserViewModel.Factory(getActivity().getApplication(), poll.getUser_id());
                userViewModel = ViewModelProviders.of(this, factoryUser).get(UserViewModel.class);
                userViewModel.getUser().observe(getActivity(), user -> {
                    TextView name = root.findViewById(R.id.userCreatorPoll);
                    name.setText(user.getLastName() + " " + user.getFirstName());

                });
            }

            TextView title = root.findViewById(R.id.titlePollSelected);
            title.setText(poll.getTitlePoll());

            TextView day = root.findViewById(R.id.deadlinePollSelected);
            Date deadlinePoll = poll.getDeadlinePoll();
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            day.setText(df.format(deadlinePoll));

            TextView category = root.findViewById(R.id.categoryPollSelected);
            category.setText(poll.getCategoryPoll());

            TextView description = root.findViewById(R.id.descriptionPollSelected);
            description.setText(poll.getDescPoll());


            //We do the same as above and we get the data from the possibleAnswers table

            mViewModel.getPossibleAnswers().observe(getActivity(),possibleAnswers -> {

                VoteViewModel.Factory factoryVote = new VoteViewModel.Factory(getActivity().getApplication(),actualUser.getUid(),poll.getPid());
                voteViewModel = ViewModelProviders.of(this, factoryVote).get(VoteViewModel.class);
                if(this.isVisible())
                voteViewModel.getVotes().observe(getActivity(), votes -> {
                    //If there is more than 1 vote, we create a list
                    if(votes.size()>=1){
                        List<CheckBox> list = new ArrayList<CheckBox>();
                        //We go through this list and we declare a boolean with a false value
                        for(int i =0 ; i<possibleAnswers.size();i++) {
                            boolean exist =false;
                            //We go through the votes and if a possible answers matches with Possible
                            //Answers in the DB, we can say it exist
                            for (Vote vote : votes) {
                                if (vote.getPossaid()==possibleAnswers.get(i).getPaid()) {
                                    exist = true;
                                }
                            }

                            if(isAdded())
                            if(exist){
                                CheckBox button = new CheckBox(getActivity());
                                button.setText(possibleAnswers.get(i).getAnswer());
                                button.setId(possibleAnswers.get(i).getPaid());
                                button.setChecked(true);
                                button.setEnabled(false);
                                list.add(button);

                                linearLayout.addView(button);
                            }else{

                                CheckBox button = new CheckBox(getActivity());
                                button.setEnabled(false);
                                button.setText(possibleAnswers.get(i).getAnswer());
                                button.setId(possibleAnswers.get(i).getPaid());
                                list.add(button);

                                linearLayout.addView(button);
                            }


                            editPoll.setVisibility(View.VISIBLE);
                            validate.setVisibility(View.GONE);




                            //Action for the edit button

                            editPoll.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    //EDIT BUTTON
                                    for(CheckBox box : list){
                                        //Allow to give the permission to cross the box
                                        box.setEnabled(true);
                                    }
                                    updatePoll.setVisibility(View.VISIBLE);
                                    editPoll.setVisibility(View.GONE);
                                    deletePoll.setVisibility(View.VISIBLE);




                                }

                            });

                            //Action for the update button
                            updatePoll.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //UPDATE BUTTON
                                    for(CheckBox box : list){
                                        box.setEnabled(false);
                                    }
                                    updatePoll.setVisibility(View.GONE);
                                    editPoll.setVisibility(View.VISIBLE);
                                    deletePoll.setVisibility(View.GONE);

                                    //The toast give the message below when the answer(s) are updated
                                    Toast.makeText(getActivity(),"Answer(s) updated",Toast.LENGTH_SHORT).show();

                                    for(Vote vote : votes) {

                                        new DeleteVote(getActivity().getApplication(), new OnAsyncEventListener() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d(TAG, "createUserWithEmail: success");
                                            }

                                            @Override
                                            public void onFailure(Exception e) {
                                                Log.d(TAG, "createUserWithEmail: failure", e);
                                            }
                                        }).execute(vote);
                                    }





                                    //In this part we create a list of the answers chosen by the
                                    //user and we put the data of the list of checkboxes in it

                                    List<Integer> listChoosenAnswers = new ArrayList<Integer>();

                                    for(int i=0;i<list.size();i++){
                                        if(list.get(i).isChecked()==true){
                                            listChoosenAnswers.add(list.get(i).getId());

                                            Vote vote = new Vote();


                                            vote.setUser_id(actualUser.getUid());
                                            vote.setPossaid(list.get(i).getId());
                                            vote.setPoll_id(poll.getPid());


                                            new CreateVote(getActivity().getApplication(), new OnAsyncEventListener() {
                                                @Override
                                                public void onSuccess() {
                                                    Log.d(TAG, "createUserWithEmail: success");
                                                }

                                                @Override
                                                public void onFailure(Exception e) {
                                                    Log.d(TAG, "createUserWithEmail: failure", e);
                                                }
                                            }).execute(vote);



                                        }
                                    }

                                    FragmentTransaction transaction;
                                    transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.home, new HomeFragment()).commit();


                                }
                            });



                            //Action for the delete button
                            deletePoll.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                    alertDialog.setTitle(getString(R.string.delete));
                                    alertDialog.setCancelable(false);
                                    alertDialog.setMessage(getString(R.string.deleteAttedance));
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yesmeeting), (dialog, which) -> {

                                        Toast.makeText(getActivity(),"Answers succesfully deleted !",Toast.LENGTH_SHORT).show();

                                        //We delete each votes
                                        for(Vote vote : votes) {

                                            new DeleteVote(getActivity().getApplication(), new OnAsyncEventListener() {
                                                @Override
                                                public void onSuccess() {
                                                    Log.d(TAG, "createUserWithEmail: success");
                                                }

                                                @Override
                                                public void onFailure(Exception e) {
                                                    Log.d(TAG, "createUserWithEmail: failure", e);
                                                }
                                            }).execute(vote);
                                        }
                                        updatePoll.setVisibility(View.GONE);
                                        editPoll.setVisibility(View.GONE);
                                        validate.setVisibility(View.VISIBLE);
                                        deletePoll.setVisibility(View.GONE);

                                        for(CheckBox box : list){
                                            box.setEnabled(true);
                                            box.setChecked(false);
                                        }

                                        Activity a = getActivity();
                                        Intent intent = new Intent(a, MainActivity.class);
                                        startActivity(intent);
                                        a.finish();





                                    });
                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.Nobtn), (dialog, which) -> alertDialog.dismiss());
                                    alertDialog.show();

                                }
                            });

                        }
                    //If there is less than 1 vote we create a list of checkbox where every possible
                    //answers are put
                    }else{

                        List<CheckBox> list = new ArrayList<CheckBox>();

                        for(int i =0 ; i<possibleAnswers.size();i++) {
                            if (isAdded()) {
                                CheckBox button = new CheckBox(getActivity());

                                button.setText(possibleAnswers.get(i).getAnswer());
                                button.setId(possibleAnswers.get(i).getPaid());
                                list.add(button);
                                linearLayout.addView(button);
                            }
                        }


                        //If we click on the validate button we save all the data
                        validate.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {



                                List<Integer> listChoosenAnswers = new ArrayList<Integer>();

                                for(int i=0;i<list.size();i++){
                                    if(list.get(i).isChecked()==true){
                                        listChoosenAnswers.add(list.get(i).getId());

                                        Vote vote = new Vote();


                                        vote.setUser_id(actualUser.getUid());
                                        vote.setPossaid(list.get(i).getId());
                                        vote.setPoll_id(poll.getPid());


                                        new CreateVote(getActivity().getApplication(), new OnAsyncEventListener() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d(TAG, "createUserWithEmail: success");
                                            }

                                            @Override
                                            public void onFailure(Exception e) {
                                                Log.d(TAG, "createUserWithEmail: failure", e);
                                            }
                                        }).execute(vote);



                                    }
                                }

                                //We finally get back to the main activity
                                Activity a = getActivity();
                                Intent intent = new Intent(a, MainActivity.class);
                                startActivity(intent);
                                a.finish();


                                //FragmentTransaction transaction;
                                //transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                //transaction.replace(R.id.home, new HomeFragment()).commit();
                            }

                        });
                    }
                });

            });

        });
*/



        return root;
    }


    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        pollRep = getPollRepository();
        userRep = getUserRepository();
        possibleAnsRep = getPossibleAnswersRepository();
        voteRep = getVoteRepository();

        if(this.isVisible()){
        pollRep.getPoll(id,getActivity().getApplication()).observe(getActivity(), poll -> {

            TextView title = view.findViewById(R.id.titlePollSelected);
            title.setText(poll.getTitlePoll());

            TextView day = view.findViewById(R.id.deadlinePollSelected);
            Date deadlinePoll = poll.getDeadlinePoll();
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            day.setText(df.format(deadlinePoll));

            TextView category = view.findViewById(R.id.categoryPollSelected);
            category.setText(poll.getCategoryPoll());

            TextView description = view.findViewById(R.id.descriptionPollSelected);
            description.setText(poll.getDescPoll());


            userRep.getUserById(poll.getUser_id(),getActivity().getApplication()).observe(getActivity(),user -> {

                TextView name = view.findViewById(R.id.userCreatorPoll);
                name.setText(user.getLastName() + " " + user.getFirstName());

            });


            SharedPreferences preferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = preferences.getString("User", "");
            User usertest = gson.fromJson(json, User.class);
            LinearLayout linearLayout = view.findViewById(R.id.linearLayoutAnswersPollSelected);

            voteRep.getVote(usertest.getUid(),poll.getPid(),getActivity().getApplication()).observe(getActivity(), vote ->{
                   linearLayout.removeAllViews();

                   possibleAnsRep.getPossibleAnswersByPoll(poll.getPid(),getActivity().getApplication()).observe(getActivity(), possibleAnswers -> {
                       if(vote.size()>=1){
                           List<TextView> list = new ArrayList<TextView>();
                           for(int i =0 ; i<possibleAnswers.size();i++) {
                               boolean exist =false;
                               for (Vote customer : vote) {
                                   if (customer.getPossaid()==possibleAnswers.get(i).getPaid()) {
                                       exist = true;
                                   }
                               }


                               if(exist){
                                   TextView button = new TextView(getActivity());
                                   button.setText(possibleAnswers.get(i).getAnswer());
                                   button.setId(possibleAnswers.get(i).getPaid());
                                   button.setTextColor(getResources().getColor(R.color.TopicsHome));
                                   list.add(button);

                                   linearLayout.addView(button);
                               }else{

                                   TextView button = new TextView(getActivity());
                                   button.setText(possibleAnswers.get(i).getAnswer());
                                   button.setId(possibleAnswers.get(i).getPaid());
                                   list.add(button);

                                   linearLayout.addView(button);
                               }

                           }

                       }else{

                           List<CheckBox> list = new ArrayList<CheckBox>();

                           for(int i =0 ; i<possibleAnswers.size();i++) {

                               CheckBox button = new CheckBox(getActivity());
                               button.setText(possibleAnswers.get(i).getAnswer());
                               button.setId(possibleAnswers.get(i).getPaid());
                               list.add(button);
                               linearLayout.addView(button);
                           }

                           Button validate = view.findViewById(R.id.validateAnswers);

                           validate.setOnClickListener(new View.OnClickListener() {
                               public void onClick(View v) {

                                   List<Integer> listChoosenAnswers = new ArrayList<Integer>();

                                   for(int i=0;i<list.size();i++){
                                       if(list.get(i).isChecked()==true){
                                           listChoosenAnswers.add(list.get(i).getId());

                                           Vote vote = new Vote();

                                           SharedPreferences preferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
                                           Gson gson = new Gson();
                                           String json = preferences.getString("User", "");
                                           User user = gson.fromJson(json, User.class);


                                           vote.setUser_id(user.getUid());
                                           vote.setPossaid(list.get(i).getId());
                                           vote.setPoll_id(poll.getPid());


                                           new CreateVote(getActivity().getApplication(), new OnAsyncEventListener() {
                                               @Override
                                               public void onSuccess() {
                                                   Log.d(TAG, "createUserWithEmail: success");
                                               }

                                               @Override
                                               public void onFailure(Exception e) {
                                                   Log.d(TAG, "createUserWithEmail: failure", e);
                                               }
                                           }).execute(vote);



                                       }
                                   }
                                    Activity a = getActivity();
                                   Intent intent = new Intent(a, MainActivity.class);
                                   startActivity(intent);
                                   a.finish();


                                   //FragmentTransaction transaction;
                                   //transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                   //transaction.replace(R.id.home, new HomeFragment()).commit();
                               }

                           });
                       }




                   });
               });
            });}

    }*/

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.sort);
        item.setVisible(false);
    }
}
