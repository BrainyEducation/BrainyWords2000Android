package com.hearatale.bw2000.ui.main;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.hearatale.bw2000.R;
import com.hearatale.bw2000.data.AppDataManager;
import com.hearatale.bw2000.data.model.InfoButton;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.ui.adapter.StreetsViewAdapter;
import com.hearatale.bw2000.ui.base.BaseActivity;
import com.hearatale.bw2000.util.Config;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainMvpView, ScaleGestureDetector.OnScaleGestureListener {

    private static final float MIN_ZOOM = 1.0f;
    private static final float MAX_ZOOM = 2.0f;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.layout_root)
    ConstraintLayout layoutRoot;

    @OnClick(R.id.button_student_logout)
    void studentLogout(){
        AppDataManager.getInstance().studentLogout();
        mPresenter.goSplashScreen();
    }

    private MainMvpPresenter<MainMvpView> mPresenter;

    List<InfoStreetView> infoStreetViewList;

    ScaleGestureDetector scaleDetector;
    GestureDetector gestureDetector;

    private float dx = 0f;
    private float dy = 0f;
    private float scale = 1.0f;
    private float lastScaleFactor = 0f;


    @Override
    protected void onResume() {
        Config.IS_GO_BACK_HOME = false;
        super.onResume();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mPresenter = new MainPresenter<>();
        mPresenter.onAttach(this);
        initRecycleView();

        scaleDetector = new ScaleGestureDetector(this, this);

        gestureDetector = new GestureDetector(this, new GestureListener());
    }

    private void initRecycleView() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        infoStreetViewList = mPresenter.getListInfoStreetView();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        int half = Integer.MAX_VALUE / 2;
        layoutManager.scrollToPosition(half - half % infoStreetViewList.size());
        recyclerView.setLayoutManager(layoutManager);
        int heightItem = (int) (displayMetrics.heightPixels * 0.8);
        StreetsViewAdapter adapter = new StreetsViewAdapter(infoStreetViewList, heightItem, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnGetInfoButton(new StreetsViewAdapter.OnGetInfoButton() {
            @Override
            public void onClick(View view, InfoButton infoButton) {
                if (infoButton == null) return;
                mPresenter.handleInfoButton(infoButton);
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        scaleDetector.onTouchEvent(ev);
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = scaleDetector.getScaleFactor();
        if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
            float prevScale = scale;
            scale *= scaleFactor;
            scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
            lastScaleFactor = scaleFactor;
            float adjustedScaleFactor = scale / prevScale;
            // added logic to adjust dx and dy for pinch/zoom pivot point
            float focusX = scaleDetector.getFocusX();
            float focusY = scaleDetector.getFocusY();
            dx += (dx - focusX) * (adjustedScaleFactor - 1);
            dy += (dy - focusY) * (adjustedScaleFactor - 1);
        } else {
            lastScaleFactor = 0;
        }
        applyScaleAndTranslation();
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            dy = dy - distanceY;
            applyScaleAndTranslation();
            return true;
        }

    }

    private void applyScaleAndTranslation() {
        float maxDx = parent().getWidth() * (scale - 1);  // adjusted for zero pivot
        float maxDy = parent().getHeight() * (scale - 1);  // adjusted for zero pivot
        dx = Math.min(Math.max(dx, -maxDx), 0);  // adjusted for zero pivot
        dy = Math.min(Math.max(dy, -maxDy), 0);  // adjusted for zero pivot

        parent().setScaleX(scale);
        parent().setScaleY(scale);
        parent().setPivotX(0f);  // default is to pivot at view center
        parent().setPivotY(0f);  // default is to pivot at view center
        parent().setTranslationX(dx);
        parent().setTranslationY(dy);
    }

    private View parent() {
        return layoutRoot;
    }
}
