package com.hearatale.bw2000.ui.store_display_items;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hearatale.bw2000.R;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.typedef.ScreenDef;
import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.adapter.DisplayItemsAdapter;
import com.hearatale.bw2000.ui.base.BaseActivity;
import com.hearatale.bw2000.ui.main.MainActivity;
import com.hearatale.bw2000.ui.quiz.QuizActivity;
import com.hearatale.bw2000.util.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DisplayItemsActivity extends BaseActivity implements DisplayMvpView {

    private DisplayItemsAdapter mAdapter;
    private List<Item> mData = new ArrayList<>();
    private DisplayMvpPresenter<DisplayMvpView> mPresenter;
    private static final String INTERIORS = "Interiors";
    private String titleCategory = "";
    @ScreenDef
    private int mFromScreen = ScreenDef.INTERIOR; //Default

    @BindView(R.id.rv_drawerList)
    RecyclerView mRecycleView;

    @Override
    protected int getContentView() {
        return R.layout.activity_display_items;
    }

    @Override
    protected void initViews() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            titleCategory = bundle.getString(Config.CATEGORY);
            mFromScreen = bundle.getInt(Config.FROM_SCREEN, ScreenDef.INTERIOR);
        }
        mPresenter = new DisplayItemsPresenter<>();
        mPresenter.onAttach(this);

        mData.addAll(mPresenter.getData(titleCategory));
        initRV();
    }

    private void initRV() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new DisplayItemsAdapter(mData);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    protected void initControls() {

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!titleCategory.equals(INTERIORS)) {
                    String AUDIO_URL = mData.get(position).getImageUri();
                    AUDIO_URL = AUDIO_URL.replace("png", "mp3");
                    String BASE_URL = titleCategory + "/sounds";
                    int index = AUDIO_URL.lastIndexOf("/");
                    String NAME_FILE = AUDIO_URL.substring(index, AUDIO_URL.length());
                    AUDIO_URL = BASE_URL + NAME_FILE;
                    AudioPlayerHelper.getInstance().playAudio(AUDIO_URL);
                } else {
                    //get all the path of the files inside Animals folder
//                    String[] path = mPresenter.getAllPathByCategory(titleCategory);
//                    path[position] = path[position].substring(path[position].indexOf("-") + 1);
                    //name of the file to open the right interior
                    //String tag = path[position];
                    Intent interiorActivity = new Intent(DisplayItemsActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    //b.putString("cat",tag);
                    interiorActivity.putExtras(b);
                    startActivity(interiorActivity);
                }
            }
        });
    }

    @OnClick(R.id.button_home)
    void goHome() {
        goBackHome();
    }


    @OnClick(R.id.button_quiz)
    void goQuiz() {
        Intent quizActivity = new Intent(this, QuizActivity.class);
        Bundle b = new Bundle();
        b.putString(Config.CATEGORY, titleCategory);
        quizActivity.putExtras(b);
        startActivity(quizActivity);
    }

    @OnClick(R.id.button_back)
    void goBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }
}
