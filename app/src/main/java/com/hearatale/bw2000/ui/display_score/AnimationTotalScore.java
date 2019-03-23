package com.hearatale.bw2000.ui.display_score;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.hearatale.bw2000.Application;
import com.hearatale.bw2000.R;
import com.hearatale.bw2000.ui.base.BaseFragment;
import com.hearatale.bw2000.util.Config;
import com.hearatale.bw2000.util.glide.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnimationTotalScore extends BaseFragment {

    String TYPE = "";
    int countTruck = 0;
    int DURATION_TRANSLATE = 1000;
    int DURATION_ALPHA = 1000;
    @BindView(R.id.image_main)
    ImageView mImageMain;

    @BindView(R.id.image1)
    ImageView mImage01;

    @BindView(R.id.image2)
    ImageView mImage02;

    @BindView(R.id.image3)
    ImageView mImage03;

    @BindView(R.id.image4)
    ImageView mImage04;

    @BindView(R.id.image5)
    ImageView mImage05;

    DismissListener mListener;

    List<ImageView> list = new ArrayList<>();


    public AnimationTotalScore() {
    }

    public static AnimationTotalScore newInstance(String type, int countTruck) {
        AnimationTotalScore animationTotalScore = new AnimationTotalScore();
        Bundle bundle = new Bundle();
        bundle.putString(Config.TYPE, type);
        bundle.putInt(Config.COUNT_TRUCK, countTruck);
        animationTotalScore.setArguments(bundle);
        return animationTotalScore;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_animation;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getArguments();
        TYPE = bundle.getString(Config.TYPE);
        countTruck = bundle.getInt(Config.COUNT_TRUCK);

        setupLayout();

        mImage01.post(new Runnable() {
            @Override
            public void run() {
                for (ImageView imageView : list)
                    setAnimation(imageView);
            }
        });
    }

    private void setupLayout() {
        list.add(mImage01);
        list.add(mImage02);
        list.add(mImage03);
        list.add(mImage04);
        list.add(mImage05);

        switch (TYPE) {
            case Config.TYPE_TRUCK:
                for (ImageView imageView : list) {
                    GlideApp.with(Application.Context).load(R.drawable.bag).into(imageView);
                }
                if (countTruck % 2 != 0) {
                    GlideApp.with(Application.Context).load(R.drawable.green_armored_truck).into(mImageMain);
                } else {
                    GlideApp.with(Application.Context).load(R.drawable.red_armored_truck).into(mImageMain);
                }
                break;
            case Config.TYPE_BANK:
                for (int i = 0; i < list.size(); i++) {
                    if (i % 2 == 0) {
                        GlideApp.with(Application.Context).load(R.drawable.green_armored_truck).into(list.get(i));
                    } else {
                        GlideApp.with(Application.Context).load(R.drawable.red_armored_truck).into(list.get(i));
                    }
                }
                GlideApp.with(Application.Context).load(R.drawable.bank).into(mImageMain);
                break;
        }
    }

    private void setAnimation(View view) {
        float startX = view.getX();
        float startY = view.getY();

        float endX = mImageMain.getX();
        float endY = mImageMain.getY();

        getHeightScreen();
        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);

        ObjectAnimator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
        }
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f),
                animator
        );
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                alphaView(mImageMain, 0.0f, 1f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimatorSet.setDuration(DURATION_TRANSLATE);
        mAnimatorSet.setStartDelay(300);
        mAnimatorSet.start();
    }

    public void alphaView(View v, float startAlpha, float endAlpha) {

        v.setVisibility(View.VISIBLE);
        v.clearAnimation();
        AlphaAnimation animation = new AlphaAnimation(startAlpha, endAlpha);
        animation.setDuration(DURATION_ALPHA);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                mListener.dismissed();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(animation);
    }

    public int getHeightScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        if (getActivity() != null) {
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            return metrics.heightPixels;
        }
        return 0;
    }

    public void setListener(DismissListener mListener) {
        this.mListener = mListener;
    }

    public interface DismissListener {
        void dismissed();
    }
}
