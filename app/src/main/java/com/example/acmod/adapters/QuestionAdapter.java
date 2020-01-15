package com.example.acmod.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acmod.MainActivity;
import com.example.acmod.R;
import com.example.acmod.models.question_details;
import com.example.acmod.utils.CommonUtils;
import com.example.acmod.utils.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.SearchViewHolder> {

    private ArrayList<question_details> questions;
    private Context context;
    private Activity parent;
    int final_starcount = 0;
    String final_answer;

    public QuestionAdapter(Context context, ArrayList<question_details> questions, Activity parent) {
        this.context = context;
        this.questions = questions;
        this.parent = parent;
    }

    @NonNull
    @Override
    public QuestionAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem;
        listItem = layoutInflater.inflate(R.layout.item_questions, parent, false);

        return new QuestionAdapter.SearchViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionAdapter.SearchViewHolder holder, int position) {
        final question_details model = (question_details) questions.get(position);

        holder.qnum.setText(model.getNumb());
        holder.question.setText(model.getQuestion());

        //setting initial layout invisibility
        holder.ratedLL.setVisibility(View.GONE);
        holder.answerLL.setVisibility(View.GONE);
        holder.uploadLL.setVisibility(View.GONE);

        //setting button Text
        if (model.getType().equals("1")) {
            holder.rate.setText("Answer");
        } else if (model.getType().equals("2")) {
            holder.rate.setText("Rate");
        } else if (model.getType().equals("3")) {
            holder.rate.setText("Answer");
        }

        holder.rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = parent.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_answer, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(true);

                TextView QuestionTV = dialogView.findViewById(R.id.questionTV);
                final EditText answerET = dialogView.findViewById(R.id.answerET);
                final ImageView st1 = dialogView.findViewById(R.id.st1IV);
                final ImageView st2 = dialogView.findViewById(R.id.st2IV);
                final ImageView st3 = dialogView.findViewById(R.id.st3IV);
                final ImageView st4 = dialogView.findViewById(R.id.st4IV);
                final ImageView st5 = dialogView.findViewById(R.id.st5IV);
                final Button yesBT = dialogView.findViewById(R.id.yesBT);
                final Button noBT = dialogView.findViewById(R.id.noBT);
                Button submitBT = dialogView.findViewById(R.id.answeBT);
                Button cancelBT = dialogView.findViewById(R.id.cancelBT);
                LinearLayout rateLL = dialogView.findViewById(R.id.rateLL);
                LinearLayout yesnoLL = dialogView.findViewById(R.id.yesnoLL);
                LinearLayout ansLL = dialogView.findViewById(R.id.ansLL);
                yesnoLL.setVisibility(View.GONE);
                rateLL.setVisibility(View.GONE);
                ansLL.setVisibility(View.GONE);

                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialog.show();

                //question setting
                QuestionTV.setText(model.getQuestion());

                //setting layout visibility
                if (model.getType().equals("1")) {
                    yesnoLL.setVisibility(View.VISIBLE);
                } else if (model.getType().equals("2")) {
                    rateLL.setVisibility(View.VISIBLE);
                } else if (model.getType().equals("3")) {
                    ansLL.setVisibility(View.VISIBLE);
                }

                //auto star fill setting
                st1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        st1.setImageResource(R.drawable.star);
                        st2.setImageResource(R.drawable.star_gray);
                        st3.setImageResource(R.drawable.star_gray);
                        st4.setImageResource(R.drawable.star_gray);
                        st5.setImageResource(R.drawable.star_gray);
                        final_starcount = 1;

                    }
                });
                st2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        st1.setImageResource(R.drawable.star);
                        st2.setImageResource(R.drawable.star);
                        st3.setImageResource(R.drawable.star_gray);
                        st4.setImageResource(R.drawable.star_gray);
                        st5.setImageResource(R.drawable.star_gray);
                        final_starcount = 2;

                    }
                });
                st3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        st1.setImageResource(R.drawable.star);
                        st2.setImageResource(R.drawable.star);
                        st3.setImageResource(R.drawable.star);
                        st4.setImageResource(R.drawable.star_gray);
                        st5.setImageResource(R.drawable.star_gray);
                        final_starcount = 3;

                    }
                });
                st4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        st1.setImageResource(R.drawable.star);
                        st2.setImageResource(R.drawable.star);
                        st3.setImageResource(R.drawable.star);
                        st4.setImageResource(R.drawable.star);
                        st5.setImageResource(R.drawable.star_gray);
                        final_starcount = 4;

                    }
                });
                st5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        st1.setImageResource(R.drawable.star);
                        st3.setImageResource(R.drawable.star);
                        st2.setImageResource(R.drawable.star);
                        st4.setImageResource(R.drawable.star);
                        st5.setImageResource(R.drawable.star);
                        final_starcount = 5;

                    }
                });

                //yes or no button listener
                yesBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yesBT.setBackgroundResource(R.drawable.button_rounded_blue);
                        noBT.setBackgroundResource(R.drawable.button_rounded_gray);
                        final_answer = "Yes / Available";
                    }
                });
                noBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noBT.setBackgroundResource(R.drawable.button_rounded_blue);
                        yesBT.setBackgroundResource(R.drawable.button_rounded_gray);
                        final_answer = "No / NotAvailable";
                    }
                });

                //SubmitButton
                submitBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.hideKeyboard(parent);
                        if (model.getType().equals("1")) {
                            holder.answerLL.setVisibility(View.VISIBLE);
                            holder.answer.setText(final_answer);
                        } else if (model.getType().equals("2")) {
                            holder.ratedLL.setVisibility(View.VISIBLE);
                            if (final_starcount == 1) {
                                holder.star1.setImageResource(R.drawable.star);
                                holder.star2.setImageResource(R.drawable.star_gray);
                                holder.star3.setImageResource(R.drawable.star_gray);
                                holder.star4.setImageResource(R.drawable.star_gray);
                                holder.star5.setImageResource(R.drawable.star_gray);

                            } else if (final_starcount == 2) {
                                holder.star1.setImageResource(R.drawable.star);
                                holder.star2.setImageResource(R.drawable.star);
                                holder.star3.setImageResource(R.drawable.star_gray);
                                holder.star4.setImageResource(R.drawable.star_gray);
                                holder.star5.setImageResource(R.drawable.star_gray);
                            } else if (final_starcount == 3) {
                                holder.star1.setImageResource(R.drawable.star);
                                holder.star2.setImageResource(R.drawable.star);
                                holder.star3.setImageResource(R.drawable.star);
                                holder.star4.setImageResource(R.drawable.star_gray);
                                holder.star5.setImageResource(R.drawable.star_gray);
                            } else if (final_starcount == 4) {
                                holder.star1.setImageResource(R.drawable.star);
                                holder.star2.setImageResource(R.drawable.star);
                                holder.star3.setImageResource(R.drawable.star);
                                holder.star4.setImageResource(R.drawable.star);
                                holder.star5.setImageResource(R.drawable.star_gray);
                            } else if (final_starcount == 5) {
                                holder.star1.setImageResource(R.drawable.star);
                                holder.star2.setImageResource(R.drawable.star);
                                holder.star3.setImageResource(R.drawable.star);
                                holder.star4.setImageResource(R.drawable.star);
                                holder.star5.setImageResource(R.drawable.star);
                            }else if (final_starcount == 0) {
                                holder.star1.setImageResource(R.drawable.star_gray);
                                holder.star2.setImageResource(R.drawable.star_gray);
                                holder.star3.setImageResource(R.drawable.star_gray);
                                holder.star4.setImageResource(R.drawable.star_gray);
                                holder.star5.setImageResource(R.drawable.star_gray);
                            }
                        } else if (model.getType().equals("3")) {
                            holder.answerLL.setVisibility(View.VISIBLE);
                            final_answer = answerET.getText().toString().trim();
                            holder.answer.setText(final_answer);
                        }
                        alertDialog.dismiss();
                    }
                });
                cancelBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonUtils.hideKeyboard(parent);
                        alertDialog.dismiss();
                    }
                });



            }
        });


    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        public ImageView star1, star2, star3, star4, star5;
        public TextView qnum, question, answer, upload;
        public Button rate;
        public LinearLayout ratedLL, answerLL, uploadLL;

        public SearchViewHolder(View itemView) {
            super(itemView);

            star1 = itemView.findViewById(R.id.star1IV);
            star2 = itemView.findViewById(R.id.star2IV);
            star3 = itemView.findViewById(R.id.star3IV);
            star4 = itemView.findViewById(R.id.star4IV);
            star5 = itemView.findViewById(R.id.star5IV);
            qnum = itemView.findViewById(R.id.qnumTV);
            question = itemView.findViewById(R.id.quesTV);
            answer = itemView.findViewById(R.id.answerTV);
            upload = itemView.findViewById(R.id.uploadsTV);
            rate = itemView.findViewById(R.id.answerBT);
            ratedLL = itemView.findViewById(R.id.ratedLL);
            answerLL = itemView.findViewById(R.id.answerLL);
            uploadLL = itemView.findViewById(R.id.uploadsLL);

        }
    }

}
