package com.hearatale.bw2000.ui.quiz_congrats;

import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hearatale.bw2000.R;
import com.hearatale.bw2000.ui.base.BaseActivity;
import com.hearatale.bw2000.util.glide.GlideApp;

import butterknife.BindView;
import butterknife.OnClick;

public class QuizCongratsActivity extends BaseActivity implements QuizCongratsMvpView {

    @BindView(R.id.logo)
    ImageView imageView;

    @OnClick(R.id.container)
    public void closeView(){
        finish();
    }

    @OnClick(R.id.button_close)
    public void closeViewTwo(){
        finish();
    }

    private QuizCongratsMvpPresenter<QuizCongratsMvpView> mPresenter;


    @Override
    protected int getContentView() {
        return R.layout.activity_quiz_congrats;
    }

    @Override
    protected void initViews() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        GlideApp.with(this)
                .load(R.drawable.brainy_words)
                .override(width,height)
                .into(imageView);
        mPresenter = new QuizCongratsPresenter<>();
        mPresenter.onAttach(this);
        mPresenter.playAudioWithPath();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @Override
    public void donePlaying() {
        finish();
    }
}
