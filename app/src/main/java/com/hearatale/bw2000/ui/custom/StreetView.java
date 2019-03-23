package com.hearatale.bw2000.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hearatale.bw2000.data.model.InfoButton;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.util.Config;

public class StreetView extends AppCompatImageView {

    InfoStreetView mInfoView;

    private float mXClick, mYClick;
    InfoButton mInfoButtonClick;
    Paint paintRect = new Paint();
    Rect rect = new Rect();

    OnClickGetButtonInfoListener onClickGetInfoButtonListener;

    public void setOnClickGetInfoButtonListener(OnClickGetButtonInfoListener onClickGetInfoButtonListener) {
        this.onClickGetInfoButtonListener = onClickGetInfoButtonListener;
    }

    public StreetView(Context context) {
        super(context);
        init();
    }

    public StreetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StreetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setInfoView(InfoStreetView infoView) {
        this.mInfoView = infoView;
    }

    private void init() {
        paintRect.setStyle(Paint.Style.STROKE);
        paintRect.setColor(Color.RED);
        paintRect.setStrokeWidth(4);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXClick = event.getX();
                mYClick = event.getY();
                return true;

            case MotionEvent.ACTION_UP:
                performClick();
                return true;
        }
        return false;
    }

    // Because we call this from onTouchEvent, this code will be executed for both
    // normal touch events and for when the system calls this using Accessibility
    @Override
    public boolean performClick() {
        super.performClick();
        handleClick();
        return true;
    }

    public void handleClick() {
        if (onClickGetInfoButtonListener == null) return;
        float percentXClick = (mXClick / getWidth()) * 100;
        float percentYClick = (mYClick / getHeight()) * 100;
        mInfoButtonClick = mInfoView.getInfoButtonClick(percentXClick, percentYClick);
        onClickGetInfoButtonListener.onClick(this, mInfoButtonClick);
        invalidate();

    }


    public interface OnClickGetButtonInfoListener {
        void onClick(View view, InfoButton infoButton);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Config.DEVELOP) {
            drawRectangles(canvas);
        }
    }

    private void drawRectangles(Canvas canvas) {
        int[] colors = new int[]{
                Color.RED,
                Color.BLACK,
                Color.BLUE,
                Color.GRAY,
                Color.GREEN,
                Color.YELLOW,
                Color.CYAN,
                Color.DKGRAY,
                Color.parseColor("#96e1f0"),
                Color.parseColor("#fd2ad3"),//10
                Color.parseColor("#4d2d0a"),
                Color.parseColor("#004506"),
                Color.parseColor("#8000ff"),
                Color.parseColor("#ff0080"),
                Color.parseColor("#f6e2e3"),
                Color.parseColor("#d1a835"),
                Color.parseColor("#c4c1ea"),
                Color.parseColor("#51b2e8"),
                Color.parseColor("#ff3333"),
                Color.parseColor("#008375")
        };
        for (InfoButton infoButton : mInfoView.getListInfoButton()) {
            int i = mInfoView.getListInfoButton().indexOf(infoButton) % 20;
            paintRect.setColor(colors[i]);
            if (infoButton.equals(mInfoButtonClick)) {
                paintRect.setStrokeWidth(10);
            } else {
                paintRect.setStrokeWidth(4);
            }

            int x1 = (int) infoButton.getPercentStartX() * getWidth() / 100;
            int x2 = ((int) infoButton.getPercentEndX() * getWidth() / 100);
            int y1 = (int) infoButton.getPercentStartY() * getHeight() / 100;
            int y2 = ((int) infoButton.getPercentEndY() * getHeight() / 100);
            rect.set(x1, y1, x2, y2);
            canvas.drawRect(rect, paintRect);
        }
    }
}
