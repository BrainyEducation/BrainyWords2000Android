package com.hearatale.bw2000.ui.splash;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hearatale.bw2000.Application;
import com.hearatale.bw2000.R;
import com.hearatale.bw2000.data.AppDataManager;
import com.hearatale.bw2000.data.model.BaseResponseGetAllStudent;
import com.hearatale.bw2000.data.model.student.BaseResponseCreateStudent;
import com.hearatale.bw2000.data.model.student.BaseResponseStudentLogin;
import com.hearatale.bw2000.data.model.student.PayloadStudent;
import com.hearatale.bw2000.data.model.student.Student;
import com.hearatale.bw2000.data.model.teacher_login.BaseResponseTeacherLogin;
import com.hearatale.bw2000.data.model.teacher_login.PayLoadLogin;
import com.hearatale.bw2000.network.ApiService;
import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.adapter.StudentAdapter;
import com.hearatale.bw2000.ui.base.BaseActivity;
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

public class SplashActivity extends BaseActivity implements SplashMvpView {
    private SplashMvpPresenter<SplashMvpView> mPresenter;
    private static final String PATH = "xtra/HEADINGS/00brainy_words_2000.mp3";

    @BindView(R.id.username)
    EditText mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;

    @BindView(R.id.login_progress)
    ProgressBar mProgressView;

    @BindView(R.id.text_login)
    TextView mTextLogin;

    @BindView(R.id.text_continue)
    TextView mTextContinue;

    @BindView(R.id.button_login)
    Button mButtonLogin;

    @BindView(R.id.recycle_view_student)
    RecyclerView mRecycleView;

    @BindView(R.id.container)
    ConstraintLayout mFormLogin;

    @BindView(R.id.layout_option)
    LinearLayout mFormOption;

    @BindView(R.id.button_close)
    View mButtonCloseFormLogin;

    @BindView(R.id.transparent)
    FrameLayout mFormTransparent;

    @BindView(R.id.button_add_student)
    Button mButtonAddStudent;

    @BindView(R.id.form_login)
    LinearLayout mChildFormLogin;

    @BindView(R.id.button_back)
    ImageView mButtonBack;

    @BindView(R.id.student_name)
    EditText mStudentName;

    @BindView((R.id.student_id))
    EditText mStudentId;

    @BindView(R.id.form_add_student)
    ConstraintLayout mFormAddStudent;


    private boolean isStartGame = false;
    private boolean isFirstTimeOpenApp = true;
    private ApiService apiService;
    private StudentAdapter mAdapter;
    private List<Student> mData = new ArrayList<>();
    private BaseResponseTeacherLogin mBaseResponseTeacherLogin;
    private BaseResponseStudentLogin mBaseResponseStudentLogin;

