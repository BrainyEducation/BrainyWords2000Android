package com.hearatale.bw2000.ui.quiz_celebrate;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hearatale.bw2000.R;
import com.hearatale.bw2000.ui.base.BaseActivity;
import com.hearatale.bw2000.util.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuizCelebrateActivity extends BaseActivity implements QuizCelebrateMvpView {

    private int endX = 0, endY = 0;
    private String type = Config.TYPE_STACK;
    private int countTruck = 0;
    private QuizCelebrateMvpPresenter<QuizCelebrateMvpView> mPresenter;
    private List<ImageView> list = new ArrayList<>();
    private int WIDTH;
    int DURATION_TRANSLATE = 2000;

    @BindView(R.id.layout_content)
    ConstraintLayout layoutContent;

    @BindView(R.id.content)
    LinearLayout linearLayout;

    @BindView(R.id.coin1)
    ImageView mCoin1;

    @BindView(R.id.coin2)
    ImageView mCoin2;

    @BindView(R.id.coin3)
    ImageView mCoin3;

    @BindView(R.id.coin4)
    ImageView mCoin4;

    @BindView(R.id.coin5)
    ImageView mCoin5;

    @BindView(R.id.bag)
    ImageView mBigBag;


    @Override
    protected int getContentView() {
        return R.layout.activity_quiz_celebrate;
    }

    @Override
    protected void initViews() {
        mPresenter = new QuizCelebratePresenter<>();
        mPresenter.onAttach(this);
        getEndPoint();
        WIDTH = getWithScreen();
        setupLayout();
        mCoin1.post(new Runnable() {
            @Override
            public void run() {
                mPresenter.playAudioWithPath();
                setAnimation();
                //scaleView(mBigBag, 1f, 5f);
            }
        });

    }

    private void setAnimation(View view) {
        float startX = view.getX();
        float startY = view.getY();

        float endX = mBigBag.getX();
        float endY = mBigBag.getY();

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
                scaleView(mBigBag, 0.1f, 5f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimatorSet.setDuration(DURATION_TRANSLATE);
        mAnimatorSet.start();
    }

    private void setAnimation() {
        for (ImageView imageView : list) {
            setAnimation(imageView);
        }
    }

    private void setupLayout() {
        list.add(mCoin1);
        list.add(mCoin2);
        list.add(mCoin3);
        list.add(mCoin4);
        list.add(mCoin5);
        mBigBag.setVisibility(View.INVISIBLE);
        mCoin1.post(new Runnable() {
            @Override
            public void run() {

                for (ImageView imageView : list) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    layoutParams.width = WIDTH / 10;
                    imageView.setLayoutParams(layoutParams);
                }
            }
        });
        int dp_4 = getResources().getDimensionPixelOffset(R.dimen.dp_4);
        switch (type) {

            case Config.TYPE_BAG:
                linearLayout.setPadding(WIDTH / 10, 0, 0, 0);
                for (ImageView imageView : list) {
                    imageView.setImageResource(R.drawable.coin_stack);
                }
                mBigBag.setPadding(0, 0, 0, 0);
                mBigBag.setImageResource(R.drawable.bag);
                break;
            case Config.TYPE_STACK:
                for (ImageView imageView : list) {
                    imageView.setPadding(dp_4, dp_4, dp_4, dp_4);
                    imageView.setImageResource(R.drawable.gcoin);
                }
                mBigBag.setPadding(dp_4, dp_4, dp_4, dp_4);
                mBigBag.setImageResource(R.drawable.coin_stack);
                break;
            case Config.TYPE_TRUCK:
                linearLayout.setPadding(WIDTH / 10 * 2, 0, 0, 0);
                for (ImageView imageView : list) {
                    imageView.setImageResource(R.drawable.bag);
                }
                if (countTruck % 2 == 0 && countTruck != 0) {
                    mBigBag.setImageResource(R.drawable.red_armored_truck);
                } else {
                    mBigBag.setImageResource(R.drawable.green_armored_truck);
                }
                break;
            case Config.TYPE_BANK:
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setPadding(0,0,0,0);
                    if (i % 2 == 0) {
                        list.get(i).setImageResource(R.drawable.green_armored_truck);
                    } else {
                        list.get(i).setImageResource(R.drawable.red_armored_truck);
                    }
                }
                mBigBag.setPadding(dp_4, dp_4, dp_4, dp_4);
                mBigBag.setImageResource(R.drawable.bank);
                break;
        }

    }

    //get endX, endY to (stack,bag,truck) translate
    private void getEndPoint() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            endX = b.getInt(Config.ENDX) + getResources().getDimensionPixelOffset(R.dimen.dp_16);
            endY = b.getInt(Config.ENDY) + getResources().getDimensionPixelOffset(R.dimen.dp_8);
            type = b.getString(Config.TYPE);
            countTruck = b.getInt(Config.COUNT_TRUCK);
        }
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
        Runtime.getRuntime().gc();
    }

    //stack,big bag,truck translate to endX, endY + event end animation
    private void partialConstruction() {
        float startX = mBigBag.getX();
        float startY = mBigBag.getY();

        Path path = new Path();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);


        ObjectAnimator animator = ObjectAnimator.ofFloat(mBigBag, View.X, View.Y, path);
        animator.setDuration(1000);
        animator.start();

        //When stack,big bag,truck translate to endX, endY --> activity finish without animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 1000);
    }


    public int getWithScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public void scaleView(View v, float startScale, float endScale) {

        mBigBag.setVisibility(View.VISIBLE);
        mBigBag.clearAnimation();
        AnimationSet as = new AnimationSet(true);
        Animation animZoomIn = new ScaleAnimation(
                startScale, endScale * 10, // Start and end values for the X axis scaling
                startScale, endScale * 10, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        animZoomIn.setFillAfter(true); // Needed to keep the result of the animation
        animZoomIn.setDuration(2500);
        as.addAnimation(animZoomIn);

        Animation animZoomOut = new ScaleAnimation(
                startScale, startScale / endScale, // Start and end values for the X axis scaling
                startScale, startScale / endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        animZoomOut.setFillAfter(true); // Needed to keep the result of the animation
        animZoomOut.setDuration(2500);
        animZoomOut.setStartOffset(1000);
        as.addAnimation(animZoomOut);
        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                partialConstruction();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(as);
    }
}
