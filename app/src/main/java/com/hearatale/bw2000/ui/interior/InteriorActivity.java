package com.hearatale.bw2000.ui.interior;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hearatale.bw2000.R;
import com.hearatale.bw2000.data.model.InfoButton;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.data.model.typedef.InteriorSpecialDef;
import com.hearatale.bw2000.data.model.typedef.InteriorViewDef;
import com.hearatale.bw2000.data.model.typedef.ToolbarDef;
import com.hearatale.bw2000.ui.base.BaseActivity;
import com.hearatale.bw2000.ui.custom.StreetView;
import com.hearatale.bw2000.util.Config;
import com.hearatale.bw2000.util.FileHelper;
import com.hearatale.bw2000.util.glide.GlideApp;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

public class InteriorActivity extends BaseActivity implements InteriorMvpView {


    @BindView(R.id.layout_root)
    ConstraintLayout layoutRoot;

    @BindView(R.id.layout_interior)
    LinearLayoutCompat layoutInterior;

    @BindView(R.id.layout_zoo)
    LinearLayoutCompat layoutZoo;

    @BindView(R.id.layout_scroll)
    HorizontalScrollView horizontalScrollView;

    @BindView(R.id.toolbar_left)
    ConstraintLayout toolbarLeft;

    @BindView(R.id.toolbar)
    LinearLayout toolbar;

    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.button_menu)
    ImageButton buttonMenu;

    @BindView(R.id.button_home)
    ImageButton buttonHome;

    InteriorMvpPresenter<InteriorMvpView> mPresenter;
    InfoStreetView mInfoInteriorView;

    private String cat;
    @InteriorViewDef
    int mInteriorViewDef = InteriorViewDef.INTERIOR_HOUSE;

    @InteriorSpecialDef
    int mInteriorSpecialViewDef = InteriorSpecialDef.NONE;

    boolean isDisplayScroll;

    @Override
    protected int getContentView() {
        return R.layout.activity_interior;
    }

    @Override
    protected void initViews() {
        mPresenter = new InteriorPresenter<>();
        mPresenter.onAttach(this);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            cat = b.getString(Config.CATEGORY);
        }

        if (TextUtils.isEmpty(cat)) {
            return;
        }

        //check which store interior we need to display
        if (cat.contains("toys")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_TOYS;
        } else if (cat.contains("bakery")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_BAKERY;
        } else if (cat.contains("job")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_JOB;
        } else if (cat.contains("vehicle")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_VEHICLES;
        } else if (cat.contains("sports")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_SPORTS;
        } else if (cat.contains("cloth")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_CLOTHING;
        } else if (cat.contains("groceries")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_GROCERIES;
        } else if (cat.contains("music")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_MUSIC;
        } else if (cat.contains("hair")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_HAIR_SALON;
        } else if (cat.contains("tools")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_TOOLS;
        } else if (cat.contains("zoo")) {
            mInteriorSpecialViewDef = InteriorSpecialDef.INTERIOR_ZOO;
            if (cat.contains("birds")) {
                mInteriorViewDef = InteriorViewDef.BIRDS;
            } else if (cat.contains("water")) {
                mInteriorViewDef = InteriorViewDef.WATER;
            } else if (cat.contains("dino")) {
                mInteriorViewDef = InteriorViewDef.DINOSAUR;
            } else if (cat.contains("meat")) {
                mInteriorViewDef = InteriorViewDef.MEAT;
            } else if (cat.contains("monkey")) {
                mInteriorViewDef = InteriorViewDef.MONKEY;
            } else if (cat.contains("reptiles")) {
                mInteriorViewDef = InteriorViewDef.REPTILES;
            } else if (cat.contains("plant")) {
                mInteriorViewDef = InteriorViewDef.PLANT;
            } else {
                mInteriorSpecialViewDef = InteriorSpecialDef.NONE;
                mInteriorViewDef = InteriorViewDef.INTERIOR_ZOO;
            }
        } else if (cat.contains("nursery")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_NURSERY;
        } else if (cat.contains("farm")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_FARM_ANIMALS;
        } else if (cat.contains("school")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_SCHOOL;
            if (cat.contains("playground")) {
                mInteriorSpecialViewDef = InteriorSpecialDef.INTERIOR_SCHOOL_PLAYGROUND;
            }
        } else if (cat.contains("health")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_MEDICAL_CENTER;
        } else if (cat.contains("things")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_THINGS;
        } else if (cat.contains("people")) {
            mInteriorViewDef = InteriorViewDef.INTERIOR_PEOPLE;
        } else if (cat.contains("PEDIATRICIAN") || cat.contains("pediatrician")) {
            mInteriorViewDef = InteriorViewDef.PEDIATRICIAN;
            mInteriorSpecialViewDef = InteriorSpecialDef.INTERIOR_PEDIATRICIAN;
        } else if (cat.contains("park")) {
            mInteriorSpecialViewDef = InteriorSpecialDef.INTERIOR_PARK;
            isDisplayScroll = true;
            if (cat.equals("park1")) {
                mInteriorViewDef = InteriorViewDef.BUILDING4A;
            } else if (cat.equals("park2")) {
                mInteriorViewDef = InteriorViewDef.BUILDING4B;
            } else if (cat.equals("park3")) {
                mInteriorViewDef = InteriorViewDef.BUILDING4C;
            } else {
                isDisplayScroll = false;
                mInteriorSpecialViewDef = InteriorSpecialDef.NONE;
                mInteriorViewDef = InteriorViewDef.INTERIOR_PARK;
            }
        } else if (cat.equals("building7")) {
            isDisplayScroll = true;
            mInteriorViewDef = InteriorViewDef.BUILDING7A;
            mInteriorSpecialViewDef = InteriorSpecialDef.INTERIOR_BUILDING7A;
        } else if (cat.equals("building11a")) {
            isDisplayScroll = true;
            mInteriorViewDef = InteriorViewDef.BUILDING11A;
            mInteriorSpecialViewDef = InteriorSpecialDef.INTERIOR_BUILDING11A;
        } else {
            mInteriorViewDef = InteriorViewDef.INTERIOR_HOUSE;
        }

        if (mInteriorSpecialViewDef == InteriorSpecialDef.INTERIOR_PARK
                || mInteriorSpecialViewDef == InteriorSpecialDef.INTERIOR_BUILDING7A
                || mInteriorSpecialViewDef == InteriorSpecialDef.INTERIOR_BUILDING11A) {
            showHideToolbar(ToolbarDef.MENU_BACK_HOME);
            toolbarLeft.setVisibility(View.GONE);
        } else if (mInteriorSpecialViewDef == InteriorSpecialDef.INTERIOR_ZOO) {
            toolbarLeft.setVisibility(View.VISIBLE);
            showHideToolbar(ToolbarDef.NONE);
        } else if (mInteriorSpecialViewDef == InteriorSpecialDef.INTERIOR_PEDIATRICIAN
                || mInteriorSpecialViewDef == InteriorSpecialDef.INTERIOR_SCHOOL_PLAYGROUND
                ) {
            showHideToolbar(ToolbarDef.BACK_HOME);
            toolbarLeft.setVisibility(View.GONE);
        } else {
            toolbarLeft.setVisibility(View.GONE);
            showHideToolbar(ToolbarDef.BACK);
        }


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        initStreetScenes(displayMetrics);

    }

    private void initStreetScenes(DisplayMetrics displayMetrics) {
        mInfoInteriorView = mPresenter.getInfoInteriorView(mInteriorViewDef);

        final StreetView streetView = generateInteriorViewFromInfo(displayMetrics, mInfoInteriorView);
        if (isDisplayScroll) {
            layoutInterior.setVisibility(View.GONE);
            layoutZoo.setVisibility(View.GONE);
            horizontalScrollView.setVisibility(View.VISIBLE);
            horizontalScrollView.post(new Runnable() {
                @Override
                public void run() {
                    horizontalScrollView.addView(streetView);
                }
            });
        } else if (cat.contains("zoo")) {
            horizontalScrollView.setVisibility(View.GONE);
            layoutInterior.setVisibility(View.GONE);
            layoutZoo.setVisibility(View.VISIBLE);
            layoutZoo.post(new Runnable() {
                @Override
                public void run() {
                    layoutZoo.addView(streetView);
                }
            });
        } else {
            horizontalScrollView.setVisibility(View.GONE);
            layoutZoo.setVisibility(View.GONE);
            layoutInterior.setVisibility(View.VISIBLE);
            layoutInterior.post(new Runnable() {
                @Override
                public void run() {
                    layoutInterior.addView(streetView);
                }
            });
        }


    }

    @NonNull
    private StreetView generateInteriorViewFromInfo(DisplayMetrics displayMetrics, InfoStreetView infoStreetView) {

        String filePath = Config.INTERIOR_ASSETS + infoStreetView.getImageLink();
        Logger.e(filePath);
        Bitmap bitmap = FileHelper.getBitmapFromAsset(this, filePath);
        int width = (int) (bitmap.getWidth() * (displayMetrics.heightPixels * 0.85f) / bitmap.getHeight());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.width = width;
        StreetView interiorView = new StreetView(this);
        interiorView.setLayoutParams(layoutParams);
        interiorView.setInfoView(infoStreetView);

        GlideApp.with(this)
                .load(bitmap)
                .dontAnimate()
                .into(interiorView);

        interiorView.setScaleType(ImageView.ScaleType.FIT_XY);
        interiorView.setOnClickGetInfoButtonListener(new StreetView.OnClickGetButtonInfoListener() {
            @Override
            public void onClick(View view, InfoButton infoButton) {
                if (infoButton == null) return;
                mPresenter.handleInfoButton(infoButton);
            }
        });
        return interiorView;
    }

    @OnClick({R.id.button_back, R.id.button_back_left})
    void goBack() {
        onBackPressed();
    }

    @OnClick(R.id.button_home)
    void goHome() {
        goBackHome();
    }

    @OnClick(R.id.button_menu)
    void goInterior() {
        mPresenter.goInteriorSpecial(mInteriorSpecialViewDef);
    }

    private void showHideToolbar(@ToolbarDef int toolbarDef) {

        toolbar.setVisibility(View.VISIBLE);
        switch (toolbarDef) {
            case ToolbarDef.NONE:
                toolbar.setVisibility(View.GONE);
                break;

            case ToolbarDef.MENU_BACK_HOME:
                buttonMenu.setVisibility(View.VISIBLE);
                buttonHome.setVisibility(View.VISIBLE);
                buttonBack.setVisibility(View.VISIBLE);
                break;

            case ToolbarDef.BACK:
                buttonMenu.setVisibility(View.INVISIBLE);
                buttonHome.setVisibility(View.INVISIBLE);
                buttonBack.setVisibility(View.VISIBLE);
                break;


            case ToolbarDef.BACK_HOME:
                buttonMenu.setVisibility(View.INVISIBLE);
                buttonHome.setVisibility(View.VISIBLE);
                buttonBack.setVisibility(View.VISIBLE);
                break;

        }
    }
}
