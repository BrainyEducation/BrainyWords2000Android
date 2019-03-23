package com.hearatale.bw2000.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements MvpView {


    protected BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (BaseActivity) context;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(getContentView(), container, false);

        ButterKnife.bind(this, rootView);

        initViews();

        initControls();

        return rootView;

    }

    @LayoutRes
    protected abstract int getContentView();

    protected void initViews() {

    }

    protected void initControls() {

    }
}
