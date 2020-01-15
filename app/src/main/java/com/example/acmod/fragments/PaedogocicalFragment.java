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
import com.example.acmod.adapters.QuestionAdapter;
import com.example.acmod.models.question_details;

import java.util.ArrayList;

public class PaedogocicalFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<question_details> questions = new ArrayList<>();
    ImageView backIV;

    public PaedogocicalFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_padegocical, container, false);

        initUI(view);

        //1 - yes or no question
        //2 - rating questions
        //3 - other answers
        questions.add(new question_details("Digital classrooms or ICT labs or virtual classrooms- Availability and maintenance","1.","2"));
        questions.add(new question_details("Remedial teaching for slow learners and children with special needs","2.","2"));
        questions.add(new question_details("Assessments conducted on schedule","3.","1"));
        questions.add(new question_details("School functioning as per timetable","4.","1"));
        questions.add(new question_details("Projects given to children.","5.","1"));
        questions.add(new question_details("Projects given to children and their active participation","6.","2"));
        questions.add(new question_details("Ability of students to write using concept cartoons to ensure conceptual understanding","7.","2"));
        questions.add(new question_details("Engaging students in group activities","8.","2"));
        questions.add(new question_details("Parent teaching meetings organised ","9.","1"));
        questions.add(new question_details("NCC, Scouts, RSP and JRC clubs in school","10.","2"));
        questions.add(new question_details("Spoken english and foreign language coaching in school","11.","2"));
        questions.add(new question_details("Book review for secondary and HS and story telling for primary","12.","2"));

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