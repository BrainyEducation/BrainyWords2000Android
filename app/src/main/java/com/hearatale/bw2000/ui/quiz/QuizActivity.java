package com.hearatale.bw2000.ui.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hearatale.bw2000.R;
import com.hearatale.bw2000.data.AppDataManager;
import com.hearatale.bw2000.data.model.Analytic.BaseResponseAnalytic;
import com.hearatale.bw2000.data.model.Analytic.PayLoadAnalytic;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.QuestionModel;
import com.hearatale.bw2000.data.model.teacher_login.BaseResponseTeacherLogin;
import com.hearatale.bw2000.network.ApiService;
import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.base.BaseActivity;
import com.hearatale.bw2000.ui.quiz.answer_letter.AnswerFragment;
import com.hearatale.bw2000.util.Config;
import com.hearatale.bw2000.util.Helper;
import com.hearatale.bw2000.util.glide.GlideApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuizActivity extends BaseActivity implements QuizMvpView, AnswerFragment.ChooseAnswerListener {
    private QuizMvpPresenter<QuizMvpView> mPresenter;
    private String mCategory = "";
    private boolean anim = false;
    private static int currentPraiseIndex = 0;
    private int playCount = 0;
    private float mScore = 0;
    private float mTotalScore = 0;
    //when won a truck then start video  Congratulations
    private boolean isWonBank = false;
    //when StartCelebration then no updateQuestion because 2 audio playing
    private boolean isStartCelebration = false;

    private boolean isAnswerQuestion = false;
    private AnswerFragment answerFragment;
    static final int TIME_REPLACE_FRAGMENT_ANIMATION_FINISH = 1000;
    private boolean hasAnimationOfQuiz = false;

    int DELAY_TIME = 0;
    List<ImageView> listCoins = new ArrayList<>();
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

    //bank
    private int banks = 0;

    //if is first time open just set coin bag truck without animation else ...
    private boolean isFirstTimeOpenQuiz = true;
    @BindView(R.id.layout_content)
    ConstraintLayout layoutContent;

    //20 coins
    @BindView(R.id.image_coin1)
    ImageView mCoin1;

    @BindView(R.id.image_coin2)
    ImageView mCoin2;

    @BindView(R.id.image_coin3)
    ImageView mCoin3;

    @BindView(R.id.image_coin4)
    ImageView mCoin4;

    @BindView(R.id.image_coin5)
    ImageView mCoin5;

    @BindView(R.id.image_coin6)
    ImageView mCoin6;

    @BindView(R.id.image_coin7)
    ImageView mCoin7;

    @BindView(R.id.image_coin8)
    ImageView mCoin8;

    @BindView(R.id.image_coin9)
    ImageView mCoin9;

    @BindView(R.id.image_coin10)
    ImageView mCoin10;

    @BindView(R.id.image_coin11)
    ImageView mCoin11;

    @BindView(R.id.image_coin12)
    ImageView mCoin12;

    @BindView(R.id.image_coin13)
    ImageView mCoin13;

    @BindView(R.id.image_coin14)
    ImageView mCoin14;

    @BindView(R.id.image_coin15)
    ImageView mCoin15;

    @BindView(R.id.image_coin16)
    ImageView mCoin16;

    @BindView(R.id.image_coin17)
    ImageView mCoin17;

    @BindView(R.id.image_coin18)
    ImageView mCoin18;

    @BindView(R.id.image_coin19)
    ImageView mCoin19;

    @BindView(R.id.image_coin20)
    ImageView mCoin20;

    @BindView(R.id.image_coin21)
    ImageView mCoin21;

    @BindView(R.id.image_coin22)
    ImageView mCoin22;

    @BindView(R.id.image_coin23)
    ImageView mCoin23;

    @BindView(R.id.image_coin24)
    ImageView mCoin24;

    @BindView(R.id.image_coin25)
    ImageView mCoin25;


    //button back + repeate
    @BindView(R.id.button_back)
    ImageButton mButtonBack;

    @BindView(R.id.button_repeat)
    ImageButton mButtonRepeat;

    @BindView(R.id.button_bag)
    ImageButton mButtonGoDisplayScore;

    QuestionModel questionModel;

    boolean mIsLargeLayout = false;
    boolean isStartAnimationTotal = false;
    long startTime, endTime;


    @Override
    protected int getContentView() {
        return R.layout.activity_quiz;
    }

    @Override
    protected void initViews() {

        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        mPresenter = new QuizPresenter<>();
        mPresenter.onAttach(this);


        getCategory();
        mPresenter.setCategory(mCategory);

        mScore = AppDataManager.getInstance().getScore(mCategory);

        mTotalScore = mPresenter.getTotalScore();

        setupLayout();

        mPresenter.updateQuestion(mCategory);

        getScoinGcoinStackBagTruck(mScore);

        setLayoutForScore();

//        currentPraiseIndex = mPresenter.getCurrentPraiseIndex();

        isFirstTimeOpenQuiz = false;
    }

    private void getCategory() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mCategory = b.getString(Config.CATEGORY);
            if (mCategory != null && mCategory.contains("letters")) {
                mCategory = "School/letters/letters_quiz";
            }
        }
    }

    private void setupLayout() {
        layoutContent.setPadding(getResources().getDimensionPixelOffset(R.dimen.dp_16), 0, 0, 0);

        mButtonBack.post(new Runnable() {
            @Override
            public void run() {
                int SDP_40 = getResources().getDimensionPixelOffset(R.dimen._40sdp);
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mButtonBack.getLayoutParams();
                layoutParams.width = SDP_40;
                layoutParams.height = SDP_40;
                mButtonBack.setLayoutParams(layoutParams);
                layoutParams = (ConstraintLayout.LayoutParams) mButtonRepeat.getLayoutParams();
                layoutParams.width = SDP_40;
                layoutParams.height = SDP_40;
                mButtonRepeat.setLayoutParams(layoutParams);
                layoutParams = (ConstraintLayout.LayoutParams) mButtonGoDisplayScore.getLayoutParams();
                layoutParams.width = SDP_40;
                layoutParams.height = SDP_40;
                mButtonGoDisplayScore.setLayoutParams(layoutParams);
            }
        });

        listCoins.add(mCoin1);
        listCoins.add(mCoin2);
        listCoins.add(mCoin3);
        listCoins.add(mCoin4);

        listCoins.add(mCoin6);
        listCoins.add(mCoin7);
        listCoins.add(mCoin8);
        listCoins.add(mCoin9);

        listCoins.add(mCoin11);
        listCoins.add(mCoin12);
        listCoins.add(mCoin13);
        listCoins.add(mCoin14);

        listCoins.add(mCoin16);
        listCoins.add(mCoin17);
        listCoins.add(mCoin18);
        listCoins.add(mCoin19);

        listCoins.add(mCoin21);
        listCoins.add(mCoin22);
        listCoins.add(mCoin23);
        listCoins.add(mCoin24);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    @OnClick(R.id.button_back)
    public void goBack() {
        finish();
    }

    @OnClick(R.id.button_repeat)
    public void repeat() {
        mPresenter.playAudioQuestion();
    }

    @OnClick(R.id.button_bag)
    public void goToDisplayScore() {
        mPresenter.goToDisplayScore(false);
    }

    private void replaceFragment(QuestionModel questionModel) {
        // first time anim = false and replace fragment no animation
        if (questionModel != null) {

            answerFragment = null;
            answerFragment = AnswerFragment.newInstance(questionModel);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (anim) {
                ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
            }
            ft.replace(R.id.content, answerFragment);
            ft.commit();
            anim = !anim ? true : true;
        }
    }

    @Override
    public void onCorrected(Item item) {
        endTime = System.currentTimeMillis();
        isFirstTimeOpenQuiz = false;
        if (playCount == 2) {
            mScore += 0.5f;
            mTotalScore += 0.5f;
            isFirstTimeOpenQuiz = sCoin == 0;
            // send data to server analytic
            sendAnalytic(item, 2);
        } else {
            mScore++;
            mTotalScore++;

            // send data to server analytic
            sendAnalytic(item, 1);
        }
        isAnswerQuestion = true;
        mPresenter.setScore(mCategory, mScore, mTotalScore);
        // finish play audio correct ==> update question , mScore++, currentPraiseIndex++ (currentPraiseIndex : position of list audio correct)
        mPresenter.playAudioCorrect(new AudioPlayerHelper.DonePlayingListener() {
            @Override
            public void donePlaying() {
                if (playCount != 2) {
                    mPresenter.playAudioCorrect(currentPraiseIndex, new AudioPlayerHelper.DonePlayingListener() {
                        @Override
                        public void donePlaying() {

                            currentPraiseIndex++;
                            //reset and get again
                            checkAnimationTotalScore(mTotalScore);
                            getScoinGcoinStackBagTruck(mScore);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setLayoutForScore();
                                }
                            }, DELAY_TIME);


                            if (!isStartCelebration && !isStartAnimationTotal) {
                                mPresenter.updateQuestion(mCategory);
                            }
                        }

                    });
                } else {
                    checkAnimationTotalScore(mTotalScore);
                    getScoinGcoinStackBagTruck(mScore);
                    setLayoutForScore();
                    if (!isStartCelebration && !isStartAnimationTotal) {
                        mPresenter.updateQuestion(mCategory);
                    }
                }
            }
        });

    }

    @Override
    public void onInCorrected() {

        //if playCount == 2 ==> finish play audio incorrect==> update question
        //else only play audio incorrect
        mPresenter.playAudioInCorrect(new AudioPlayerHelper.DonePlayingListener() {
            @Override
            public void donePlaying() {
                if (playCount >= 2) {
                    mPresenter.updateQuestion(mCategory);
                    isAnswerQuestion = true;
                }
            }
        });

    }

    @Override
    public void playCount(int playCount) {
        this.playCount = playCount;
    }

    @Override
    public void finishUpdateQuestion(QuestionModel question) {
        if (question != null) {
            this.questionModel = question;
            replaceFragment(question);
            isAnswerQuestion = false;
            if (!isWonBank && !hasAnimationOfQuiz) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.playAudioQuestion();
                        startTime = System.currentTimeMillis();
                    }
                }, TIME_REPLACE_FRAGMENT_ANIMATION_FINISH);
            }
        }
    }

    private void getScoinGcoinStackBagTruck(float mscore) {

        int score = (int) mscore;
        if (mscore > score) {
            sCoin = 1;
        } else {
            sCoin = 0;
        }
        banks = score / 625;
        trucks = (score - (banks * 625)) / 125;
        bags = (score - (banks * 625) - (trucks * 125)) / 25;
        stackCoins = (score - (banks * 625) - (trucks * 125) - (bags * 25)) / 5;
        coins = score - (banks * 625) - (trucks * 125) - (bags * 25) - (stackCoins * 5);

        if (score == 0) return;

        if (score % 625 == 0) {
            if (banks == 5) {
                //reset score
                DELAY_TIME = 300;
                if (!isFirstTimeOpenQuiz && !isStartAnimationTotal) {
                    int endX = (int) mCoin5.getX();
                    int endY = (int) mCoin5.getY();
                    isWonBank = true;
                    isStartCelebration = true;
                    anim = false;
                    mPresenter.startCelebration(Config.TYPE_BANK, endX, endY, trucks);
                }
                mScore = 0;
            } else {
                DELAY_TIME = 300;
                if (!isFirstTimeOpenQuiz && !isStartAnimationTotal) {
                    int endX = (int) listCoins.get(banks - 1).getX();
                    int endY = (int) listCoins.get(banks - 1).getY();
                    isWonBank = true;
                    isStartCelebration = true;
                    anim = false;
                    mPresenter.startCelebration(Config.TYPE_BANK, endX, endY, trucks);
                }
            }
        } else {
            if (score % 125 == 0) {
                //start animation BagsToTruck
                DELAY_TIME = 300;
                if (!isFirstTimeOpenQuiz && !isStartAnimationTotal) {
                    int endX = (int) listCoins.get(trucks - 1).getX();
                    int endY = (int) listCoins.get(trucks - 1).getY();
                    isStartCelebration = true;
                    anim = false;
                    mPresenter.startCelebration(Config.TYPE_TRUCK, endX, endY, trucks);
                }

            } else {
                if (score % 25 == 0) {
                    //start animation stackToBag
                    DELAY_TIME = 300;
                    if (!isFirstTimeOpenQuiz && !isStartAnimationTotal) {
                        isStartCelebration = true;
                        int endX = (int) listCoins.get(bags - 1).getX();
                        int endY = (int) listCoins.get(bags - 1).getY();
                        anim = false;
                        mPresenter.startCelebration(Config.TYPE_BAG, endX, endY, trucks);
                    }
                } else {
                    if (score % 5 == 0) {
                        //start animation coinToStack
                        DELAY_TIME = 300;
                        if (!isFirstTimeOpenQuiz && !isStartAnimationTotal) {
                            isStartCelebration = true;
                            int endX = (int) listCoins.get(stackCoins - 1).getX();
                            int endY = (int) listCoins.get(stackCoins - 1).getY();
                            anim = false;
                            mPresenter.startCelebration(Config.TYPE_STACK, endX, endY, trucks);
                        }
                    } else {
                        //set coin
                        DELAY_TIME = 0;
                    }
                }
            }
        }


    }

    private void setLayoutForScore() {
        int dp_4 = getResources().getDimensionPixelOffset(R.dimen.dp_4);
        if (sCoin == 1) {
            loadImage(mCoin5, R.drawable.scoin);
            // mCoin5.setImageResource(R.drawable.scoin);
            mCoin5.setVisibility(View.VISIBLE);
        } else {
            sCoin = 0;
            mCoin5.setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < listCoins.size(); i++) {
            //gold coin
            if (i < coins) {
                listCoins.get(i).setPadding(0, 0, 0, 0);
                loadImage(listCoins.get(i), R.drawable.gcoin);
                //listCoins.get(i).setImageResource(R.drawable.gcoin);
                listCoins.get(i).setPadding(dp_4, dp_4, dp_4, dp_4);
                listCoins.get(i).setVisibility(View.VISIBLE);
            } else {
                listCoins.get(i).setPadding(0, 0, 0, 0);
                //stack coin
                if (i < coins + stackCoins) {
                    loadImage(listCoins.get(i), R.drawable.coin_stack);
                    listCoins.get(i).setPadding(dp_4, dp_4, dp_4, dp_4);
                    //listCoins.get(i).setImageResource(R.drawable.coin_stack);
                    listCoins.get(i).setVisibility(View.VISIBLE);
                } else {
                    listCoins.get(i).setPadding(0, 0, 0, 0);
                    //bag
                    if (i < bags + coins + stackCoins) {
                        loadImage(listCoins.get(i), R.drawable.bag);
                        //listCoins.get(i).setImageResource(R.drawable.bag);
                        listCoins.get(i).setVisibility(View.VISIBLE);
                    } else {
                        //truck
                        if (i < trucks + bags + stackCoins + coins) {
                            if ((i - (bags + stackCoins + coins)) % 2 != 0) {
                                //TODO: green truck
                                loadImage(listCoins.get(i), R.drawable.red_armored_truck);
                                //listCoins.get(i).setImageResource(R.drawable.red_armored_truck);
                                listCoins.get(i).setVisibility(View.VISIBLE);
                            } else {
                                //TODO: red truck
                                loadImage(listCoins.get(i), R.drawable.green_armored_truck);
                                //listCoins.get(i).setImageResource(R.drawable.green_armored_truck);
                                listCoins.get(i).setVisibility(View.VISIBLE);
                            }
                        } else {
                            if (i < banks + trucks + bags + stackCoins + coins) {
                                loadImage(listCoins.get(i), R.drawable.bank);
                                //listCoins.get(i).setImageResource(R.drawable.bag);
                                listCoins.get(i).setVisibility(View.VISIBLE);
                            } else {
                                if (listCoins.get(i).getVisibility() == View.VISIBLE) {
                                    listCoins.get(i).setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadImage(ImageView imageView, Object o) {
        int WIDTH = imageView.getMeasuredWidth();
        int HEIGHT = imageView.getMeasuredHeight();
        GlideApp.with(this)
                .load(o)
                .override(WIDTH, HEIGHT)
                .into(imageView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isAnswerQuestion) {
            anim = false;
            mPresenter.updateQuestion(mCategory);
        }
    }

    private void showAnimationTotalScore() {
        mPresenter.goToDisplayScore(true);
    }

    private void checkAnimationTotalScore(float totalScore) {
        if (playCount == 1) {
            //won 1 bank
            if ((int) totalScore % 625 == 0) {

                hasAnimationOfQuiz = true;
                isStartAnimationTotal = true;
                anim = false;
                showAnimationTotalScore();
                return;
            }
            //won 1 truck
            if ((int) totalScore % 125 == 0) {

                hasAnimationOfQuiz = true;
                isStartAnimationTotal = true;
                anim = false;
                showAnimationTotalScore();
            }
        } else {
            int score = (int) totalScore;
            if (score >= totalScore) {
                //won 1 bank
                if ((int) totalScore % 625 == 0) {

                    hasAnimationOfQuiz = true;
                    isStartAnimationTotal = true;
                    anim = false;
                    showAnimationTotalScore();
                    return;
                }
                //won 1 truck
                if ((int) totalScore % 125 == 0) {

                    hasAnimationOfQuiz = true;
                    isStartAnimationTotal = true;
                    anim = false;
                    showAnimationTotalScore();
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQUES_CODE_DISPLAY_SCORE && mPresenter != null) {
            if (hasAnimationOfQuiz) {
                isStartCelebration = false;
                isStartAnimationTotal = false;
                getScoinGcoinStackBagTruck(mScore);
            }
        }
        if (requestCode == Config.REQUES_CODE && mPresenter != null) {
            hasAnimationOfQuiz = false;
            mPresenter.updateQuestion(mCategory);
            if (mScore == 0) {
                isFirstTimeOpenQuiz = true;
                getScoinGcoinStackBagTruck(mScore);
            }
            if (isWonBank) {
                anim = false;
                mPresenter.startCongratulations();
            }
            isStartCelebration = false;
            isWonBank = false;
            setLayoutForScore();
        } else {
            hasAnimationOfQuiz = false;
            isFirstTimeOpenQuiz = true;
            getScoinGcoinStackBagTruck(mScore);
            setLayoutForScore();
        }
    }

    long getTimeSpent(long startTime, long endTime) {
        return (endTime - startTime) / 1000;
    }

    private void sendAnalytic(Item item, long playCount) {


        BaseResponseTeacherLogin mBaseResponseTeacherLogin = AppDataManager.getInstance().getSessionTeacher();
        if (mBaseResponseTeacherLogin == null) {
            return;
        }

        PayLoadAnalytic payLoadAnalytic = new PayLoadAnalytic();
        payLoadAnalytic.setTimeSpent(getTimeSpent(startTime, endTime));
        payLoadAnalytic.setProgram(Config.PROGRAM_ID);
        payLoadAnalytic.setFocusItem(item.getAppId());
        payLoadAnalytic.setStudent(AppDataManager.getInstance().getSessionStudentLogin().getStudent().getId());
        payLoadAnalytic.setCorrectOn(playCount);
        Map<String, String> header = Helper.createHeader(AppDataManager.getInstance().getSessionTeacher().getToken());

        ApiService apiService = AppDataManager.getInstance().getApiService();

        Disposable disposable = apiService.startPostAnalytics(header, payLoadAnalytic)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleErrorStudentLogin);

        getCompositeDisposable().add(disposable);


    }

    void handleResponse(BaseResponseAnalytic baseResponseAnalytic) {
        Log.d("startPostAnalytics", "OK:" + baseResponseAnalytic.getAnalytic().getTimeSpent() + ":" + baseResponseAnalytic.getAnalytic().getCorrectOn());
    }

    void handleErrorStudentLogin(Throwable error) {
        Log.d("startPostAnalytics", error.getMessage());
    }
}
