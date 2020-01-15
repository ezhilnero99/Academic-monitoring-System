package com.example.acmod.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acmod.GpsActivity;
import com.example.acmod.R;
import com.example.acmod.SearchActivity;
import com.example.acmod.adapters.QuestionAdapter;
import com.example.acmod.adapters.SearchSchoolAdapter;
import com.example.acmod.models.question_details;

import java.util.ArrayList;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AcademicFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<question_details> questions = new ArrayList<>();
    ImageView backIV;


    public AcademicFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_academic, container, false);
        initUI(view);

        //1 - yes or no question
        //2 - rating questions
        //3 - other answers
        questions.add(new question_details("how many students are in a classroom?","1.","3"));
        questions.add(new question_details("Blackboard and classroom cleanliness on an overall basis ?","2.","2"));
        questions.add(new question_details("Furniture condition and maintenance?","3.","2"));
        questions.add(new question_details("How ventilated is the classroom?","4.","2"));
        questions.add(new question_details("Availability of fans and their proportion with the number of students.","5.","1"));
        questions.add(new question_details("Timetable chart in class","6.","1"));
        questions.add(new question_details("RO system for water availability or clean drinking water","7.","2"));
        questions.add(new question_details("Student and teacher attendance maintenance","8.","2"));

        recyclerView.setAdapter(new QuestionAdapter(getContext(),questions, getActivity()));

//        backIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), GpsActivity.class));
//
//            }
//        });

        return view;
    }

    /*********************************************************/


    void initUI(View view){

        recyclerView = view.findViewById(R.id.questionRV);
        backIV = view.findViewById(R.id.backIV);
        backIV.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

    }

}