    @BindView(R.id.logo)
    ImageView logo;


    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {
        mPresenter = new SplashPresenter<>();
        mPresenter.onAttach(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        GlideApp.with(this).load(R.drawable.brainy_words).override(width, height).into(logo);
        apiService = AppDataManager.getInstance().getApiService();
        initRV();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String teacherId = AppDataManager.getInstance().getSessionTeacher().getTeacher().getTeacherId();
                String studentId = mData.get(position).getStudentId();
                startStudentLogin(teacherId, studentId);
            }
        });
        isStartGame = AppDataManager.getInstance().isStartGame();
        isFirstTimeOpenApp = AppDataManager.getInstance().isFirstTimeOpenApp();

        if (AppDataManager.getInstance().getBaseResponseGetAllStudent() != null) {
            List<Student> data = AppDataManager.getInstance().getBaseResponseGetAllStudent().getStudents();
            mData.addAll(data);
        }
        mBaseResponseTeacherLogin = AppDataManager.getInstance().getSessionTeacherLogin();
        mBaseResponseStudentLogin = AppDataManager.getInstance().getSessionStudentLogin();


        if(!isStartGame) {
            AudioPlayerHelper.getInstance().playAudio(PATH, new AudioPlayerHelper.DonePlayingListener() {
                @Override
                public void donePlaying() {

                    if (mBaseResponseTeacherLogin == null) {

                        mFormTransparent.setVisibility(View.VISIBLE);
                        showFormOption();

                    } else {
                        if (mBaseResponseStudentLogin != null) {
                            mPresenter.goToMainActivity();
                        } else {
                            mFormTransparent.setVisibility(View.VISIBLE);
                            getAllStudents(AppDataManager.getInstance().getSessionTeacher().getToken());
                            showFormListStudent();
                            mProgressView.setVisibility(View.VISIBLE);
                            new CountDownTimer(1000,1000){

                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    mProgressView.setVisibility(View.INVISIBLE);
                                }
                            }.start();
                        }
                    }


                }
            });
        }else{
            if (mBaseResponseTeacherLogin == null) {

                mFormTransparent.setVisibility(View.VISIBLE);
                showFormOption();

            } else {
                if (mBaseResponseStudentLogin != null) {
                    mPresenter.goToMainActivity();
                } else {
                    mFormTransparent.setVisibility(View.VISIBLE);
                    showFormListStudent();
                }
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();

    }

    @Override
    protected void onResume() {
        super.onResume();


        //mPresenter.showLoginDialog();

    }

    public void startAudio() {
        mPresenter.playAudioWithPath();
    }


    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(Application.Context.getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(Application.Context.getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(Application.Context.getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid password .
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(Application.Context.getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
            startTeacherLogin(email, password);
            showProgressLogin(true);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void showFormLogin() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(300);
        mProgressView.setVisibility(View.INVISIBLE);
        mFormOption.setVisibility(View.INVISIBLE);
        mFormLogin.setVisibility(View.VISIBLE);
        mButtonCloseFormLogin.setVisibility(View.VISIBLE);
        mButtonAddStudent.setVisibility(View.INVISIBLE);
        mChildFormLogin.setVisibility(View.VISIBLE);
        mRecycleView.setVisibility(View.INVISIBLE);
        mTextLogin.setText(R.string.login);
        mButtonLogin.setVisibility(View.VISIBLE);
        mTextContinue.setVisibility(View.VISIBLE);
        mButtonCloseFormLogin.setVisibility(View.VISIBLE);
        mButtonBack.setVisibility(View.INVISIBLE);
        mFormLogin.startAnimation(fadeIn);
        isFirstTimeOpenApp = false;
        AppDataManager.getInstance().setFirstTimeOpenApp(isFirstTimeOpenApp);
    }

    private void showFormOption() {

        mFormOption.setVisibility(View.VISIBLE);
        mFormLogin.setVisibility(View.INVISIBLE);
        mButtonCloseFormLogin.setVisibility(View.INVISIBLE);
    }

    private void initRV() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new StudentAdapter(mData);
        mRecycleView.setAdapter(mAdapter);
    }


    private void showFormAddStudent() {
        mButtonBack.setVisibility(View.INVISIBLE);
        mFormAddStudent.setVisibility(View.VISIBLE);
    }

    private void showFormListStudent() {
        mProgressView.setVisibility(View.INVISIBLE);
        mRecycleView.setVisibility(View.VISIBLE);
        mButtonAddStudent.setVisibility(View.VISIBLE);
        mTextLogin.setText(R.string.students);
        mButtonBack.setVisibility(View.VISIBLE);
        mChildFormLogin.setVisibility(View.INVISIBLE);
        mButtonLogin.setVisibility(View.INVISIBLE);
        mTextContinue.setVisibility(View.INVISIBLE);
    }

    private void gotoMainActivity() {
        mPresenter.goToMainActivity();
        mFormTransparent.setVisibility(View.INVISIBLE);
    }


    private void showProgressLogin(boolean isLogin) {
        if (isLogin) {
            mButtonCloseFormLogin.setVisibility(View.INVISIBLE);
            mChildFormLogin.setVisibility(View.INVISIBLE);
            mTextLogin.setText(R.string.loadding);
            mProgressView.setVisibility(View.VISIBLE);
            mTextContinue.setVisibility(View.INVISIBLE);
            mButtonLogin.setVisibility(View.INVISIBLE);
        } else {
            mButtonCloseFormLogin.setVisibility(View.VISIBLE);
            mChildFormLogin.setVisibility(View.VISIBLE);
            mTextLogin.setText(R.string.login);
            mProgressView.setVisibility(View.INVISIBLE);
            mTextContinue.setVisibility(View.VISIBLE);
            mButtonLogin.setVisibility(View.VISIBLE);
        }
    }

    private void attemptCreateStudent() {
        // Reset errors.
        mStudentName.setError(null);
        mStudentId.setError(null);

        // Store values at the time of the login attempt.
        String studentName = mStudentName.getText().toString();
        String studentId = mStudentId.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(studentId) && studentId.length() > 3) {
            mStudentId.setError(Application.Context.getString(R.string.error_invalid_student_id));
            focusView = mStudentId;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(studentName)) {
            mStudentName.setError(Application.Context.getString(R.string.error_field_required));
            focusView = mStudentName;
            cancel = true;
        } else {
            // Check for a valid password .
            if (TextUtils.isEmpty(studentId)) {
                mStudentId.setError(Application.Context.getString(R.string.error_field_required));
                focusView = mStudentId;
                cancel = true;
            }
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            startTeacherLogin(email, password);
            createStudent(studentName, studentId);
        }
    }


    //----------------------------------------
    private void createStudent(String name, String studentId) {

        String teacherId = AppDataManager.getInstance().getSessionTeacher().getTeacher().getId();
        String token = AppDataManager.getInstance().getSessionTeacher().getToken();
        Map<String, String> header = Helper.createHeader(token);
        PayloadStudent payloadStudent = Helper.createPayloadCreateStudent(name, studentId, teacherId);

        Disposable disposable1 = apiService.createStudent(header, payloadStudent)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);
        this.getCompositeDisposable().add(disposable1);
    }

    private void handleResponse(BaseResponseCreateStudent response) {
        if (response.getStatus().contains("error")) {
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

        } else {
            String token = AppDataManager.getInstance().getSessionTeacher().getToken();
            Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();

            getAllStudents(token);
            // close form add student only
            cancelCreateStudent();
        }
    }

    private void startStudentLogin(String teacher_id, String student_id) {
        Disposable disposable = apiService.startStudentLogin(Helper.createPayloadStudent(teacher_id, student_id))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleErrorStudentLogin);
        this.getCompositeDisposable().add(disposable);
    }


    private void getAllStudents(String token) {
        Disposable disposable = apiService.getAllStudents("Bearer " + token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleErrorStudentLogin);
        this.getCompositeDisposable().add(disposable);
    }

    private void startTeacherLogin(String email, String password) {

        PayLoadLogin payLoadLogin = new PayLoadLogin();
        payLoadLogin.setEmail(email);
        payLoadLogin.setPassword(password);

        Disposable disposable1 = apiService.startTeacherLogin(payLoadLogin)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError);
        this.getCompositeDisposable().add(disposable1);
    }


    private void handleResponse(BaseResponseGetAllStudent responseGetAllStudent) {
        AppDataManager.getInstance().setBaseResponseGetAllStudent(responseGetAllStudent);

        List<Student> data = AppDataManager.getInstance().getBaseResponseGetAllStudent().getStudents();

        if (mAdapter != null) {
            mAdapter.replaceData(data);
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(this, R.string.email_r_password_isincorrect, Toast.LENGTH_SHORT).show();
        showFormLogin();
    }

    private void handleErrorStudentLogin(Throwable error) {
        Toast.makeText(this, R.string.please_check_your_internet, Toast.LENGTH_SHORT).show();

    }

    private void handleResponse(BaseResponseTeacherLogin responseTeacherLogin) {
        mBaseResponseTeacherLogin = responseTeacherLogin;
        getAllStudents(mBaseResponseTeacherLogin.getToken());
        AppDataManager.getInstance().setSessionTeacherLogin(mBaseResponseTeacherLogin);
        if (!isFirstTimeOpenApp)
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
        showFormListStudent();
    }

    private void handleResponse(BaseResponseStudentLogin responseStudentLogin) {
        Toast.makeText(this, R.string.student_login_success, Toast.LENGTH_SHORT).show();
        AppDataManager.getInstance().setSessionStudentLogin(responseStudentLogin);
        AppDataManager.getInstance().setStartGame(true);
        gotoMainActivity();

    }

    @OnClick(R.id.button_close)
    void closeFormLogin() {
        mFormOption.setVisibility(View.VISIBLE);
        mFormLogin.setVisibility(View.INVISIBLE);
        mButtonCloseFormLogin.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.button_login)
    void clickLogin() {
        attemptLogin();
    }

    @OnClick(R.id.button_add_student)
    void clickAddStudent() {
        showFormAddStudent();
    }

    @OnClick(R.id.button_back)
    void back() {
        showFormLogin();
        AppDataManager.getInstance().teacherLogout();
    }

    @OnClick(R.id.button_cancel)
    void cancelCreateStudent() {
        mFormAddStudent.setVisibility(View.INVISIBLE);
        mButtonBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_create)
    void createStudent() {
        attemptCreateStudent();
    }

    @OnClick(R.id.button_teacher)
    void clickTeacher() {
        showFormLogin();
    }


    @OnClick(R.id.button_play_game)
    void clickPlayGame() {
        AppDataManager.getInstance().teacherLogout();
        AppDataManager.getInstance().studentLogout();
        AppDataManager.getInstance().setStartGame(true);
        gotoMainActivity();
    }


}
