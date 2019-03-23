package com.hearatale.bw2000.ui.display_score;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hearatale.bw2000.R;
import com.hearatale.bw2000.ui.base.BaseActivity;
import com.hearatale.bw2000.util.Config;

import butterknife.BindView;
import butterknife.OnClick;

public class DisplayScoreActivity extends BaseActivity implements IDisplayScoreMvpView, AnimationTotalScore.DismissListener {

    //gold coin
    private int coins = 0;
    //stack coin
    private int stackCoins = 0;
    //bag coin
    private int bags = 0;
    //bank car
    private int trucks = 0;
    //sliver coin
    private int sCoin = 0;

    private int banks = 0;

    private float mScore;


    @BindView(R.id.layout_list_car_one)
    LinearLayout layoutListBank;

    @BindView(R.id.layout_list_car_two)
    LinearLayout layoutListTruck;

    @BindView(R.id.layout_list_bag)
    LinearLayout layoutListBag;

    @BindView(R.id.layout_list_stack_coin)
    LinearLayout layoutListStackCoin;

    @BindView(R.id.layout_list_gold_coin)
    LinearLayout layoutListCoin;

    @BindView(R.id.button_close)
    ImageButton mButtonBack;

    @BindView(R.id.text_toltal_score)
    TextView mTextTotalScore;

    @BindView(R.id.container)
    ConstraintLayout constraintLayout;

    @BindView(R.id.container_animation)
    FrameLayout layoutContainerAnimation;


    boolean isAnimation, mIsLargeLayout;

    private IDisplayScoreMvpPresenter<IDisplayScoreMvpView> mPresenter;
    private boolean isBank;

    @Override
    protected int getContentView() {
        return R.layout.activity_display_score;
    }

    @Override
    protected void initViews() {

        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        isAnimation = getIntent().getBooleanExtra(Config.ANIMATION, false);

        constraintLayout.setVisibility(!isAnimation ? View.VISIBLE : View.INVISIBLE);


        mPresenter = new DisplayScorePresenter<>();
        mPresenter.onAttach(this);
        mScore = mPresenter.getTotalScore();
        getScoinGcoinStackBagTruck(mScore);
        if (isAnimation) {

            if ((int)mScore % 625 == 0) {
                isBank = true;
                showFragmentAnimation(Config.TYPE_BANK, 0);
            } else {
                isBank = false;
                showFragmentAnimation(Config.TYPE_TRUCK, trucks);
            }
        }

        setupLayout(banks, trucks, bags, stackCoins, coins, sCoin);


        if (mScore > (int) mScore) {
            // String with type float ex: mScore = 120.5
            mTextTotalScore.setText(String.valueOf(mScore));
        } else {
            // String with type int ex: mScore = 120
            mTextTotalScore.setText(String.valueOf((int) mScore));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    private void getScoinGcoinStackBagTruck(float mScore) {

        int score = (int) mScore;
        if (mScore > score) {
            sCoin = 1;
        } else {
            sCoin = 0;
        }
        banks = score / 625;
        trucks = (score - (banks * 625)) / 125;
        bags = (score - (banks * 625) - (trucks * 125)) / 25;
        stackCoins = (score - (banks * 625) - (trucks * 125) - (bags * 25)) / 5;
        coins = score - (banks * 625) - (trucks * 125) - (bags * 25) - (stackCoins * 5);
    }

    private void setupLayout(int banks, int trucks, int bags, int stack, int gCoin, int sCoin) {

        setVisible(layoutListBank, banks, false);
        setVisible(layoutListTruck, trucks, false);
        setVisible(layoutListBag, bags, false);
        setVisible(layoutListStackCoin, stack, false);
        setVisible(layoutListCoin, gCoin, false);
        setVisible(layoutListCoin, sCoin, true);
    }

    private void setVisible(LinearLayout linearLayout, int number, boolean isSliverCoin) {
        if (!isSliverCoin) {
            int childCount = linearLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (i < number) {
                    View v = linearLayout.getChildAt(i);
                    v.setVisibility(View.VISIBLE);
                } else {
                    View v = linearLayout.getChildAt(i);
                    v.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            if (number == 1) {
                int childCount = linearLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View v = linearLayout.getChildAt(i);
                    if (v.getVisibility() == View.INVISIBLE) {
                        ((ImageView) v).setImageResource(R.drawable.scoin);
                        v.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.button_close)
    public void goBack() {
        onBackPressed();
    }


    @Override
    public void dismissed() {
        if (constraintLayout.getVisibility() == View.INVISIBLE) {
            fadeOutAnimation(layoutContainerAnimation);
            constraintLayout.setVisibility(View.VISIBLE);
            fadeInAnimation(constraintLayout);
        }

    }

    public void showFragmentAnimation(String type, int trucks) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        AnimationTotalScore animationTotalScore = AnimationTotalScore.newInstance(type, trucks);//the fragment you want to show
        animationTotalScore.setListener(this);

        fragmentTransaction.replace(R.id.container_animation, animationTotalScore);//R.id.content_frame is the layout you want to replace
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void fadeOutAnimation(View view) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isBank){
                            mPresenter.startCongratulations();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }else {
                            finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        }
                    }
                }, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(fadeOut);
    }

    private void fadeInAnimation(View view) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);
        fadeIn.setFillAfter(true);
        view.startAnimation(fadeIn);
    }
}
