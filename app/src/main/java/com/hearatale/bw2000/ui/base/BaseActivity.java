package com.hearatale.bw2000.ui.base;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.util.Config;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {
    private CompositeDisposable mDisposable ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);

        initViews();

        initControls();

        mDisposable = new CompositeDisposable();
    }

    @LayoutRes
    protected abstract int getContentView();

    protected void initViews() {

    }

    protected void initControls() {

    }

    public CompositeDisposable getCompositeDisposable(){
        return this.mDisposable;
    }

    public AppCompatActivity getActivity() {
        return this;
    }

    public void pushIntent(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Logger.e(e.getMessage());
        }

    }

    public void pushIntentForResult(Intent intent, int requestCode) {
        try {
            startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            Logger.e(e.getMessage());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioPlayerHelper.getInstance().stopPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.IS_GO_BACK_HOME) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear();
    }

    protected void goBackHome(){
        Config.IS_GO_BACK_HOME = true;
        finish();
    }
}
