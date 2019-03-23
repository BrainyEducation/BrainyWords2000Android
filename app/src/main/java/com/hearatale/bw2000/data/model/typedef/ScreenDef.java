package com.hearatale.bw2000.data.model.typedef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ScreenDef.MAIN,
        ScreenDef.INTERIOR,
        ScreenDef.DISPLAY_ITEM,
})

@Retention(RetentionPolicy.SOURCE)
public @interface ScreenDef {
    int MAIN = 0;
    int INTERIOR = 1;
    int DISPLAY_ITEM = 2;
}
