package com.hearatale.bw2000.ui.quiz.answer_letter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hearatale.bw2000.R;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.QuestionModel;
import com.hearatale.bw2000.ui.base.BaseFragment;
import com.hearatale.bw2000.util.Helper;
import com.hearatale.bw2000.util.glide.GlideApp;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerFragment extends BaseFragment implements AnswerMvpView {

    private static String ARG_QUESTION = "ARG_QUESTION";
    private QuestionModel questionModel;
    private boolean isEnableClick = false;
    private ChooseAnswerListener mListener;
    private int playCount = 0;
    private long TIME_DELAY = 1000;
    private String ROOT_URI = "file:///android_asset/";

    @BindView(R.id.text_question)
    TextView mQuestionText;

    @BindView(R.id.image_anwser1)
    ImageButton mChooseAnswer1;

    @BindView(R.id.image_anwser2)
    ImageButton mChooseAnswer2;

    @BindView(R.id.image_anwser3)
    ImageButton mChooseAnswer3;

    @BindView(R.id.image_anwser4)
    ImageButton mChooseAnswer4;

    @BindView(R.id.constraintLayout)
    ConstraintLayout layoutAnswers;


    public AnswerFragment() {
        // Required empty public constructor
    }


    public static AnswerFragment newInstance(QuestionModel questionModel) {
        AnswerFragment fragment = new AnswerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION, questionModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionModel = getArguments().getParcelable(ARG_QUESTION);
        }

        //delay for finish setup animation ==> enable click
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isEnableClick = true;
            }
        }, TIME_DELAY);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_answer;
    }

    @Override
    protected void initViews() {
        createAnswers();
        //updateScore playCount ==> QuizActivity
        mListener.playCount(this.playCount);
    }

    //create layout answer
    private void createAnswers() {
        if (questionModel != null) {
            mQuestionText.setText(questionModel.getQuestion());
            GlideApp.with(this)
                    .load(Uri.parse(ROOT_URI + questionModel.getData().get(0).getImageUri()))
                    .into(mChooseAnswer1);
            GlideApp.with(this)
                    .load(Uri.parse(ROOT_URI + questionModel.getData().get(1).getImageUri()))
                    .into(mChooseAnswer2);
            GlideApp.with(this)
                    .load(Uri.parse(ROOT_URI + questionModel.getData().get(2).getImageUri()))
                    .into(mChooseAnswer3);
            GlideApp.with(this)
                    .load(Uri.parse(ROOT_URI + questionModel.getData().get(3).getImageUri()))
                    .into(mChooseAnswer4);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void checkAnswer(int numberAnswer, View view) {
        playCount++;
        mListener.playCount(playCount);
        if (playCount <= 2) {
            if (questionModel.getData().get(numberAnswer).isAnswer()) {
                mListener.onCorrected(questionModel.getData().get(numberAnswer));
                //animation(view);
                isEnableClick = false;
            } else {
                mListener.onInCorrected();
            }
        } else {
            mListener.onInCorrected();
        }
    }

    @OnClick(R.id.image_anwser1)
    void chooseAnswer1() {
        if (isEnableClick && playCount <= 2) {
            checkAnswer(0, mChooseAnswer1);
        }
    }

    @OnClick(R.id.image_anwser2)
    void chooseAnswer2() {
        if (isEnableClick && playCount <= 2) {
            checkAnswer(1, mChooseAnswer2);
        }
    }

    @OnClick(R.id.image_anwser3)
    void chooseAnswer3() {
        if (isEnableClick && playCount <= 2) {
            checkAnswer(2, mChooseAnswer3);
        }
    }

    @OnClick(R.id.image_anwser4)
    void chooseAnswer4() {
        if (isEnableClick && playCount <= 2) {
            checkAnswer(3, mChooseAnswer4);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (ChooseAnswerListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnListItemSelectedListener");
        }
    }

    private void animation(View view) {
        int positionXCorrectAnswers = Helper.getRelativeLeft(view) + view.getWidth() / 2;
        int positionYCorrectAnswers = Helper.getRelativeTop(view) + view.getHeight() / 2;
        //mListener.animateCoinFlyToBag(positionXCorrectAnswers, positionYCorrectAnswers);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mListener = null;
    }

    public interface ChooseAnswerListener {

        void onCorrected(Item item);

        void onInCorrected();

        void playCount(int playCount);

        //void animateCoinFlyToBag(int positionXCorrect, int positionYCorrect);
    }
}
